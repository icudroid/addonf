package fr.k2i.adbeback.core.business.user;

import fr.k2i.adbeback.core.business.BaseObject;
import fr.k2i.adbeback.core.business.IMetaData;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * User: dimitri
 * Date: 11/04/14
 * Time: 10:52
 * Goal:
 */
@Data
@Entity
@Table(name = IMetaData.TableMetadata.CATEGORY_PRICE)
public class CategoryPrice extends BaseObject implements Serializable {

    @Id
    @SequenceGenerator(name = "CategoriePrice_Gen", sequenceName = "CategoriePrice_Sequence")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CategoriePrice_Gen")
    private Long id;

    private Double minPrice;

    @Enumerated(EnumType.STRING)
    private MediaType mediaType;

    @ManyToOne
    @JoinColumn(name = "cat_id")
    private Category category;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CategoryPrice)) return false;

        CategoryPrice that = (CategoryPrice) o;

        if (category != null ? !category.equals(that.category) : that.category != null) return false;
        if (minPrice != null ? !minPrice.equals(that.minPrice) : that.minPrice != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = minPrice != null ? minPrice.hashCode() : 0;
        result = 31 * result + (category != null ? category.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "CategoryPrice{" +
                "category=" + category +
                ", minPrice=" + minPrice +
                '}';
    }
}


