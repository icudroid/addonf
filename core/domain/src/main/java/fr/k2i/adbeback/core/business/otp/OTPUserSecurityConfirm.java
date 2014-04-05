package fr.k2i.adbeback.core.business.otp;

import fr.k2i.adbeback.core.business.IMetaData;
import fr.k2i.adbeback.core.business.user.User;
import lombok.Data;

import javax.persistence.*;

/**
 * User: dimitri
 * Date: 18/02/13
 * Time: 11:29
 * Goal:
 */

@Data
@Entity
@DiscriminatorValue(IMetaData.ColumnMetadata.OTPSecurity.Discrimator.USER)
public class OTPUserSecurityConfirm extends OTPSecurity {


    @ManyToOne
    @JoinColumn(name=IMetaData.ColumnMetadata.OTPSecurity.USER_JOIN)
    protected User user;


    @Override
    public String toString() {
        return "OTPUserSecurityConfirm{" +
                "user=" + user +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OTPUserSecurityConfirm)) return false;

        OTPUserSecurityConfirm that = (OTPUserSecurityConfirm) o;

        if (user != null ? !user.equals(that.user) : that.user != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return user != null ? user.hashCode() : 0;
    }
}
