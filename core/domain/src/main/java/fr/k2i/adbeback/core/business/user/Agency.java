package fr.k2i.adbeback.core.business.user;

import fr.k2i.adbeback.core.business.BaseObject;
import fr.k2i.adbeback.core.business.IMetaData;
import fr.k2i.adbeback.core.business.player.Address;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * User: dimitri
 * Date: 01/04/14
 * Time: 11:35
 * Goal:
 */
@Entity
@Data
@Table(name="agency")
public class Agency extends BaseObject {

    @Id
    @SequenceGenerator(name = "Agency_Gen", sequenceName = "Agency_Sequence")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Agency_Gen")
    private Long id;

    private String name;

    private String phone;

    @Embedded
    private Address address = new Address();

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = IMetaData.ColumnMetadata.Agency.CREATED_DATE)
    private Date createdDate;


    @Column(name = IMetaData.ColumnMetadata.Agency.SIRET_NUMBER)
    private String siret;

    @Column(name = IMetaData.ColumnMetadata.Agency.SIREN_NUMBER)
    private String siren;


    @Enumerated(EnumType.STRING)
    @Column(name = IMetaData.ColumnMetadata.Agency.LEGAL_STATUS)
    private LegalStatus legalStatus;

    @OneToMany(cascade = {CascadeType.ALL})
    @JoinColumn(name = IMetaData.ColumnMetadata.Agency.USER_JOIN)
    private List<AgencyUser> users = new ArrayList<AgencyUser>();

    @OneToMany(cascade = {CascadeType.ALL})
    @JoinTable(name=IMetaData.TableMetadata.ATTACHEMENTS)
    @MapKeyColumn(name=IMetaData.ColumnMetadata.Attachement.ID,length = 32)
    private Map<String,Attachement> attachements;


    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = IMetaData.ColumnMetadata.Agency.CONFIG)
    private IhmConfig ihmConfig;

    public void addUser(AgencyUser user) {
        if(users==null){
            users = new ArrayList<AgencyUser>();
        }
        user.setAgency(this);
        users.add(user);
    }
}
