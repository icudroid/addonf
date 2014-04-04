package fr.k2i.adbeback.core.business.ad;

import fr.k2i.adbeback.core.business.BaseObject;
import fr.k2i.adbeback.core.business.IMetaData;
import fr.k2i.adbeback.core.business.player.Sex;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name= IMetaData.TableMetadata.CONTACT)
public class Contact extends BaseObject implements Serializable{
	private static final long serialVersionUID = 8778110793577776954L;

    @Id
    @SequenceGenerator(name = "Contact_Gen", sequenceName = "Contact_Sequence")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Contact_Gen")
    private Long id;

	@Column(name=IMetaData.ColumnMetadata.Contact.LASTNAME,length = 60)
	private String lastname;
	
	@Column(name=IMetaData.ColumnMetadata.Contact.FIRSTNAME,length = 60)
	private String firstname;
	
	@Enumerated(EnumType.STRING)
	@Column(name=IMetaData.ColumnMetadata.Contact.SEX,length = 5)
	private Sex salutation;

	@Column(name=IMetaData.ColumnMetadata.Contact.PHONE,length = 15)
	private String phone;
	
	@Column(name=IMetaData.ColumnMetadata.Contact.MOBILE,length = 15)
	private String mobilePhone;
	
	@Column(name=IMetaData.ColumnMetadata.Contact.EMAIL,length = 60)
	private String email;

	@ManyToOne
    @JoinColumn(name=IMetaData.ColumnMetadata.Contact.BRAND)
	private Brand brand;

    @Column(name=IMetaData.ColumnMetadata.Contact.FUNCTION,length = 60)
    private String function;

    @Transient
    public String getFullName() {
        return firstname +" "+ lastname;
    }



    @Override
    public String toString() {
        return "Contact{" +
                "id='" + id + '\'' +
                ", lastname='" + lastname + '\'' +
                ", firstname='" + firstname + '\'' +
                ", salutation=" + salutation +
                ", phone='" + phone + '\'' +
                ", mobilePhone='" + mobilePhone + '\'' +
                ", email='" + email + '\'' +
                ", brand=" + brand.getName() +
                ", function='" + function + '\'' +
                '}';
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Contact contact = (Contact) o;

        if (brand != null ? !brand.equals(contact.brand) : contact.brand != null) return false;
        if (email != null ? !email.equals(contact.email) : contact.email != null) return false;
        if (firstname != null ? !firstname.equals(contact.firstname) : contact.firstname != null) return false;
        if (function != null ? !function.equals(contact.function) : contact.function != null) return false;
        if (lastname != null ? !lastname.equals(contact.lastname) : contact.lastname != null) return false;
        if (mobilePhone != null ? !mobilePhone.equals(contact.mobilePhone) : contact.mobilePhone != null) return false;
        if (phone != null ? !phone.equals(contact.phone) : contact.phone != null) return false;
        if (salutation != contact.salutation) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = lastname != null ? lastname.hashCode() : 0;
        result = 31 * result + (firstname != null ? firstname.hashCode() : 0);
        result = 31 * result + (salutation != null ? salutation.hashCode() : 0);
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        result = 31 * result + (mobilePhone != null ? mobilePhone.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (brand != null ? brand.hashCode() : 0);
        result = 31 * result + (function != null ? function.hashCode() : 0);
        return result;
    }
}

