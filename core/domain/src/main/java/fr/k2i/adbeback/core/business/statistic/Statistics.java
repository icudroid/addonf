package fr.k2i.adbeback.core.business.statistic;

import fr.k2i.adbeback.core.business.BaseObject;
import fr.k2i.adbeback.core.business.IMetaData;
import fr.k2i.adbeback.core.business.ad.rule.AdService;
import fr.k2i.adbeback.core.business.contact.SubjectMessage;
import fr.k2i.adbeback.core.business.country.City;
import fr.k2i.adbeback.core.business.player.AgeGroup;
import fr.k2i.adbeback.core.business.player.Sex;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: dev
 * Date: 14/01/14
 * Time: 11:15
 * To change this template use File | Settings | File Templates.
 */
@Data
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = "statitics")
@DiscriminatorColumn(name = "classe", discriminatorType = DiscriminatorType.STRING)
public abstract class Statistics extends BaseObject {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;

    @Temporal(TemporalType.DATE)
    protected Date day;

    @Enumerated(EnumType.STRING)
    protected Sex sex;

    @ManyToOne
    @JoinColumn(name = "city_id")
    protected City city;

    @ManyToOne
    @JoinColumn(name = "service_id")
    protected AdService service;

    @Enumerated(EnumType.STRING)
    protected AgeGroup ageGroup;

    protected Long nb;

    protected Statistics(){}

    protected Statistics(Date day, Sex sex, City city, AdService service, AgeGroup ageGroup, Long nb) {
        this.day = day;
        this.sex = sex;
        this.city = city;
        this.service = service;
        this.ageGroup = ageGroup;
        this.nb = nb;
    }
}
