package fr.k2i.adbeback.core.business.game;

import lombok.Data;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Data
@Entity
@DiscriminatorValue("Price")
public class PricePossibility extends Possibility {
	private static final long serialVersionUID = -5422694885484003177L;
	private Double price;


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((price == null) ? 0 : price.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		PricePossibility other = (PricePossibility) obj;
		if (price == null) {
			if (other.price != null)
				return false;
		} else if (!price.equals(other.price))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "PricePossibility [price=" + price + ", id=" + id + ", ad=" + ad
				+ "]";
	}
}
