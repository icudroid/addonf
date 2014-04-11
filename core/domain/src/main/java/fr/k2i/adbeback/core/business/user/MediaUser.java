package fr.k2i.adbeback.core.business.user;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * User: dimitri
 * Date: 05/12/13
 * Time: 16:21
 * Goal:
 */
@Data
@Entity
@DiscriminatorValue("Media")
public class MediaUser extends User{

    private String passPhrase;

    @Column(name = "ext_id",unique = true)
    private String extId;



}
