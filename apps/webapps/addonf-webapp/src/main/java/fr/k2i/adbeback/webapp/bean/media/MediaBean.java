package fr.k2i.adbeback.webapp.bean.media;

import fr.k2i.adbeback.core.business.media.Genre;
import fr.k2i.adbeback.webapp.bean.PersonBean;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class MediaBean {
	private Long id;
	private String title;
	private List<PersonBean> productors;
	private List<PersonBean> artists;
	private List<Genre> genres;
	private String description;
	private Long duration;
	private String jacket;
	private Date releaseDate;
	private String thumbJacket;
    protected Integer nbAdsNeeded;
}
