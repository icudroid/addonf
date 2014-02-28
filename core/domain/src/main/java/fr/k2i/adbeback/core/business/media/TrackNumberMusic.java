package fr.k2i.adbeback.core.business.media;

import fr.k2i.adbeback.core.business.BaseObject;
import lombok.Data;

import javax.persistence.*;

/**
 * User: dimitri
 * Date: 21/02/14
 * Time: 13:49
 * Goal:
 */
@Data
@Entity
@Table(name = "track_music")
public class TrackNumberMusic  extends BaseObject {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;
    @Column(name = "track_number")
    private Integer trackNumber;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "music_id")
    private Music music;


    public TrackNumberMusic(){}

    public TrackNumberMusic(Integer trackNumber, Music music) {
        this.trackNumber = trackNumber;
        this.music = music;
    }
}
