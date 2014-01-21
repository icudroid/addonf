package fr.k2i.adbeback.core.business.ad.rule;

import fr.k2i.adbeback.core.business.IMetaData;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@DiscriminatorValue(IMetaData.ColumnMetadata.AdRule.Discrimator.AMOUNT_RULE)
public class AmountRule extends AdRule {
	private static final long serialVersionUID = 6708314171621564778L;

    @Column(name = IMetaData.ColumnMetadata.AdRule.AMOUNT)
    private Double amount;

    @Column(name = IMetaData.ColumnMetadata.AdRule.INITIAL_AMOUNT)
    private Double initialAmount;

    @Column(name = IMetaData.ColumnMetadata.AdRule.INSERTED)
    @Temporal(TemporalType.TIMESTAMP)
    private Date inserted;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AmountRule)) return false;
        if (!super.equals(o)) return false;

        AmountRule that = (AmountRule) o;

        if (amount != null ? !amount.equals(that.amount) : that.amount != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (amount != null ? amount.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "AmountRule{" +
                "amount=" + amount +
                '}';
    }
}
