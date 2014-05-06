package fr.k2i.adbeback.webapp.bean;

import lombok.Data;
import org.hibernate.validator.constraints.Email;

import java.io.Serializable;

/**
 * User: dimitri
 * Date: 06/05/14
 * Time: 15:16
 * Goal:
 */
@Data
public class ChangeIdentifiantBean implements Serializable{
    @Email
    private String email;
}
