package fr.k2i.adbeback.webapp.bean;

import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: dev
 * Date: 21/12/13
 * Time: 11:54
 * To change this template use File | Settings | File Templates.
 */
public class EnrollBrandCommand implements Serializable {
    private String name;
    private String siret;
    private AddressBean address;
    private ContactBean contact;
    @Size(message = "wrong",min = 8,max = 30)
    private String password;
    @Size(message = "wrong",min = 8,max = 30)
    private String confirmPassword;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSiret() {
        return siret;
    }

    public void setSiret(String siret) {
        this.siret = siret;
    }

    public AddressBean getAddress() {
        return address;
    }

    public void setAddress(AddressBean address) {
        this.address = address;
    }

    public ContactBean getContact() {
        return contact;
    }

    public void setContact(ContactBean contact) {
        this.contact = contact;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
