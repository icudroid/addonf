package fr.k2i.adbeback.core.business.user;

import fr.k2i.adbeback.core.business.BaseObject;
import lombok.Data;

import javax.persistence.*;

/**
 * User: dimitri
 * Date: 01/04/14
 * Time: 16:53
 * Goal:
 */
@Entity
@Data
@Table(name="ihm_config")
public class IhmConfig extends BaseObject {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;

    private String logo;
}
