package fr.k2i.adbeback.core.business.ad.rule;

import fr.k2i.adbeback.core.business.BaseObject;
import fr.k2i.adbeback.core.business.IMetaData;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;


@Data
@Entity
@Table(name = IMetaData.TableMetadata.AD_RESPONSE)
public class AdResponse extends BaseObject implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = IMetaData.ColumnMetadata.AdResponse.ID)
	private Long id;

    @Column(name = IMetaData.ColumnMetadata.AdResponse.RESPONSE)
	private String response;

    @Column(name = IMetaData.ColumnMetadata.AdResponse.RESPONSE_IMG)
    private String image;
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((response == null) ? 0 : response.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AdResponse other = (AdResponse) obj;
		if (response == null) {
			if (other.response != null)
				return false;
		} else if (!response.equals(other.response))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "AdResponse [id=" + id + ", response=" + response + "]";
	}

}
