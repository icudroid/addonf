package fr.k2i.adbeback.webapp.state;

import lombok.Data;

import java.io.Serializable;

/**
 * User: dimitri
 * Date: 07/10/14
 * Time: 11:15
 * Goal:
 */
@Data
public class AdPayCommand implements Serializable{
    private AdPayChoice choice;


}
