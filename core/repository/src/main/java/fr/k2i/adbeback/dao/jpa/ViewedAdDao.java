package fr.k2i.adbeback.dao.jpa;

import com.mysema.query.jpa.impl.JPAQuery;
import fr.k2i.adbeback.core.business.ad.Ad;
import fr.k2i.adbeback.core.business.ad.QViewedAd;
import fr.k2i.adbeback.core.business.ad.ViewedAd;
import fr.k2i.adbeback.core.business.ad.rule.AdService;
import fr.k2i.adbeback.core.business.player.Player;
import fr.k2i.adbeback.dao.IViewedAdDao;
import org.springframework.stereotype.Repository;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: dev
 * Date: 12/01/14
 * Time: 21:15
 * To change this template use File | Settings | File Templates.
 */
@Repository
public class ViewedAdDao extends GenericDaoJpa<ViewedAd, Long> implements IViewedAdDao {

    public ViewedAdDao() {
        super(ViewedAd.class);
    }


    @Override
    public ViewedAd findForToday(Player currentPlayer, AdService adService) {
        QViewedAd viewedAd = QViewedAd.viewedAd;
        JPAQuery query = new JPAQuery(getEntityManager());
        query.from(viewedAd)
                .where(
                        viewedAd.player.eq(currentPlayer).and(viewedAd.adRule.eq(adService)).and(viewedAd.date.eq(new Date()))
                );

        return query.uniqueResult(viewedAd);
    }

    @Override
    public Long computeTodayNbViewGlobal(Ad ad) {
        QViewedAd viewedAd = QViewedAd.viewedAd;
        JPAQuery query = new JPAQuery(getEntityManager());
        query.from(viewedAd)
                .where(
                        viewedAd.date.eq(new Date()).and(viewedAd.adRule.ad.eq(ad))
                );

        return Long.valueOf(query.uniqueResult(viewedAd.nb.sum()));
    }
}
