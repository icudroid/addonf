package fr.k2i.adbeback.webapp.bean.adservice;

import com.fasterxml.jackson.annotation.JsonIgnore;
import fr.k2i.adbeback.webapp.bean.FileCommand;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

/**
 * User: dimitri
 * Date: 21/01/14
 * Time: 10:22
 * Goal:
 */
@Data
public class AdResponseBean implements Serializable{
    private String response;
    @JsonIgnore
    private FileCommand image;
    private boolean correct;
    private Long id;
}
