package fr.k2i.adbeback.dao.jpa;

import com.mysema.query.jpa.impl.JPAQuery;
import fr.k2i.adbeback.core.business.game.QAdGameTransaction;
import fr.k2i.adbeback.core.business.user.MediaUser;
import fr.k2i.adbeback.core.business.user.QMediaUser;
import fr.k2i.adbeback.dao.IMediaDao;
import org.springframework.stereotype.Repository;


@Repository
public class MediaDao extends GenericDaoJpa<MediaUser, Long> implements IMediaDao {

    /**
     * Constructor that sets the entity to User.class.
     */
    public MediaDao() {
        super(MediaUser.class);
    }


    @Override
    public MediaUser findbyExtId(String idPartnerExt) {
        QMediaUser qPartner = QMediaUser.mediaUser;

        JPAQuery query = new JPAQuery(getEntityManager());
        query.from(qPartner).where(qPartner.extId.eq(idPartnerExt));

        return query.uniqueResult(qPartner);
    }

    @Override
    public boolean existTransaction(String idPartnerExt, String idTransactionExt) {
        QAdGameTransaction adGameTransaction = QAdGameTransaction.adGameTransaction;
        JPAQuery query = new JPAQuery(getEntityManager());
        query.from(adGameTransaction).where(adGameTransaction.media.extId.eq(idPartnerExt).and(adGameTransaction.idTransaction.eq(idTransactionExt)));
        return query.exists();
    }
}

