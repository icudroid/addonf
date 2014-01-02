package fr.k2i.adbeback.dao.jpa;

import fr.k2i.adbeback.core.business.media.Media;
import fr.k2i.adbeback.core.business.media.Music;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: dev
 * Date: 01/01/14
 * Time: 15:38
 * To change this template use File | Settings | File Templates.
 */
public interface MusicRepository extends CrudRepository<Music, Long> {

}
