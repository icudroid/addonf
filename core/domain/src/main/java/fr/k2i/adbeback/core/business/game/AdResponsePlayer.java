package fr.k2i.adbeback.core.business.game;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.*;


import fr.k2i.adbeback.core.business.BaseObject;
import fr.k2i.adbeback.core.business.IMetaData;
import fr.k2i.adbeback.core.business.ad.rule.AdService;
import lombok.Data;

@Data
@Entity
@Table(name = "ad_response_player")
public class AdResponsePlayer extends BaseObject implements Serializable {

	private static final long serialVersionUID = -9114916487551116090L;

    @Id
    @SequenceGenerator(name = "AdResponsePlayer_Gen", sequenceName = "AdResponsePlayer_Sequence")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "AdResponsePlayer_Gen")
	private Long id;

    @ManyToMany
    @JoinTable(
            name = "player_res_possibility",
            joinColumns = { @JoinColumn(name = "possibility_id") },
            inverseJoinColumns = @JoinColumn(name = "ad_response_player_id")
    )
    private List<Possibility> responses;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "AD_SCORE_ID")
    private AdScore adScore;

	private Integer number;

    private Boolean correctAnswer;

    @Temporal(TemporalType.TIMESTAMP)
    private Date played;


    private AdChoise adChoise;

    @ManyToOne
    @JoinColumn(name = "AD_SERVICE_ID")
    private AdService adService;

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
