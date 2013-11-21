package fr.k2i.adbeback.application.services.mail;

import fr.k2i.adbeback.application.services.mail.dto.Attachement;
import fr.k2i.adbeback.application.services.mail.dto.Email;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.ReflectionUtils;
import org.subethamail.wiser.Wiser;
import org.subethamail.wiser.WiserMessage;

import javax.mail.BodyPart;
import javax.mail.Part;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author Bryan Noll
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
        locations={ "classpath:/applicationContext-test.xml"})
public class MailEngineTest extends AbstractJUnit4SpringContextTests {
    @Autowired
    MailEngine mailEngine;
    
    JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

    @Before
    public void setUp() {
        mailSender.setHost("localhost");
        Field field = ReflectionUtils.findField(mailEngine.getClass(), "mailSender");
        field.setAccessible(true);
        ReflectionUtils.setField(field,mailEngine,mailSender);
    }

    @After
    public void tearDown() {
        Field field = ReflectionUtils.findField(mailEngine.getClass(), "mailSender");
        field.setAccessible(true);
        ReflectionUtils.setField(field,mailEngine,null);
    }

    @Test
    public void testSend() throws Exception {
        // mock smtp server
        Wiser wiser = new Wiser();
        // set the port to a random value so there's no conflicts between tests
        int port = 2525 + (int)(Math.random() * 100);
        mailSender.setPort(port);
        wiser.setPort(port);
        wiser.start();

        Map<String,Object> model = new HashMap<String, Object>();
        model.put("test","test");
        Email.Producer producer = Email.builder(true)
                .subject("Merci de votre inscription")
                .model(model)
                .content("test")
                .recipients("test@test.fr")
                .noAttachements();

        Email email = producer.build();
        this.mailEngine.sendMessage(email);

        wiser.stop();
        assertTrue(wiser.getMessages().size() == 1);
        WiserMessage wm = wiser.getMessages().get(0);

        assertEquals("Merci de votre inscription", wm.getMimeMessage().getSubject());
    }

    @Test
    public void testSendMessageWithAttachment() throws Exception {
        final String ATTACHMENT_NAME = "boring-attachment.txt";
        
        //mock smtp server
        Wiser wiser = new Wiser();
        int port = 2525 + (int)(Math.random() * 100);
        mailSender.setPort(port);
        wiser.setPort(port);
        wiser.start();

        ClassPathResource cpResource = new ClassPathResource("/test-attachment.txt");

        Map<String,Object> model = new HashMap<String, Object>();
        model.put("test","test");
        Email.Producer producer_noattach = Email.builder(true)
                .subject("Merci de votre inscription")
                .model(model)
                .content("test")
                .recipients("test@test.fr")
                .noAttachements();


        Email.Producer producer = Email.builder(true)
                .subject("Merci de votre inscription")
                .model(model)
                .content("test")
                .recipients("test@test.fr")
                .attachements(new Attachement(cpResource,ATTACHMENT_NAME));

        Email email = producer.build();
        this.mailEngine.sendMessage(email);

        Email email_noattch = producer_noattach.build();
        this.mailEngine.sendMessage(email_noattch);

        wiser.stop();
        // one without and one with from
        assertTrue(wiser.getMessages().size() == 2);
        
        WiserMessage wm = wiser.getMessages().get(0);
        MimeMessage mm = wm.getMimeMessage();

        Object o = wm.getMimeMessage().getContent();
        assertTrue(o instanceof MimeMultipart);
        MimeMultipart multi = (MimeMultipart)o;
        int numOfParts = multi.getCount();
        
        boolean hasTheAttachment = false;
        for (int i = 0; i < numOfParts; i++) {
            BodyPart bp = multi.getBodyPart(i);
            String disp = bp.getDisposition();
            if (disp!=null && disp.equals(Part.ATTACHMENT)) { //the attachment to the email
                hasTheAttachment = true;
                assertEquals(ATTACHMENT_NAME, bp.getFileName());
            }
        }
        assertTrue(hasTheAttachment);
        assertEquals("Merci de votre inscription", mm.getSubject());
    }
}
