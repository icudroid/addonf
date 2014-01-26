package fr.k2i.adbeback.dao.jpa;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.types.expr.BooleanExpression;
import com.mysema.query.types.path.PathBuilder;
import fr.k2i.adbeback.core.business.ad.rule.AdRule;
import fr.k2i.adbeback.core.business.ad.rule.AdService;
import fr.k2i.adbeback.core.business.game.*;
import fr.k2i.adbeback.core.business.media.Media;
import fr.k2i.adbeback.core.business.media.Music;
import fr.k2i.adbeback.core.business.player.Player;
import fr.k2i.adbeback.core.business.player.Player_;
import fr.k2i.adbeback.dao.utils.CriteriaBuilderHelper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

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

	@SuppressWarnings("unchecked")
	public List<AbstractAdGame> findWonAdGame(Long idPlayer) throws Exception {

        CriteriaBuilderHelper<AbstractAdGame> helper = new CriteriaBuilderHelper(getEntityManager(),AbstractAdGame.class);

        Calendar now = new GregorianCalendar();
        now.add(Calendar.DATE, -7);

        helper.criteriaHelper.and(
                helper.criteriaHelper.equal(helper.rootHelper.join(AbstractAdGame_.player).get(Player_.id), idPlayer),
                helper.criteriaHelper.equal(helper.rootHelper.get(AbstractAdGame_.statusGame), StatusGame.Win),
                helper.criteriaHelper.greaterThan(helper.rootHelper.get(AbstractAdGame_.generated), now.getTime())
        );

        helper.criteriaHelper.desc(helper.rootHelper.get(AbstractAdGame_.generated));

		return helper.getResultList();
	}

    @Override
    public Page<Media> getDownloadedMusic(Player player, long genreId, String req, Pageable pageable) {
        QAdGameMedia qAdGameMedia = QAdGameMedia.adGameMedia;
        JPAQuery query = new JPAQuery(getEntityManager());
        BooleanExpression predicat = qAdGameMedia.player.eq(player).and(qAdGameMedia.statusGame.eq(StatusGame.Win));
        
        if(genreId>0){
           predicat = predicat.and(qAdGameMedia.medias.any().categories.any().id.eq(genreId));
        }

        if(!StringUtils.isEmpty(req)){
            predicat = predicat.and(qAdGameMedia.medias.any().title.containsIgnoreCase(req));
        }
        
        query.from(qAdGameMedia).where(
                predicat.and(qAdGameMedia.medias.any().instanceOf(Music.class))
        );


        query
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(qAdGameMedia.medias.any().title.asc());

        return new PageImpl<Media>(query.list(qAdGameMedia.medias.any()),pageable,query.count());
    }

    @Override
    public Boolean musicIsWonByPlayer(Player player, Long musicId) {
        QAdGameMedia qAdGameMedia = QAdGameMedia.adGameMedia;
        JPAQuery query = new JPAQuery(getEntityManager());

        query.from(qAdGameMedia).where(
                        qAdGameMedia.player.eq(player)
                        .and(qAdGameMedia.statusGame.eq(StatusGame.Win))
                        .and(qAdGameMedia.medias.any().instanceOf(Music.class))
                        .and(qAdGameMedia.medias.any().id.eq(musicId))
        );

        return query.exists();

    }

    @Override
    public boolean isGeneratedWithRule(AdService adRule) {
        QAdGame qAdGame = QAdGame.adGame;
        JPAQuery query = new JPAQuery(getEntityManager());

        PathBuilder<AdChoise> choise = new PathBuilder<AdChoise>(AdChoise.class, "choise");
        query.from(qAdGame).join(qAdGame.choises, choise)
                .where(choise.get("generatedBy").eq(adRule));

        return query.exists();
    }


}

