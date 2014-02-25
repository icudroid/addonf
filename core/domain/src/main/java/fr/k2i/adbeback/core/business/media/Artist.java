package fr.k2i.adbeback.core.business.media;

import fr.k2i.adbeback.core.business.IMetaData;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@DiscriminatorValue("Artist")
public class Artist extends Person {
	public Artist() {

	}

	public Artist(String firstName, String lastName) {
		super(firstName, lastName);
	}

	private static final long serialVersionUID = -8371684556048514484L;


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "productor_artist",
            joinColumns = { @JoinColumn(name = "artist_id") },
            inverseJoinColumns = @JoinColumn(name = "productor_id")
    )
    private List<Productor> productors = new ArrayList<Productor>();

	//private List<Music> musics;


/*    @ManyToMany(
            cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            mappedBy = "artists",
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
		return "Artist [id=" + id + ", firstName=" + firstName + ", lastName="
				+ lastName + ", website=" + website + ", version=" + version
				+ "]";
	}


}
