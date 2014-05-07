package fr.k2i.adbeback.core.business.company;

import fr.k2i.adbeback.core.business.BaseObject;
import fr.k2i.adbeback.core.business.IMetaData;
import fr.k2i.adbeback.core.business.company.billing.MonthBilling;
import fr.k2i.adbeback.core.business.player.Address;
import fr.k2i.adbeback.core.business.user.Attachement;
import fr.k2i.adbeback.core.business.user.LegalStatus;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Map;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: dev
 * Date: 13/04/14
 * Time: 10:51
 * To change this template use File | Settings | File Templates.
 */
@Data
@Entity
@Table(name="company")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Company extends BaseObject implements Serializable {

    @Id
    @SequenceGenerator(name = "Company_Gen", sequenceName = "Company_Sequence")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Company_Gen")
    protected Long id;

    protected String name;

    protected String phone;

    @Embedded
    protected Address address = new Address();

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = IMetaData.ColumnMetadata.Agency.CREATED_DATE)
    protected Date createdDate;


    @Column(name = IMetaData.ColumnMetadata.Agency.SIRET_NUMBER)
    protected String siret;

    @Column(name = IMetaData.ColumnMetadata.Agency.SIREN_NUMBER)
    protected String siren;


    @Enumerated(EnumType.STRING)
    @Column(name = IMetaData.ColumnMetadata.Agency.LEGAL_STATUS)
    protected LegalStatus legalStatus;

    @OneToMany(cascade = {CascadeType.ALL})
    @JoinTable(name=IMetaData.TableMetadata.ATTACHMENTS)
    @MapKeyColumn(name=IMetaData.ColumnMetadata.Attachement.ID,length = 32,nullable = true)
    protected Map<String,Attachement> attachements;

    protected String logo;

    @OneToMany(targetEntity = MonthBilling.class,cascade = CascadeType.ALL,mappedBy = "company")
    private Set<MonthBilling> monthBillings;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Company)) return false;

        Company company = (Company) o;

        if (createdDate != null ? !createdDate.equals(company.createdDate) : company.createdDate != null) return false;
        if (name != null ? !name.equals(company.name) : company.name != null) return false;
        if (siret != null ? !siret.equals(company.siret) : company.siret != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (createdDate != null ? createdDate.hashCode() : 0);
        result = 31 * result + (siret != null ? siret.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Company{" +
                "siret='" + siret + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
