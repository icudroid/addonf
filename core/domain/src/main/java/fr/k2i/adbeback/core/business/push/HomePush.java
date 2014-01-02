package fr.k2i.adbeback.core.business.push;

import fr.k2i.adbeback.core.business.BaseObject;
import fr.k2i.adbeback.core.business.media.Media;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "home_push")
@Data
public class HomePush extends BaseObject implements Serializable {

	private static final long serialVersionUID = 6711696472191272787L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

    @ManyToMany(targetEntity = Media.class)
    @JoinTable(name = "push_home", joinColumns = @JoinColumn(name = "PUSH_ID"), inverseJoinColumns = @JoinColumn(name = "MEDIA_ID"))
	private List<Media> medias;

    @Temporal(TemporalType.DATE)
	private Date startDate;

    @Temporal(TemporalType.DATE)
    private Date endDate;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof HomePush)) return false;

        HomePush homePush = (HomePush) o;

        if (endDate != null ? !endDate.equals(homePush.endDate) : homePush.endDate != null) return false;
        if (startDate != null ? !startDate.equals(homePush.startDate) : homePush.startDate != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = startDate != null ? startDate.hashCode() : 0;
        result = 31 * result + (endDate != null ? endDate.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "HomePush{" +
                "startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }
}
