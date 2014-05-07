package fr.k2i.adbeback.dao.jpa;

import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.types.expr.BooleanExpression;
import com.mysema.query.types.path.PathBuilder;
import fr.k2i.adbeback.core.business.ad.rule.AdService;
import fr.k2i.adbeback.core.business.game.*;
import fr.k2i.adbeback.core.business.player.Player;
import fr.k2i.adbeback.core.business.player.Player_;
import fr.k2i.adbeback.core.business.user.Media;
import fr.k2i.adbeback.dao.utils.CriteriaBuilderHelper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * This class interacts with Spring's HibernateTemplate to save/delete and
 * retrieve User objects.
 *
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 *   Modified by <a href="mailto:dan@getrolling.com">Dan Kibler</a>
 *   Extended to implement Acegi UserDetailsService interface by David Carter david@carter.net
 *   Modified by <a href="mailto:bwnoll@gmail.com">Bryan Noll</a> to work with 
 *   the new BaseDaoHibernate implementation that uses generics.
*/
@Repository("adGameDao")
public class AdGameDao extends GenericDaoJpa<AbstractAdGame, Long> implements fr.k2i.adbeback.dao.IAdGameDao {

    /**
     * Constructor that sets the entity to User.class.
     */
    public AdGameDao() {
        super(AbstractAdGame.class);
    }


    @Override
    public boolean RuleIsUsed(AdService adRule) {
        QAdChoise qAdChoise = QAdChoise.adChoise;
        JPAQuery query = new JPAQuery(getEntityManager());
        query.from(qAdChoise).where(qAdChoise.generatedBy.eq(adRule));
          return query.exists();
    }

    @Override
    public Double sumTransactionForDay(Media media, Date date) {

        QAdGameTransaction transaction = QAdGameTransaction.adGameTransaction;
        QAdChoise adChoise = QAdChoise.adChoise;
        JPAQuery query = new JPAQuery(getEntityManager());

        query.from(transaction).join(transaction.choises,adChoise).where(
                transaction.media.eq(media),
                transaction.generated.eq(date),
                transaction.statusGame.eq(StatusGame.Win)
        );

        return query.uniqueResult(adChoise.winBidPrice.sum());
    }


}

