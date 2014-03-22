package fr.k2i.adbeback.core.business.game;

import fr.k2i.adbeback.core.business.media.Media;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@DiscriminatorValue("Music")
public class AdGameMedia extends AbstractAdGame {

	private static final long serialVersionUID = -4291589475824097583L;
    @ManyToMany(targetEntity = Media.class)
    @JoinTable(name = "adgame_media", joinColumns = @JoinColumn(name = "ADGAME_ID"), inverseJoinColumns = @JoinColumn(name = "MEDIA_ID"))
    protected List<Media> medias;

    @Override
    public String toString() {
        return "AdGameMedia{" +
                "medias=" + medias +
                '}';
    }
}

