package fr.k2i.adbeback.webapp.bean;

import lombok.Data;

import java.io.Serializable;

/**
 * User: dimitri
 * Date: 21/01/14
 * Time: 13:39
 * Goal:
 */
@Data
public class BrandBean implements Serializable{
    private Long id;
    private String name;
    private String logo;

    public BrandBean(Long id, String name, String logo) {
        this.id = id;
        this.name = name;
        this.logo = logo;
    }

    public BrandBean() {
    }
}
