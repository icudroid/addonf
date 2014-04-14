package fr.k2i.adbeback.core.business.user;

import fr.k2i.adbeback.core.business.BaseObject;
import fr.k2i.adbeback.core.business.IMetaData;
import lombok.Data;

import javax.persistence.*;

/**
 * User: dimitri
 * Date: 14/04/14
 * Time: 15:32
 * Goal:
 */
@Data
@Entity
@Table(name="user_acces")
public class UserAccess  extends BaseObject{

    @Id
    @SequenceGenerator(name = "UserAccess_Gen", sequenceName = "UserAccess_Sequence")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "UserAccess_Gen")
    protected Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "ad_right")
    private AdRight right;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserAccess)) return false;

        UserAccess that = (UserAccess) o;

        if (right != that.right) return false;
        if (user != null ? !user.equals(that.user) : that.user != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = right != null ? right.hashCode() : 0;
        result = 31 * result + (user != null ? user.hashCode() : 0);
        return result;
    }


    @Override
    public String toString() {
        return "UserAccess{" +
                "user=" + user +
                ", right=" + right +
                '}';
    }
}
