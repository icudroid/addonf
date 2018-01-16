package fr.k2i.adbeback.dao.jpa;

import com.querydsl.jpa.impl.JPAQuery;
import fr.k2i.adbeback.core.business.ad.QSector;
import fr.k2i.adbeback.core.business.ad.Sector;
import fr.k2i.adbeback.core.business.game.QAdGameTransaction;
import fr.k2i.adbeback.core.business.user.MediaUser;
import fr.k2i.adbeback.core.business.user.QMediaUser;
import fr.k2i.adbeback.dao.IMediaDao;
import fr.k2i.adbeback.dao.ISectorDao;
import org.springframework.stereotype.Repository;


@Repository
public class SectorDao extends GenericDaoJpa<Sector, Long> implements ISectorDao{

    /**
     * Constructor that sets the entity to User.class.
     */
    public SectorDao() {
        super(Sector.class);
    }


    @Override
    public Sector findbyCode(String code) {
        QSector sector = QSector.sector;
        JPAQuery<Sector> query = new JPAQuery(getEntityManager());
        query.from(sector).where(sector.code.eq(code));
        return query.select(sector).fetchOne();
    }
}

