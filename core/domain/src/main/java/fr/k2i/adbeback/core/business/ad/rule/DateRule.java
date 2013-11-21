package fr.k2i.adbeback.core.business.ad.rule;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;


@Entity
@DiscriminatorValue("Date")
public class DateRule extends AdRule {
	private static final long serialVersionUID = 6708314171621564778L;


    protected Date startDate;
    protected Date endDate;

    @Temporal(TemporalType.DATE)
    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    @Temporal(TemporalType.DATE)
    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DateRule)) return false;
        if (!super.equals(o)) return false;

        DateRule dateRule = (DateRule) o;

        if (endDate != null ? !endDate.equals(dateRule.endDate) : dateRule.endDate != null) return false;
        if (startDate != null ? !startDate.equals(dateRule.startDate) : dateRule.startDate != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (startDate != null ? startDate.hashCode() : 0);
        result = 31 * result + (endDate != null ? endDate.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "DateRule{" +
                "startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }
}
