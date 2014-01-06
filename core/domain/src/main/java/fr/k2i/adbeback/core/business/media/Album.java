package fr.k2i.adbeback.core.business.media;

import lombok.Data;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import java.util.List;

@Entity
@DiscriminatorValue("album")
@Data
public class Album extends Media {
	private static final long serialVersionUID = -8195169031786628618L;

/*	private List<Music> musics;

    @ManyToMany(
        cascade = {CascadeType.PERSIST, CascadeType.MERGE},
        mappedBy = "albums",
        targetEntity = Music.class
    )
	public List<Music> getMusics() {
		return musics;
	}

	public void setMusics(List<Music> musics) {
		this.musics = musics;
	}*/


	@Override
	public String toString() {
		return "Album [ id=" + id + ", title=" + title
				+ ", productors=" + productors + ", genres=" + genres
				+ ", description=" + description + ", duration=" + duration
				+ ", jacket=" + jacket + ", releaseDate=" + releaseDate
				+ ", nbAdsNeeded=" + nbAdsNeeded + ", file=" + file + "]";
	}


}
