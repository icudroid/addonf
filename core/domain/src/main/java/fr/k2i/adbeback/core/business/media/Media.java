package fr.k2i.adbeback.core.business.media;

import fr.k2i.adbeback.core.business.BaseObject;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = "media")
@DiscriminatorColumn(name = "classe", discriminatorType = DiscriminatorType.STRING)
public abstract class Media extends BaseObject implements Serializable {
	private static final long serialVersionUID = -5332865080689901973L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	protected Long id;

	protected String title;

    @ManyToMany(targetEntity = Productor.class, cascade = {
            CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(name = "media_productor", joinColumns = @JoinColumn(name = "MEDIA_ID"), inverseJoinColumns = @JoinColumn(name = "PRODUCTOR_ID"))
    protected List<Productor> productors;

    @ManyToMany(targetEntity = Category.class, cascade = {
            CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(name = "media_genre", joinColumns = @JoinColumn(name = "MEDIA_ID"), inverseJoinColumns = @JoinColumn(name = "GENRE_ID"))
    protected List<Category> categories;
	protected String description;
	protected Long duration;
	protected String jacket;
	protected String thumbJacket;
	protected String imgPresentation;
    @Temporal(TemporalType.DATE)
	protected Date releaseDate;
	protected Integer nbAdsNeeded; 
	protected String file;



}
