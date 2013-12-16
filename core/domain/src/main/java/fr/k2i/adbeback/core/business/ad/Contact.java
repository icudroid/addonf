package fr.k2i.adbeback.core.business.ad;

import fr.k2i.adbeback.core.business.BaseObject;
import fr.k2i.adbeback.core.business.IMetaData;
import fr.k2i.adbeback.core.business.player.Sex;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name= IMetaData.TableMetadata.CONTACT)
public class Contact extends BaseObject implements Serializable{
	private static final long serialVersionUID = 8778110793577776954L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = IMetaData.ColumnMetadata.Contact.ID)
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


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public Sex getSalutation() {
        return salutation;
    }

    public void setSalutation(Sex salutation) {
        this.salutation = salutation;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public String getFunction() {
        return function;
    }

    public void setFunction(String function) {
        this.function = function;
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

