package fr.k2i.adbeback.dao.jpa;

import com.mysema.query.Tuple;
import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.types.expr.BooleanExpression;
import com.mysema.query.types.path.PathBuilder;
import fr.k2i.adbeback.core.business.ad.Ad;
import fr.k2i.adbeback.core.business.ad.QViewedAd;
import fr.k2i.adbeback.core.business.ad.rule.*;
import fr.k2i.adbeback.core.business.country.City;
import fr.k2i.adbeback.core.business.game.*;
import fr.k2i.adbeback.core.business.player.AgeGroup;
import fr.k2i.adbeback.core.business.player.Player;
import fr.k2i.adbeback.core.business.player.QPlayer;
import fr.k2i.adbeback.core.business.player.Sex;
import fr.k2i.adbeback.core.business.statistic.*;
import fr.k2i.adbeback.dao.IStatisticsDao;
import fr.k2i.adbeback.dao.IStatisticsRealTimeDao;
import lombok.Data;
import org.joda.time.LocalDate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.*;

@Repository
public class StatisticsRealTimeDao  implements IStatisticsRealTimeDao {
    @PersistenceContext
    private javax.persistence.EntityManager entityManager;


    
    
    @Transactional
    @Override
    public List computeResponsesPlayer(OpenRule rule){

        LocalDate now = LocalDate.now();
        Date d0 = now.toDate();
        Date d1 = now.plusDays(1).toDate();

        Query query =  entityManager.createQuery("select player.sex,player.ageGroup,response.generatedBy from AdChoise adChoies join adChoies.adGame game join game.player player join game.score score join score.answers answer join answer.responses response where adChoies.generatedBy=:rule and game.generated between :start and :end group by player.sex,player.ageGroup,response.generatedBy");
        query.setParameter("rule",rule);
        query.setParameter("start",d0);
        query.setParameter("end",d1);

        return query.getResultList();

    }

    @Override
    public List<Tuple> computeResponsesPlayer(BrandRule rule) {
        LocalDate now = LocalDate.now();
        Date d0 = now.toDate();
        Date d1 = now.plusDays(1).toDate();

        Query query =  entityManager.createQuery("select player.sex,player.ageGroup,response.brand from AdChoise adChoies join adChoies.adGame game join game.player player join game.score score join score.answers answer join answer.responses response where adChoies.generatedBy=:rule and game.generated between :start and :end group by player.sex,player.ageGroup,response.brand");
        query.setParameter("rule",rule);
        query.setParameter("start",d0);
        query.setParameter("end",d1);

        return query.getResultList();
    }

    @Override
    public List<Tuple> computeResponsesPlayer(MultiResponseRule rule) {
        LocalDate now = LocalDate.now();
        Date d0 = now.toDate();
        Date d1 = now.plusDays(1).toDate();

        Query query =  entityManager.createQuery("select player.sex,player.ageGroup,response.generatedBy from AdChoise adChoies join adChoies.adGame game join game.player player join game.score score join score.answers answer join answer.responses response where adChoies.generatedBy=:rule and game.generated between :start and :end group by player.sex,player.ageGroup,response.generatedBy");
        query.setParameter("rule",rule);
        query.setParameter("start",d0);
        query.setParameter("end",d1);

        return query.getResultList();
    }

    @Override
    public List<Tuple> computeResponsesPlayerKo(AdService service) {


        return null;
    }


    @Override
    public List<Tuple> computeResponsesPlayerOk(AdService service) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }


    @Transactional
    @Override
    public Long computeNbView(AdService service){
        return null;
    }


    @Transactional
    @Override
    public Long computeNbViewGlobal(){
        return null;
    }


}

