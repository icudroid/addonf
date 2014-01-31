package fr.k2i.adbeback.webapp.bean;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * User: dimitri
 * Date: 30/01/14
 * Time: 15:02
 * Goal:
 */
@Data
public class DoStatBean implements Serializable{
    private Date start;
    private Date end;
}
