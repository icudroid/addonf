package fr.k2i.adbeback.webapp.bean.search;

import fr.k2i.adbeback.core.business.media.Artist;
import fr.k2i.adbeback.core.business.media.Person;
import lombok.Data;

import java.io.Serializable;

@Data
public class SmallPersonBean implements Serializable {
    private Long id;
    private String fullName;
    private String photo;

    public SmallPersonBean(Person artist) {
        id = artist.getId();
        fullName = artist.getFullName();
        photo = artist.getPhoto();
    }
}