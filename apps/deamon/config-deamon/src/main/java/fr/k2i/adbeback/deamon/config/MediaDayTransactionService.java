package fr.k2i.adbeback.deamon.config;

import au.com.bytecode.opencsv.CSVWriter;
import fr.k2i.adbeback.application.services.mail.IMailEngine;
import fr.k2i.adbeback.application.services.mail.MailEngine;
import fr.k2i.adbeback.application.services.mail.dto.Attachement;
import fr.k2i.adbeback.application.services.mail.dto.Email;
import fr.k2i.adbeback.application.services.mail.exception.SendException;
import fr.k2i.adbeback.core.business.company.billing.DayBilling;
import fr.k2i.adbeback.core.business.company.billing.MonthBilling;
import fr.k2i.adbeback.core.business.game.AdGameTransaction;
import fr.k2i.adbeback.core.business.game.StatusGame;
import fr.k2i.adbeback.core.business.user.Media;
import fr.k2i.adbeback.dao.IAdGameDao;
import fr.k2i.adbeback.dao.IMediaDao;
import fr.k2i.adbeback.dao.IMonthBillingDao;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: dev
 * Date: 15/01/14
 * Time: 14:41
 * To change this template use File | Settings | File Templates.
 */
@Component
public class MediaDayTransactionService {

    protected final Log logger = LogFactory.getLog(this.getClass());

    @Autowired
    private IAdGameDao adGameDao;

    @Autowired
    private IMediaDao mediaDao;

    @Autowired
    private IMailEngine mailEngine;




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
    @Scheduled(cron = "0 30 0 * * *")
    public void doAction() throws URISyntaxException, IOException {
        LocalDate now = new LocalDate();
        LocalDate yesterday = now.minusDays(1);

        List<Media> medias = mediaDao.getAll();

        for (Media media : medias) {
            Double sum = adGameDao.sumTransactionForDate(media, yesterday.toDate());
            Long nbOk = adGameDao.countTransactionsOkByDate(media, yesterday.toDate());
            Long nbKo = adGameDao.countTransactionsOkByDate(media, yesterday.toDate());


            List<AdGameTransaction> trs = adGameDao.findTransactionsForDay(media, new Date(), StatusGame.Win);

            File csv = new File("/tmp/"+ UUID.randomUUID().toString()+".csv");

            FileWriter fileWriter = new FileWriter(csv);

            CSVWriter writer = new CSVWriter(fileWriter, '\t');
            String[] columns = new String[] {"generated","idTransaction","status","amount"};
            writer.writeNext(columns);


            for (AdGameTransaction tr : trs) {
                String[] line = new String[]{tr.getGenerated().toString(),tr.getIdTransaction(),tr.getStatusGame().getLabel(),tr.getAmount().toString()};
                writer.writeNext(line);
            }

            Map<String, Object> model =new HashMap<String, Object>();
            model.put("sum",sum);
            model.put("nbOk",nbOk);
            model.put("nbKo",nbKo);
            model.put("media",media);

            Attachement attachement = new Attachement(new ClassPathResource(csv.getPath()),"transactions.csv");

            Email email = Email.builder()
                    .subject("Vos transactions du " + yesterday.getDayOfMonth() + "/" + yesterday.getMonthOfYear() + "/"+yesterday.getYear())
                    .model(model)
                    .content("email/media_transaction_day")
                    .recipients(media.getUser().getEmail())
                    .attachements(attachement)
                    .build();
            try {
                mailEngine.sendMessage(email);
            } catch (SendException e) {
                logger.debug("Erreur lors de l'envoi du mail des transaction du jour pour le media : "+media.getName(),e);
            }


        }

    }

}
