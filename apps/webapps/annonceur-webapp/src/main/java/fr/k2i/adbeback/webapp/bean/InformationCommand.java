package fr.k2i.adbeback.webapp.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import fr.k2i.adbeback.core.business.ad.AdType;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
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
    private Long displayDuration = 0L;
    private Double initialAmonut;
    private Date startDate =new Date();
    private Date endDate =new Date();

    @JsonIgnore
    private MultipartFile adFile;

    @JsonIgnore
    private FileCommand adFileCommand;


}
