package fr.k2i.adbeback.dao.jpa;

import com.mysema.query.jpa.impl.JPAQuery;
import fr.k2i.adbeback.core.business.game.QAdGameTransaction;
import fr.k2i.adbeback.core.business.user.Media;
import fr.k2i.adbeback.core.business.user.QMedia;
import fr.k2i.adbeback.dao.IMediaDao;
import org.springframework.stereotype.Repository;


@Repository
public class MediaDao extends GenericDaoJpa<Media, Long> implements IMediaDao {

    /**
     * Constructor that sets the entity to User.class.
     */
    public MediaDao() {
        super(Media.class);
    }


    @Override
    public Media findbyExtId(String idPartnerExt) {
        QMedia qPartner = QMedia.media;

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

