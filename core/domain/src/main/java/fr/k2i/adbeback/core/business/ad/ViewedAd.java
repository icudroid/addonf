package fr.k2i.adbeback.core.business.ad;

import fr.k2i.adbeback.core.business.BaseObject;
import fr.k2i.adbeback.core.business.IMetaData;
import fr.k2i.adbeback.core.business.ad.rule.AdService;
import fr.k2i.adbeback.core.business.player.Player;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = IMetaData.TableMetadata.VIEWED_AD)
public class ViewedAd extends BaseObject implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = IMetaData.ColumnMetadata.ViewedMedia.ID)
    private Long id;

    @ManyToOne
    @JoinColumn(name=IMetaData.ColumnMetadata.ViewedMedia.PLAYER)
    private Player player;

    @Column(name=IMetaData.ColumnMetadata.ViewedMedia.NB)
    private Integer nb;

    @Temporal(TemporalType.DATE)
    private Date date;

    @ManyToOne
    @JoinColumn(name = IMetaData.ColumnMetadata.ViewedMedia.RULE)
    private AdService adRule;//Brand, Product, Open

    public ViewedAd(){}

    public ViewedAd(Player currentPlayer, AdService adRule) {
        this.player = currentPlayer;
        nb = 1;
        date = new Date();
        this.adRule = adRule;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ViewedAd)) return false;

        ViewedAd viewedAd = (ViewedAd) o;

        if (adRule != null ? !adRule.equals(viewedAd.adRule) : viewedAd.adRule != null) return false;
        if (nb != null ? !nb.equals(viewedAd.nb) : viewedAd.nb != null) return false;
        if (player != null ? !player.equals(viewedAd.player) : viewedAd.player != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = player != null ? player.hashCode() : 0;
        result = 31 * result + (nb != null ? nb.hashCode() : 0);
        result = 31 * result + (adRule != null ? adRule.hashCode() : 0);
        return result;
    }


    @Override
    public String toString() {
        return "ViewedAd{" +
                "player=" + player +
                ", nb=" + nb +
                ", adRule=" + adRule +
                '}';
    }

    public void view() {
        nb++;
    }
}
