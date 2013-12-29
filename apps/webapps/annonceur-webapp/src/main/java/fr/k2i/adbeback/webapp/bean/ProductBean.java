package fr.k2i.adbeback.webapp.bean;

import lombok.Data;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: dev
 * Date: 24/12/13
 * Time: 11:36
 * To change this template use File | Settings | File Templates.
 */
@Data
public class ProductBean implements Serializable{
    private Long id;
    private String name;
    private String description;
    private Double publicPrice;
    private Integer adPrice;
}
