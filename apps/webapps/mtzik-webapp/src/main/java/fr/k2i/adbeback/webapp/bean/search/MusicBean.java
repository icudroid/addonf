package fr.k2i.adbeback.webapp.bean.search;

import com.google.common.collect.Lists;
import fr.k2i.adbeback.core.business.media.Artist;
import fr.k2i.adbeback.core.business.media.Category;
import fr.k2i.adbeback.core.business.media.Music;
import fr.k2i.adbeback.core.business.media.Productor;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: dev
 * Date: 10/01/14
 * Time: 17:47
 * To change this template use File | Settings | File Templates.
 */
@Data
public class MusicBean implements Serializable{

    private Long id;
    private String title;
    private List<ProductorBean> productors;
    private List<ArtistBean> artists;
    private List<Category> categories;
    private String jacket;
    private Date releaseDate;
    private Integer nbAdsNeeded;


    public MusicBean(Music music){
        id =music.getId();

        title=music.getTitle();

        productors = new ArrayList<ProductorBean>();
        for (Productor productor : music.getProductors()) {
            productors.add(new ProductorBean(productor.getId(),productor.getFirstName(),productor.getLastName(),productor.getPhoto()));
        }

        artists = new ArrayList<ArtistBean>();
        for (Artist artist : music.getArtists()) {
            artists.add(new ArtistBean(artist.getId(),artist.getFirstName(),artist.getLastName(),artist.getPhoto()));
        }

        categories = Lists.newArrayList(music.getCategories());

        jacket = music.getJacket();

        releaseDate = music.getReleaseDate();

        nbAdsNeeded = music.getNbAdsNeeded();

    }

}
