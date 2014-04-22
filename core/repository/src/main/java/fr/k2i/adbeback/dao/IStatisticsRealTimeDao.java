package fr.k2i.adbeback.dao;

import com.mysema.query.Tuple;
import fr.k2i.adbeback.core.business.ad.Ad;
import fr.k2i.adbeback.core.business.ad.rule.AdService;
import fr.k2i.adbeback.core.business.ad.rule.BrandRule;
import fr.k2i.adbeback.core.business.ad.rule.MultiResponseRule;
import fr.k2i.adbeback.core.business.ad.rule.OpenRule;
import fr.k2i.adbeback.core.business.statistic.Statistics;
import fr.k2i.adbeback.core.business.statistic.StatisticsValidated;
import fr.k2i.adbeback.core.business.statistic.StatisticsViewed;
import fr.k2i.adbeback.dao.jpa.StatisticsDao;
import fr.k2i.adbeback.dao.jpa.StatisticsRealTimeDao;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * User Data Access Object (IGenericDao) interface.
 *
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 */
public interface IStatisticsRealTimeDao {


    @Transactional
    List computeResponsesPlayer(OpenRule rule);

    @Transactional
    List<Tuple> computeResponsesPlayer(BrandRule rule);

    @Transactional
    List<Tuple> computeResponsesPlayer(MultiResponseRule rule);



    @Transactional
    List<Tuple> computeResponsesPlayerKo(AdService service);

    @Transactional
    List<Tuple> computeResponsesPlayerOk(AdService service);

    @Transactional
    Long computeNbView(AdService service);

    @Transactional
    Long computeNbViewGlobal();
}

