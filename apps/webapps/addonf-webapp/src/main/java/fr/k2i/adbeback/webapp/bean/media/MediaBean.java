package fr.k2i.adbeback.webapp.bean.media;

import fr.k2i.adbeback.core.business.media.Album;
import fr.k2i.adbeback.core.business.media.Category;
import fr.k2i.adbeback.core.business.media.Category;
import fr.k2i.adbeback.webapp.bean.PersonBean;
import lombok.Data;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

@Data
public class MediaBean {
	private Long id;
	private String title;
	private List<PersonBean> productors;
	private List<PersonBean> artists;
	private List<Category> categories;
    private List<AlbumBean> albums;
	private String description;
	private Long duration;
	private String jacket;
	private Date releaseDate;
	private String thumbJacket;
    private Integer nbAdsNeeded;


      public Calendar getCalReleaseDate(){
          Calendar cal = new GregorianCalendar();
          cal.setTime(releaseDate);
          return cal;
      }

    public PersonBean getMainArtist(){
        return artists.get(0);
    }
}
