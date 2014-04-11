package fr.k2i.adbeback.core.business.user;

import fr.k2i.adbeback.core.business.BaseObject;
import fr.k2i.adbeback.core.business.IMetaData;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * User: dimitri
 * Date: 11/04/14
 * Time: 10:55
 * Goal:
 */
@Data
@Entity
@Table(name = IMetaData.TableMetadata.CATEGORY)
public class Category extends BaseObject implements Serializable {

    @Id
    @SequenceGenerator(name = "Category_Gen", sequenceName = "Category_Sequence")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Category_Gen")
    private Long id;

    @Column(unique = true,nullable = false)
    private String key;

    @ManyToOne
    @JoinColumn(name="main_id")
    private Category main;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Category)) return false;

        Category category = (Category) o;

        if (key != null ? !key.equals(category.key) : category.key != null) return false;
        if (main != null ? !main.equals(category.main) : category.main != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = key != null ? key.hashCode() : 0;
        result = 31 * result + (main != null ? main.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Category{" +
                "key='" + key + '\'' +
                ", main=" + main +
                '}';
    }
}
