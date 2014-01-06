package fr.k2i.adbeback.dao.jpa;

import fr.k2i.adbeback.core.business.media.Music;
import fr.k2i.adbeback.core.business.push.HomePush;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: dev
 * Date: 01/01/14
 * Time: 15:38
 * To change this template use File | Settings | File Templates.
 */
@Repository
public interface HomePushRepository extends CrudRepository<HomePush, Long> {
    List<HomePush> findByStartDateLessThanAndEndDateGreaterThan(Date d1,Date d2);
}
