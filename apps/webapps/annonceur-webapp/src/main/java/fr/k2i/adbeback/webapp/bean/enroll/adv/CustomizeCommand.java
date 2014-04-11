package fr.k2i.adbeback.webapp.bean.enroll.adv;

import fr.k2i.adbeback.core.business.user.MediaType;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * User: dimitri
 * Date: 11/04/14
 * Time: 13:37
 * Goal:
 */
@Data
public class CustomizeCommand  implements Serializable {
    private Long sectorId;
    private List<CustomerTargetCommand> customersTarget = new ArrayList<CustomerTargetCommand>();
    private CustomerTargetCommand currentCustomerTarget = new CustomerTargetCommand();
    private List<MediaType> targetMedia = new ArrayList<MediaType>();
    private List<String> productLines = new ArrayList<String>();
}
