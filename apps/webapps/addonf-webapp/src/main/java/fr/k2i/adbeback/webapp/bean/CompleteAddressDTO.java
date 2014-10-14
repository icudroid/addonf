package fr.k2i.adbeback.webapp.bean;

import lombok.Data;

import java.util.List;

/**
 * User: dimitri
 * Date: 06/10/14
 * Time: 16:29
 * Goal:
 */
@Data
public class CompleteAddressDTO {
    private String firstname;
    private String lastname;
    private List<String> phones;
    private String email;
    private boolean company;

    private String street;
    private String compl;
    private String zip;
    private String city;
    private String country;
}
