package fr.k2i.adbeback.webapp.state;

import lombok.Data;

import java.io.Serializable;

/**
 * User: dimitri
 * Date: 07/10/14
 * Time: 11:09
 * Goal:
 */
@Data
public class AdPayFlowState implements Serializable{
    private Integer nbLuckWin;
    private Integer nbLuckPayAndWin;

    private String lastTransactionId;

}
