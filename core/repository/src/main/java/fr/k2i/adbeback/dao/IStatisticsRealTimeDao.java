package fr.k2i.adbeback.dao;

import com.mysema.query.Tuple;
import fr.k2i.adbeback.core.business.ad.Ad;
import fr.k2i.adbeback.core.business.ad.rule.AdService;
import fr.k2i.adbeback.core.business.ad.rule.BrandRule;
import fr.k2i.adbeback.core.business.ad.rule.MultiResponseRule;
import fr.k2i.adbeback.core.business.ad.rule.OpenRule;
import fr.k2i.adbeback.dao.bean.Responses;
import fr.k2i.adbeback.dao.bean.StatisticsAgeSex;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * User Data Access Object (IGenericDao) interface.
 *
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 */
public interface IStatisticsRealTimeDao {

    @Transactional
    List<StatisticsAgeSex> computeResponsesPlayerKo(AdService service);

    @Transactional
    List<StatisticsAgeSex> computeResponsesPlayerOk(AdService service);

    @Transactional
    Long computeNbView(AdService service);

    @Transactional
    Long computeNbViewGlobal(Ad ad);

    @Transactional
    Long computeNbValidated(Ad ad);

    List<StatisticsAgeSex> computeNoResponses(AdService service);

    List<Tuple> computeResponsesPlayerKo(Ad ad);

    List<Tuple> computeResponsesPlayerOk(Ad ad);

    List<Tuple> computeNoResponses(Ad ad);

    @Transactional
    Long computeNbView(Ad ad);

    @Transactional
    Responses computeResponsesPlayer(OpenRule rule);


    @Transactional
    Responses computeResponsesPlayer(BrandRule rule);


    @Transactional
    Responses computeResponsesPlayer(MultiResponseRule rule);

    @Transactional
    Double computeAverageBid(AdService rule);
}



