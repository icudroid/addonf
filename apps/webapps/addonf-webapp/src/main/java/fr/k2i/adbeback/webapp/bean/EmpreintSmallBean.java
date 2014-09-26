package fr.k2i.adbeback.webapp.bean;

import fr.k2i.adbeback.core.business.transaction.EmpreintStatus;
import fr.k2i.adbeback.core.business.transaction.TransactionHistory;
import lombok.Data;
import lombok.experimental.Builder;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * User: dimitri
 * Date: 24/09/14
 * Time: 14:18
 * Goal:
 */
@Data
@Builder
public class EmpreintSmallBean implements Serializable{
    private Long id;
    private Integer adAmount;
    private Integer adAmountLeft;

    private List<String> products;

    private Date startDate;

    private Date endDate;

    private EmpreintStatus status;

    private List<TransactionHistory> histories;

}
