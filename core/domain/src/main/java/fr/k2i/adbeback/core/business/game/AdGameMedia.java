package fr.k2i.adbeback.core.business.game;

import fr.k2i.adbeback.core.business.media.Media;

import javax.persistence.*;
import java.util.List;

@Entity
@DiscriminatorValue("Music")
public class AdGameMedia extends AbstractAdGame {

	private static final long serialVersionUID = -4291589475824097583L;
    protected List<Media> medias;


    @ManyToMany(targetEntity = Media.class)
    @JoinTable(name = "adgame_media", joinColumns = @JoinColumn(name = "ADGAME_ID"), inverseJoinColumns = @JoinColumn(name = "MEDIA_ID"))
    public List<Media> getMedias() {
        return medias;
    }

    public void setMedias(List<Media> medias) {
        this.medias = medias;
    }


    @Override
    public String toString() {
        return "AdGameMedia{" +
                "medias=" + medias +
                '}';
    }
}

