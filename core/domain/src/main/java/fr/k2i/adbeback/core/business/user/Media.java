package fr.k2i.adbeback.core.business.user;

import fr.k2i.adbeback.core.business.BaseObject;
import fr.k2i.adbeback.core.business.IMetaData;
import fr.k2i.adbeback.core.business.player.Address;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.Map;

/**
 * User: dimitri
 * Date: 01/04/14
 * Time: 11:35
 * Goal:
 */
@Entity
@Data
@Table(name="media")
public class Media extends BaseObject {

    @Id
    @SequenceGenerator(name = "Media_Gen", sequenceName = "Media_Sequence")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Media_Gen")
    private Long id;

    private String name;

    private String phone;

    @Embedded
    private Address address = new Address();

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = IMetaData.ColumnMetadata.Media.CREATED_DATE)
    private Date createdDate;


    @Column(name = IMetaData.ColumnMetadata.Media.SIRET_NUMBER)
    private String siret;

    @Column(name = IMetaData.ColumnMetadata.Media.SIREN_NUMBER)
    private String siren;


    @Enumerated(EnumType.STRING)
    @Column(name = IMetaData.ColumnMetadata.Media.LEGAL_STATUS)
    private LegalStatus legalStatus;

    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = IMetaData.ColumnMetadata.Media.USER_JOIN)
    private MediaUser user;

    @OneToMany(cascade = {CascadeType.ALL})
    @JoinTable(name=IMetaData.TableMetadata.ATTACHEMENTS)
    @MapKeyColumn(name=IMetaData.ColumnMetadata.Attachement.ID,length = 32)
    private Map<String,Attachement> attachements;


    @OneToMany(cascade=CascadeType.ALL)
    @JoinColumn(name="media_id")
    @MapKeyEnumerated(EnumType.STRING)
    private Map<MediaType,CategoryPrice> minPriceByMediaType;

}
