package fr.k2i.adbeback.dao.jpa;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import fr.k2i.adbeback.core.business.game.QAdGameTransaction;
import fr.k2i.adbeback.core.business.user.*;
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
    public Media findByExtId(String idPartnerExt) {
        QMedia media = QMedia.media;

        JPAQuery<Media> query = new JPAQuery(getEntityManager());
        query.from(media).where(media.extId.eq(idPartnerExt));

        return query.select(media).fetchOne();
    }


    @Override
    public Media findByMediaUser(MediaUser user) {
        QMedia media = QMedia.media;

        JPAQuery<Media> query = new JPAQuery(getEntityManager());
        query.from(media).where(media.user.eq(user));

        return query.select(media).fetchOne();
    }

    @Override
    public CategoryPrice findCategoryPrice(Long idMedia, MediaType mediaType,String categoryKey) {
        QMedia media = QMedia.media;
        QCategoryPrice qCategoryPrice = QCategoryPrice.categoryPrice;

        JPAQuery<CategoryPrice> query = new JPAQuery(getEntityManager());
        query.from(media).join(media.minPriceByMediaType,qCategoryPrice)
                .where(media.id.eq(idMedia).and(qCategoryPrice.category.key.eq(categoryKey).and(qCategoryPrice.mediaType.eq(mediaType))));

        return query.select(qCategoryPrice).fetchOne();
    }


    @Override
    public boolean existTransaction(String idPartnerExt, String idTransactionExt) {
        QAdGameTransaction adGameTransaction = QAdGameTransaction.adGameTransaction;
        JPAQuery query = new JPAQuery(getEntityManager());
        query.from(adGameTransaction).where(adGameTransaction.media.extId.eq(idPartnerExt).and(adGameTransaction.idTransaction.eq(idTransactionExt)));
        return (boolean) query.select(query.exists()).fetchOne();
    }
}

