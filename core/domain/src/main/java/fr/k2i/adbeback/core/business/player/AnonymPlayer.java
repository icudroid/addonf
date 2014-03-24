package fr.k2i.adbeback.core.business.player;

import fr.k2i.adbeback.core.business.IMetaData;
import lombok.Data;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * User: dimitri
 * Date: 24/03/14
 * Time: 17:30
 * Goal:
 */
@Data
@Entity
@DiscriminatorValue("anoPlayer")
public class AnonymPlayer extends Player{

}
