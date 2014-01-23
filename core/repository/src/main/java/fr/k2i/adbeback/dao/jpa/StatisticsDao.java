package fr.k2i.adbeback.dao.jpa;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSortedSet;
import com.mysema.query.Tuple;
import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.types.Expression;
import com.mysema.query.types.QTuple;
import com.mysema.query.types.expr.BooleanExpression;
import fr.k2i.adbeback.core.business.ad.Ad;
import fr.k2i.adbeback.core.business.ad.QVideoAd;
import fr.k2i.adbeback.core.business.ad.QViewedAd;
import fr.k2i.adbeback.core.business.ad.rule.*;
import fr.k2i.adbeback.core.business.player.Player;
import fr.k2i.adbeback.core.business.player.QPlayer;
import fr.k2i.adbeback.dao.IStatisticsDao;
import lombok.Data;
import org.joda.time.*;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Query;
import java.util.*;

@Repository
public class StatisticsDao extends GenericDaoJpa<Ad, Long> implements IStatisticsDao {

    public StatisticsDao() {
        super(Ad.class);
    }

    public enum AgeGroup{
        $15_17(15,17),
        $18_24(18,24),
        $25_34(25,34),
        $35_49(35,49),
        $50_64(50,64),
        _65_MORE(65,130);

        private int min;
        private int max;

        private AgeGroup(int min, int max) {
            this.min = min;
            this.max = max;
        }

        public int getMin() {
            return min;
        }

        public int getMax() {
            return max;
        }

        public Date minDate() {
            DateTime now = new DateTime();
            return now.minusYears(this.min).toDate();
        }

        public Date maxDate() {
            DateTime now = new DateTime();
            return now.minusYears(this.max).toDate();
        }
    }


    @Data
    public class Statistics{
        private AdService service;
        private Long count;

        public Statistics(AdService service, Long count) {
            this.service = service;
            this.count = count;
        }
    }


    @Data
    public class StatisticsAge{
        private AgeGroup ageGroup;
        private Long count;

        public StatisticsAge(AgeGroup ageGroup, Long count) {
            this.ageGroup = ageGroup;
            this.count = count;
        }
    }


    @Override
    public long nbGlobal(Ad ad, Date start, Date end){
        QViewedAd viewedAd = QViewedAd.viewedAd;

        JPAQuery query = new JPAQuery(getEntityManager());
        query.from(viewedAd)
                .where(
                        viewedAd.adRule.ad.eq(ad).and(viewedAd.date.between(start, end))

                );
        return query.count();
    }


    @Override
    public List<Statistics> nbGlobalGroupByService(Ad ad, Date start, Date end, List<Class<? extends AdService>> services){
        QViewedAd viewedAd = QViewedAd.viewedAd;

        JPAQuery query = new JPAQuery(getEntityManager());
        BooleanExpression predicat = viewedAd.adRule.ad.eq(ad).and(viewedAd.date.between(start, end));

        for (Class<? extends AdService> service : services) {
            predicat = predicat.and(viewedAd.adRule.instanceOf(service));
        }

        query.from(viewedAd)
                .where(
                        predicat
                );

        query.groupBy(viewedAd.adRule);
        List<Statistics> res = new ArrayList<Statistics>();
        List<Tuple> list = query.list(viewedAd.count(),viewedAd.adRule);
        for (Tuple tuple : list) {
            Long count = tuple.get(viewedAd.count());
            AdService adService = tuple.get(viewedAd.adRule);
            res.add(new Statistics(adService,count));
        }

        return res;
    }


    @Override
    public StatisticsAge nbGlobalGroupAge(Ad ad, Date start, Date end, AgeGroup ageGroup) {
        QViewedAd viewedAd = QViewedAd.viewedAd;

        JPAQuery query = new JPAQuery(getEntityManager());
        BooleanExpression predicat = viewedAd.adRule.ad.eq(ad).and(viewedAd.date.between(start, end));

        query.from(viewedAd)
                .where(
                        predicat.and(viewedAd.player.birthday.between(ageGroup.minDate(), ageGroup.maxDate()))
                );

        return new StatisticsAge(ageGroup,query.uniqueResult(viewedAd.count()));
    }

    @Override
    public List<StatisticsAge> nbGlobalGroupAge(Ad ad, Date start, Date end) {

        List<StatisticsAge> res = new ArrayList<StatisticsAge>();
        for (AgeGroup ageGroup : AgeGroup.values()) {
            res.add(nbGlobalGroupAge(ad, start, end,ageGroup));
        }

        return res;
    }





}

