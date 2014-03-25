package fr.k2i.adbeback.core.business.game;

import fr.k2i.adbeback.core.business.IMetaData;
import fr.k2i.adbeback.core.business.user.Partner;
import lombok.Data;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Data
@Entity
@DiscriminatorValue("AdGameTr")
public class AdGameTransaction extends AbstractAdGame {

	private static final long serialVersionUID = -4291589475824097583L;

    //private String idPartner;
    private String idTransaction;

    @ManyToOne
    @JoinColumn(name = "partner_id")
    private Partner partner;

    @Override
    public String toString() {
        return "AdGameTransaction";
    }
}

