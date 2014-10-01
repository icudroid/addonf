package fr.k2i.adbeback.webapp.bean;

import lombok.Data;
import lombok.experimental.Builder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * User: dimitri
 * Date: 01/10/14
 * Time: 17:05
 * Goal:
 */
@Data
@Builder
public class OrderBean implements Serializable{
    private Integer adAmount;
    private String referenceMedia;
    private List<BillLineBean> products = new ArrayList<>();
    private Date orderDate;
    private String media;


}
