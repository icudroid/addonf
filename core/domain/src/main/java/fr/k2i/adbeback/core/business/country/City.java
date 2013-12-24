package fr.k2i.adbeback.core.business.country;

import fr.k2i.adbeback.core.business.BaseObject;
import fr.k2i.adbeback.core.business.IMetaData;

import javax.persistence.*;

/**
 * Created with IntelliJ IDEA.
 * User: dev
 * Date: 21/12/13
 * Time: 16:48

 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = IMetaData.TableMetadata.CITY)
public class City extends BaseObject{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = IMetaData.ColumnMetadata.City.ZIPCODE)
    private String zipcode;

    @Column(name = IMetaData.ColumnMetadata.City.CITY)
    private String city;


    @Column(name = IMetaData.ColumnMetadata.City.LON)
    private Double lon;
    @Column(name = IMetaData.ColumnMetadata.City.LAT)
    private Double lat;


    @ManyToOne(cascade = {CascadeType.PERSIST,CascadeType.MERGE})
    @JoinColumn(name = IMetaData.ColumnMetadata.City.COUNTRY)
    private Country country;


    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        City city1 = (City) o;

        if (city != null ? !city.equals(city1.city) : city1.city != null) return false;
        if (id != null ? !id.equals(city1.id) : city1.id != null) return false;
        if (zipcode != null ? !zipcode.equals(city1.zipcode) : city1.zipcode != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (zipcode != null ? zipcode.hashCode() : 0);
        result = 31 * result + (city != null ? city.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "City{" +
                "id=" + id +
                ", zipcode='" + zipcode + '\'' +
                ", city='" + city + '\'' +
                '}';
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }
}
