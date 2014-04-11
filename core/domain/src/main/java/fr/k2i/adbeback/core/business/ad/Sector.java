package fr.k2i.adbeback.core.business.ad;

import fr.k2i.adbeback.core.business.BaseObject;
import fr.k2i.adbeback.core.business.IMetaData;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * User: dimitri
 * Date: 11/04/14
 * Time: 09:52
 * Goal:
 */
@Data
@Entity
@Table(name = IMetaData.TableMetadata.SECTOR)
public class Sector extends BaseObject implements Serializable {

    @Id
    @SequenceGenerator(name = "Sector_Gen", sequenceName = "Sector_Sequence")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Sector_Gen")
    private Long id;

    private String code;

    private String description;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Sector)) return false;

        Sector sector = (Sector) o;

        if (code != null ? !code.equals(sector.code) : sector.code != null) return false;
        if (description != null ? !description.equals(sector.description) : sector.description != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = code != null ? code.hashCode() : 0;
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }


    @Override
    public String toString() {
        return "Sector{" +
                "code='" + code + '\'' +
                ", id=" + id +
                ", descritpion='" + description + '\'' +
                '}';
    }
}
