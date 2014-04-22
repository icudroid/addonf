package fr.k2i.adbeback.service.impl;

import com.mysema.query.Tuple;
import fr.k2i.adbeback.core.business.ad.rule.AdService;
import fr.k2i.adbeback.core.business.ad.rule.BrandRule;
import fr.k2i.adbeback.core.business.ad.rule.MultiResponseRule;
import fr.k2i.adbeback.core.business.ad.rule.OpenRule;
import fr.k2i.adbeback.core.business.player.AgeGroup;
import fr.k2i.adbeback.core.business.player.Sex;
import fr.k2i.adbeback.dao.IStatisticsDao;
import fr.k2i.adbeback.dao.IStatisticsRealTimeDao;
import fr.k2i.adbeback.service.StatisticsRealTime;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * User: dimitri
 * Date: 22/04/14
 * Time: 12:30
 * Goal:
 */
public class StatisticsRealTimeImpl implements StatisticsRealTime {
    @Autowired
    private IStatisticsRealTimeDao statisticsRealTimeDao;

    @Data
    public class StatisticsAgeSex{
        private AgeGroup ageGroup;
        private Sex sex;
        private List<String> responses;
        private Long nb;
        private Integer type;
    }


    @Transactional
    @Override
    public List<StatisticsAgeSex> computeResponsesPlayer(AdService service){


        if (service instanceof OpenRule) {
            OpenRule rule = (OpenRule) service;
            List list = statisticsRealTimeDao.computeResponsesPlayer(rule);

        }else if (service instanceof MultiResponseRule) {
            MultiResponseRule rule = (MultiResponseRule) service;
            List list = statisticsRealTimeDao.computeResponsesPlayer(rule);

        }else if (service instanceof BrandRule) {
            BrandRule rule = (BrandRule) service;
            List list = statisticsRealTimeDao.computeResponsesPlayer(rule);
        }

        return null;
    }


}
