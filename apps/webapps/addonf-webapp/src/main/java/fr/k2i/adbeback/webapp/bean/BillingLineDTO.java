package fr.k2i.adbeback.webapp.bean;

import lombok.Data;

/**
 * User: dimitri
 * Date: 06/10/14
 * Time: 16:33
 * Goal:
 */
@Data
public class BillingLineDTO {
    private String category;
    private String reference;
    private String name;
    private Integer nb;
}
