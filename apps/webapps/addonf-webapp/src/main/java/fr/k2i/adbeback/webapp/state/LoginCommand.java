package fr.k2i.adbeback.webapp.state;

import lombok.Data;

import java.io.Serializable;

/**
 * User: dimitri
 * Date: 15/10/14
 * Time: 15:35
 * Goal:
 */
@Data
public class LoginCommand implements Serializable{
    private String username;
    private String password;
}
