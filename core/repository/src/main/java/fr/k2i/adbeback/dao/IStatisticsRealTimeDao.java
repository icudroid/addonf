package fr.k2i.adbeback.dao;

import com.mysema.query.Tuple;
import fr.k2i.adbeback.core.business.ad.Ad;
import fr.k2i.adbeback.core.business.ad.rule.AdService;
import fr.k2i.adbeback.core.business.ad.rule.BrandRule;
import fr.k2i.adbeback.core.business.ad.rule.MultiResponseRule;
import fr.k2i.adbeback.core.business.ad.rule.OpenRule;
import fr.k2i.adbeback.dao.bean.StatisticsAgeSex;
import fr.k2i.adbeback.dao.bean.StatisticsAgeSexBrand;
import fr.k2i.adbeback.dao.bean.StatisticsAgeSexResponse;
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
    List<StatisticsAgeSexResponse> computeResponsesPlayer(OpenRule rule);


    @Transactional
    List<StatisticsAgeSexBrand> computeResponsesPlayer(BrandRule rule);


    @Transactional
    List<Tuple> computeResponsesPlayer(MultiResponseRule rule);

}



