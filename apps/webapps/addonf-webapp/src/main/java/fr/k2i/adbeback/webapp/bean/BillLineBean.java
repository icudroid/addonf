package fr.k2i.adbeback.webapp.bean;

import lombok.Data;
import lombok.experimental.Builder;

import java.io.Serializable;

/**
 * User: dimitri
 * Date: 01/10/14
 * Time: 17:17
 * Goal:
 */
@Data
@Builder
public class BillLineBean implements Serializable{

    private String product;

    private Integer nb;
}
