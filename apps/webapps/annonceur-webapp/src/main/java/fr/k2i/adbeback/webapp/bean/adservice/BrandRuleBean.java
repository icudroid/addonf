package fr.k2i.adbeback.webapp.bean.adservice;

import fr.k2i.adbeback.core.business.ad.Brand;
import lombok.Data;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * User: dimitri
 * Date: 21/01/14
 * Time: 10:17
 * Goal:
 */
@Data
public class BrandRuleBean extends  AdServiceBean implements Serializable {
    private List<Brand> noDisplayWith = new ArrayList<Brand>();


}
