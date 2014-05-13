package fr.k2i.adbeback.webapp.bean;

import fr.k2i.adbeback.core.business.game.AdGameTransaction;
import fr.k2i.adbeback.core.business.game.StatusGame;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * User: dimitri
 * Date: 12/05/14
 * Time: 15:52
 * Goal:
 */
@Data
public class AdGameTransactionDto implements Serializable{
    private Double amount;
    private String idTransaction;
    private Date generated;
    private StatusGame statusGame;


    public AdGameTransactionDto(){}

    public AdGameTransactionDto(AdGameTransaction tr){
        amount = tr.getAmount();
        idTransaction = tr.getIdTransaction();
        generated = tr.getGenerated();
        statusGame = tr.getStatusGame();

    }
}
