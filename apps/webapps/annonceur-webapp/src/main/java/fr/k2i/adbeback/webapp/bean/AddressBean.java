package fr.k2i.adbeback.webapp.bean;

import fr.k2i.adbeback.core.business.player.Address;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: dev
 * Date: 21/12/13
 * Time: 11:57
 * To change this template use File | Settings | File Templates.
 */
public class AddressBean implements Serializable{
    private String street;
    private String zipcode;
    private String city;
    private String country;

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

}
