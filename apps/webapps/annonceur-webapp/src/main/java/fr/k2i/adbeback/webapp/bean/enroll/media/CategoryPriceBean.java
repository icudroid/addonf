package fr.k2i.adbeback.webapp.bean.enroll.media;

import fr.k2i.adbeback.core.business.user.Category;
import fr.k2i.adbeback.core.business.user.MediaType;
import lombok.Data;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: dev
 * Date: 12/04/14
 * Time: 20:58
 * To change this template use File | Settings | File Templates.
 */
@Data
public class CategoryPriceBean implements Serializable{
    private String uid;
    private Double price;
    private String category;
    private MediaType mediaType;
    private Double bid;
}
