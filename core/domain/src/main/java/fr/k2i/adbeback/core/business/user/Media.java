package fr.k2i.adbeback.core.business.user;

import fr.k2i.adbeback.core.business.BaseObject;
import fr.k2i.adbeback.core.business.IMetaData;
import fr.k2i.adbeback.core.business.company.Company;
import fr.k2i.adbeback.core.business.player.Address;
import lombok.Data;

import javax.persistence.*;
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
@Table(name="media")
@PrimaryKeyJoinColumn(name="company_id")
public class Media extends Company {

    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = IMetaData.ColumnMetadata.Media.USER_JOIN)
    private MediaUser user;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = IMetaData.ColumnMetadata.Media.JOIN)
    private List<CategoryPrice> minPriceByMediaType;

    private String passPhrase;

    @Column(name = "ext_id",unique = true)
    private String extId;


}
