package fr.k2i.adbeback.webapp.bean.enroll.media;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * User: dimitri
 * Date: 18/04/14
 * Time: 11:25
 * Goal:
 */
@Data
public class CategoryPriceBeans  implements Serializable{
    List<CategoryPriceBean> categoryPriceBeans = new ArrayList<CategoryPriceBean>();
}
