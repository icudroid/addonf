package fr.k2i.adbeback.application.services.mail;

import fr.k2i.adbeback.application.services.mail.dto.Attachement;
import fr.k2i.adbeback.application.services.mail.dto.Email;
import fr.k2i.adbeback.application.services.mail.exception.SendException;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class for sending e-mail messages based on Velocity templates
 * or with attachments.
 * 
 * @author Matt Raible
 */
public class MailEngine implements IMailEngine{
    private final Log log = LogFactory.getLog(MailEngine.class);
    private JavaMailSender mailSender;
    private String defaultFrom;
    private Configuration configuration;
    private String imagesResources;

    private int CONTENT_TEXT = 0;
    private int CONTENT_HTML = 1;

    private static final String OUTPUT_ENCODING = "UTF-8";


    public MailEngine(JavaMailSender mailSender, String defaultFrom,FreeMarkerConfigurer configuration,String imagesResources) {
        this.mailSender = mailSender;
        this.defaultFrom = defaultFrom;
        this.configuration = configuration.getConfiguration();
        this.imagesResources = imagesResources;
    }

    @Override
    public void sendMessage(Email email) throws SendException {
        sendMessage(email,Locale.FRANCE);
    }
    /**
     * Send a simple message based.
     * @param email
     */
    @Override
    public void sendMessage(Email email, Locale locale) throws SendException {

        final String[] contents;
        final String subject;
        final String messageKey = email.getMessageKey();
        final List<String> recipients = email.getRecipients();
        final List<Attachement> attachements = email.getAttachements();

        try {
            contents = retrieveContent(email,locale);
            subject = email.getSubject();
        } catch (IOException e) {
            log.error("IOException when processing template : ", e);
            throw new SendException("Unable to read or process freemarker configuration or template", e);

        } catch (TemplateException e) {
            log.error("Error when processing template : " , e);
            throw new SendException("Problem initializing freemarker or rendering template", e);
        }

        try {
            mailSender.send(new MimeMessagePreparator() {
                public void prepare(MimeMessage mimeMessage) throws MessagingException {
                    MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, "UTF-8");
                    message.setFrom(defaultFrom);
                    message.setTo(recipients.toArray(new String[recipients.size()]));
                    message.setSubject(subject);
                    if(contents.length==2){
                        message.setText(contents[CONTENT_TEXT], contents[CONTENT_HTML]);
                    }else{
                        message.setText(contents[CONTENT_TEXT]);
                    }

                    Collection<String> imgToSearch = findImgToSearch(contents[CONTENT_HTML]);

                    if (imgToSearch != null) {
                        for (String filename : imgToSearch) {
                            ClassPathResource resource = new ClassPathResource(imagesResources+File.separator+filename);
                            message.addInline(filename, resource);
                        }
                    }

                    mimeMessage.addHeader("X-Fianet-Message-Key", messageKey);
                    if(!attachements.isEmpty()){
                        for (Attachement attachement : attachements) {
                            message.addAttachment(attachement.getAttachmentName(),attachement.getResource());
                        }
                    }
                }
            });
        } catch (MailException e) {
            log.error("Problem while sending email message : " , e);
            throw new SendException("Problem while sending email message to '" + email.getRecipients() + "'", e);
        }
    }


    @Override
    public Collection<String> findImgToSearch(String htmlContent) {

        Map<String, String> map = new HashMap<String, String>();
        // search for img tag with src attribute begining with cid 'cid or "cid
        Matcher matcherImg = Pattern.compile(
                "<\\s*img[^>]*src=([\"']?)cid[^>]*>", Pattern.CASE_INSENSITIVE)
                .matcher(htmlContent);

        // search for the content ID cid value (after cid: )
        Pattern patternCID = Pattern
                .compile(".*src=([\"']?)cid:([^>\\s]*)[\\s>]?",
                        Pattern.CASE_INSENSITIVE);
        while (matcherImg.find()) {
            String img = matcherImg.group();
            Matcher matcherCID = patternCID.matcher(img);
            while (matcherCID.find()) {
                String cid = matcherCID.group(2).replaceAll("[\"']", "");
                map.put(cid, cid);
            }
        }

        Collection<String> values = null;
        if (map.size() != 0) {
            values = map.values();
        }

        return values;
    }


    @Override
    public String[] retrieveContent(Email email, Locale locale) throws TemplateException, IOException {
        List<String> content = new ArrayList<String>();
        Template textTemplate = configuration.getTemplate(email.getContent() + "-text.ftl",locale, OUTPUT_ENCODING);
        textTemplate.setOutputEncoding(OUTPUT_ENCODING);
        content.add(FreeMarkerTemplateUtils.processTemplateIntoString(textTemplate, email.getModel()));

        if (email.isHtml()) {
            Template htmlTemplate = configuration.getTemplate(email.getContent()+ "-html.ftl",locale, OUTPUT_ENCODING);
            htmlTemplate.setOutputEncoding(OUTPUT_ENCODING);
            content.add(FreeMarkerTemplateUtils.processTemplateIntoString(htmlTemplate, email.getModel()));
        }

        return content.toArray(new String[content.size()]);
    }


}
