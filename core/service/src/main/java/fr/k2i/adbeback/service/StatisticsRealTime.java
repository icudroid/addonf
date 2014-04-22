package fr.k2i.adbeback.service;

import fr.k2i.adbeback.core.business.ad.rule.AdService;
import fr.k2i.adbeback.dao.IStatisticsDao;
import fr.k2i.adbeback.service.impl.StatisticsRealTimeImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * User: dimitri
 * Date: 22/04/14
 * Time: 12:29
 * Goal:
 */
@Service
public interface StatisticsRealTime {


    @Transactional
    List<StatisticsRealTimeImpl.StatisticsAgeSex> computeResponsesPlayer(AdService service);
}
