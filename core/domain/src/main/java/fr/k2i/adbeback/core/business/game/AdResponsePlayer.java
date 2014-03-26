package fr.k2i.adbeback.core.business.game;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;


import fr.k2i.adbeback.core.business.BaseObject;
import lombok.Data;

@Data
@Entity
@Table(name = "ad_response_player")
public class AdResponsePlayer extends BaseObject implements Serializable {

	private static final long serialVersionUID = -9114916487551116090L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "RESPONSE_ID")
    private List<Possibility> responses;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "AD_SCORE_ID")
    private AdScore adScore;

	private Integer number;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AdResponsePlayer)) return false;

        AdResponsePlayer that = (AdResponsePlayer) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (number != null ? !number.equals(that.number) : that.number != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (number != null ? number.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "AdResponsePlayer{" +
                "number=" + number +
                '}';
    }
}
