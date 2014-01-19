package fr.k2i.adbeback.webapp.bean;

import fr.k2i.adbeback.core.business.ad.AdType;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: dev
 * Date: 24/12/13
 * Time: 11:38
 * To change this template use File | Settings | File Templates.
 */
@Data
public class InformationCommand implements Serializable{
    private Long id;
    private String name;
    private AdDisplay displayAd = AdDisplay.VIDEO;
    private Integer displayDuration = 0;
    private Double initialAmonut;
    private Date startDate;
    private Date endDate;
}
