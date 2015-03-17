package fr.k2i.adbeback.deamon.config;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.html.simpleparser.StyleSheet;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import fr.k2i.adbeback.application.services.mail.IMailEngine;
import fr.k2i.adbeback.application.services.mail.dto.Email;
import fr.k2i.adbeback.application.services.mail.exception.SendException;
import fr.k2i.adbeback.core.business.company.billing.BillStatus;
import fr.k2i.adbeback.core.business.company.billing.DayBilling;
import fr.k2i.adbeback.core.business.company.billing.MonthBilling;
import fr.k2i.adbeback.core.business.user.Media;
import fr.k2i.adbeback.dao.IAdGameDao;
import fr.k2i.adbeback.dao.IMediaDao;
import fr.k2i.adbeback.dao.IMonthBillingDao;
import fr.k2i.adbeback.date.DateUtils;
import fr.k2i.adbeback.logger.LogHelper;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring4.SpringTemplateEngine;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: dev
 * Date: 15/01/14
 * Time: 14:41
 * To change this template use File | Settings | File Templates.
 */
@Component
public class BillingMediaService {
    @Autowired
    private IAdGameDao adGameDao;

    @Autowired
    private IMediaDao mediaDao;

    @Autowired
    private IMonthBillingDao monthBillingDao;

    @Autowired
    private IMailEngine mailEngine;

    @Autowired
    private SpringTemplateEngine templateEngine;

    @Value("${addonf.billing.medialocation}")
    private String mediaBillingPath;

    private final Logger logger = LogHelper.getLogger(this.getClass());

    /*
    Seconds 	  	    0-59 	  	, - * /
    Minutes 	  	    0-59 	  	, - * /
    Hours 	  	        0-23 	  	, - * /
    Day-of-month 	  	1-31 	  	, - * ? / L W
    Month 	  	        1-12 or JAN-DEC 	  	, - * /
    Day-of-Week 	  	1-7 or SUN-SAT 	  	, - * ? / L #
    Year (Optional) 	empty, 1970-2199 	  	, - * /
     */

    @Transactional
    @Scheduled(cron = "0 10 0 * * *")
    public void doAction() throws URISyntaxException, IOException {
        LocalDate now = LocalDate.now();
        LocalDate yesterday = now.minusDays(1);

        List<Media> medias = mediaDao.getAll();

        for (Media media : medias) {
            Double sum = adGameDao.sumTransactionForDate(media, DateUtils.asDate(yesterday));
            MonthBilling monthBilling = monthBillingDao.findByMonth(media,yesterday.getMonthValue(),yesterday.getYear());
            if(monthBilling == null){
                monthBilling = monthBillingDao.save(new MonthBilling(yesterday.getMonthValue(),yesterday.getYear(),media));
            }

            DayBilling dayBilling = new DayBilling();
            dayBilling.setAmount(sum);
            dayBilling.setMonthBilling(monthBilling);
            dayBilling.setDayOfMonth(yesterday.getDayOfMonth());
            dayBilling.setNbTransaction(adGameDao.countTransactionsOkByDate(media, DateUtils.asDate(yesterday)));

            BigDecimal sumMonth = new BigDecimal(""+monthBilling.getSum());
            sumMonth = sumMonth.add(new BigDecimal("" + sum));
            monthBilling.setSum(sumMonth.doubleValue());


            if(now.getDayOfMonth() == 1){
                Map<String, Object> model =new HashMap<String, Object>();

                try {
                    generateMonthBillingPdf(media, monthBilling);
                } catch (DocumentException e) {
                    logger.debug("Erreur lors de la création du media billing pour le media "+media.getName(),e);
                }

                model.put("media",media);
                Email email = Email.builder()
                        .subject("Votre Facture d'avoir est disponible")
                        .model(model)
                        .content("email/media_billing_month")
                        .recipients(media.getUser().getEmail())
                        .noAttachements()
                        .build();
                try {
                    mailEngine.sendMessage(email);
                } catch (SendException e) {
                    logger.debug("Erreur lors de l'envoi du mail de la facture pour le media : "+media.getName(),e);
                }

                monthBilling.setStatus(BillStatus.EMIT);
            }


        }

    }

    private void generateMonthBillingPdf(Media media, MonthBilling monthBilling) throws DocumentException, IOException {
        final Context context = new Context(Locale.FRANCE);

        context.setVariable("media",media);
        context.setVariable("monthBilling",monthBilling);

        String html = this.templateEngine.process("pdf/media_billing", context);

        File billingPdf = new File(mediaBillingPath+monthBilling.getId()+".pdf");
        FileOutputStream out = new FileOutputStream(billingPdf);

        Document document = new Document(PageSize.A4, 36, 36, 54, 54);
        PdfWriter writer = PdfWriter.getInstance(document, out);

        writer.setBoxSize("art", new Rectangle(36, 54, 559, 788));
        document.open();


        StyleSheet styles = new StyleSheet();
        /*styles.loadStyle("color-1","color","#a5027d");
        styles.loadStyle("ul.test","margin-left","-25px");
        styles.loadStyle(".footer","vertical-align","text-bottom");
        styles.loadStyle(".footer","bottom","0px");*/

        XMLWorkerHelper.getInstance().parseXHtml(writer, document, new StringReader(html));

        /*
        Rectangle rect = writer.getBoxSize("art");

        Font font = new Font(Font.FontFamily.HELVETICA,10,Font.NORMAL);

        ColumnText.showTextAligned(writer.getDirectContent(),
                Element.ALIGN_CENTER, new Phrase("text1", font),
                (rect.getLeft() + rect.getRight()) / 2, rect.getBottom() - 18, 0);


        ColumnText.showTextAligned(writer.getDirectContent(),
                Element.ALIGN_CENTER, new Phrase("text2",font),
                (rect.getLeft() + rect.getRight()) / 2, rect.getBottom() - 30, 0);
        */

        document.close();



    }

}
