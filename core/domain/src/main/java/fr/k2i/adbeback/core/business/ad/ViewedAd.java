package fr.k2i.adbeback.core.business.ad;

import fr.k2i.adbeback.core.business.BaseObject;
import fr.k2i.adbeback.core.business.IMetaData;
import fr.k2i.adbeback.core.business.ad.rule.AdRule;
import fr.k2i.adbeback.core.business.player.Player;

import javax.persistence.*;
import java.io.Serializable;

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

    @ManyToOne
    @JoinColumn(name = IMetaData.ColumnMetadata.ViewedMedia.AD)
    private Ad ad;

    @ManyToOne
    @JoinColumn(name = IMetaData.ColumnMetadata.ViewedMedia.RULE)
    private AdRule adRule;//Brand, Product, Open

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }


    public Integer getNb() {
        return nb;
    }

    public void setNb(Integer nb) {
        this.nb = nb;
    }

    public Ad getAd() {
        return ad;
    }

    public void setAd(Ad ad) {
        this.ad = ad;
    }

    public AdRule getAdRule() {
        return adRule;
    }

    public void setAdRule(AdRule adRule) {
        this.adRule = adRule;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ViewedAd)) return false;

        ViewedAd viewedAd = (ViewedAd) o;

        if (ad != null ? !ad.equals(viewedAd.ad) : viewedAd.ad != null) return false;
        if (adRule != null ? !adRule.equals(viewedAd.adRule) : viewedAd.adRule != null) return false;
        if (nb != null ? !nb.equals(viewedAd.nb) : viewedAd.nb != null) return false;
        if (player != null ? !player.equals(viewedAd.player) : viewedAd.player != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = player != null ? player.hashCode() : 0;
        result = 31 * result + (nb != null ? nb.hashCode() : 0);
        result = 31 * result + (ad != null ? ad.hashCode() : 0);
        result = 31 * result + (adRule != null ? adRule.hashCode() : 0);
        return result;
    }


    @Override
    public String toString() {
        return "ViewedAd{" +
                "player=" + player +
                ", nb=" + nb +
                ", ad=" + ad +
                ", adRule=" + adRule +
                '}';
    }
}
