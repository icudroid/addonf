package fr.k2i.adbeback.deamon.config;

import fr.k2i.adbeback.core.business.ad.Ad;
import fr.k2i.adbeback.core.business.ad.VideoAd;
import fr.k2i.adbeback.core.business.statistic.Statistics;
import fr.k2i.adbeback.dao.IAdDao;
import fr.k2i.adbeback.dao.IStatisticsDao;
import fr.k2i.adbeback.logger.LogHelper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: dev
 * Date: 15/01/14
 * Time: 14:41
 * To change this template use File | Settings | File Templates.
 */
@Component
public class EncodeVideoService {

    @Autowired
    private IAdDao adDao;

    @Value("${addonf.ads.tmp.location}")
    private String adsPathTmp;

    @Value("${addonf.ads.location}")
    private String adsPath;

    Logger logger = LogHelper.getLogger(EncodeVideoService.class);

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
    @Scheduled(cron = "0 0/1 * * * *")
    public void doAction() throws URISyntaxException, IOException, InterruptedException {

        List<VideoAd> ads = adDao.findNoEncodedAd();
        for (VideoAd ad : ads) {
            String adFile = ad.getAdFile();

            String encodeCmds[] = {
                    "avconv -i {0} -ar 48000 -strict experimental {1}.mp4",
                    "avconv -i {0} {1}.ogv",
                    "avconv -i {0} {1}.webm"
            };

            for (String encodeCmd : encodeCmds) {
                String cmd = MessageFormat.format(encodeCmd, adsPathTmp + adFile, adsPath + adFile);
                logger.debug(cmd);
                Process exec = Runtime.getRuntime().exec(cmd);
                exec.waitFor();
                logger.debug("exit command status : "+exec.exitValue());
            }
            logger.debug("delete tmp ad file : "+adFile);

            new File(adsPathTmp+adFile).renameTo(new File(adsPath + adFile));
            ad.setAdFileEncoded(true);
        }
    }

}
