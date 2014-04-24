package fr.k2i.adbeback.dao.jpa;

import com.mysema.query.Tuple;
import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.types.expr.BooleanExpression;
import com.mysema.query.types.path.PathBuilder;
import fr.k2i.adbeback.core.business.ad.Ad;
import fr.k2i.adbeback.core.business.ad.QViewedAd;
import fr.k2i.adbeback.core.business.ad.rule.*;
import fr.k2i.adbeback.core.business.country.City;
import fr.k2i.adbeback.core.business.player.AgeGroup;
import fr.k2i.adbeback.core.business.player.Player;
import fr.k2i.adbeback.core.business.player.Sex;
import fr.k2i.adbeback.core.business.statistic.*;
import fr.k2i.adbeback.core.business.statistic.Statistics;
import fr.k2i.adbeback.dao.IStatisticsDao;
import fr.k2i.adbeback.dao.bean.*;
import fr.k2i.adbeback.dao.jpa.GenericDaoJpa;
import lombok.Data;
import org.joda.time.*;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.*;

@Repository
public class StatisticsDao extends GenericDaoJpa<Statistics, Long> implements IStatisticsDao {

    public StatisticsDao() {
        super(Statistics.class);
    }


    @Override
    public void removeAllInDay(Date day) {
        Query query= getEntityManager().createQuery("delete Statistics where day = :day").setParameter("day", day);
        query.executeUpdate();
    }



    @Override
    public List<StatisticsViewed> doStatisticsViewedForDay(Date day) {

        QViewedAd viewedAd = QViewedAd.viewedAd;
        JPAQuery jpaQuery = new JPAQuery(getEntityManager());

        LocalDate d = new LocalDate(day);
        LocalDate start =new LocalDate(d.getYear(),d.getMonthOfYear(),d.getDayOfMonth());
        PathBuilder<Player> player= new PathBuilder<Player>(Player.class, "player");

        jpaQuery.from(viewedAd).join(viewedAd.player, player).join(player.get("address.city")).join(viewedAd.adRule);
        jpaQuery.where(viewedAd.date.between(start.toDate(), start.plusDays(1).toDate()));
        jpaQuery.groupBy(player.get("address.city")).groupBy(player.get("sex")).groupBy(player.get("ageGroup")).groupBy(viewedAd.adRule);


        List<StatisticsViewed> res = new ArrayList<StatisticsViewed>();
        List<Tuple> list = jpaQuery.list(viewedAd.count(), player.get("address.city"), player.get("sex"), player.get("ageGroup"), viewedAd.adRule);
        for (Tuple tuple : list) {
            StatisticsViewed viewed = new StatisticsViewed(day, (Sex) tuple.get(player.get("sex")), (City) tuple.get(player.get("address.city")), tuple.get(viewedAd.adRule), (AgeGroup) tuple.get(player.get("ageGroup")), tuple.get(viewedAd.count()));
            res.add((StatisticsViewed) save(viewed));
        }

        return res;
    }


    @Override
    public List<StatisticsValidated> doStatisticsValidatedForDay(Date day) {

        Query query = getEntityManager().createQuery("select count(answer), game.player.address.city, game.player.sex, game.player.ageGroup, choise.generatedBy from AbstractAdGame game " +
                "join game.choises  as choise " +
                "join game.score.answers as answer " +
                "where " +
                "game.generated between :start and :end " +
                "and answer.responses = choise.corrects " +
                "group by game.player.address.city, game.player.sex, game.player.ageGroup, choise.generatedBy");

        LocalDate d = new LocalDate(day);
        LocalDate start =new LocalDate(d.getYear(),d.getMonthOfYear(),d.getDayOfMonth());
        query.setParameter("start",start.toDate());
        query.setParameter("end",start.plusDays(1).toDate());

        List resultList = query.getResultList();


        List<StatisticsValidated> res = new ArrayList<StatisticsValidated>();

        for (Object o : resultList) {
            Object results[] = (Object[]) o;
            StatisticsValidated validated = new StatisticsValidated(day, (Sex) results[2], (City) results[1], (AdService) results[4], (AgeGroup) results[3], (Long) results[0]);
            res.add((StatisticsValidated) save(validated));
        }

        return res;
    }



    private BooleanExpression globalDefaultPredicat(Ad ad, Date start, Date end, QStatistics qStatistics) {
        BooleanExpression predicat = qStatistics.service.ad.eq(ad);
        if(start != null && end != null){
            predicat.and(qStatistics.day.between(start, end));
        }else if(start != null && end == null){
            predicat.and(qStatistics.day.gt(start));
        }else if(start == null && end != null){
            predicat.and(qStatistics.day.lt(end));
        }
        return predicat;
    }



    private BooleanExpression validatedDefaultPredicat(Ad ad, Date start, Date end, QStatisticsValidated qStatistics) {
        BooleanExpression predicat = qStatistics.service.ad.eq(ad);
        if(start != null && end != null){
            predicat.and(qStatistics.day.between(start, end));
        }else if(start != null && end == null){
            predicat.and(qStatistics.day.gt(start));
        }else if(start == null && end != null){
            predicat.and(qStatistics.day.lt(end));
        }
        return predicat;
    }






    @Override
    public List<StatisticsAge> nbGlobalGroupByGroupAge(Ad ad, Date start, Date end, AdService service) {
        QStatistics qStatistics = QStatistics.statistics;
        JPAQuery query = new JPAQuery(getEntityManager());

        BooleanExpression predicat = globalDefaultPredicat(ad, start, end, qStatistics);

        if(service!=null){
            predicat = predicat.and(qStatistics.service.eq(service));
        }

        query.from(qStatistics).where(predicat);

        query.groupBy(qStatistics.ageGroup);

        List<Tuple> list = query.list(qStatistics.count(), qStatistics.ageGroup);
        List<StatisticsAge> res = new ArrayList<StatisticsAge>();
        for (Tuple tuple : list) {
            Long count = tuple.get(qStatistics.count());
            AgeGroup ageGroup = tuple.get(qStatistics.ageGroup);
            res.add(new StatisticsAge(ageGroup,count));
        }

        return res;
    }

    @Override
    public List<StatisticsAge> nbValidatedGroupByGroupAge(Ad ad, Date start, Date end, AdService service) {
        QStatisticsValidated qStatistics = QStatisticsValidated.statisticsValidated;
        JPAQuery query = new JPAQuery(getEntityManager());

        BooleanExpression predicat = validatedDefaultPredicat(ad, start, end, qStatistics);

        if(service!=null){
            predicat = predicat.and(qStatistics.service.eq(service));
        }

        query.from(qStatistics).where(predicat);

        query.groupBy(qStatistics.ageGroup);

        List<Tuple> list = query.list(qStatistics.count(), qStatistics.ageGroup);
        List<StatisticsAge> res = new ArrayList<StatisticsAge>();
        for (Tuple tuple : list) {
            Long count = tuple.get(qStatistics.count());
            AgeGroup ageGroup = tuple.get(qStatistics.ageGroup);
            res.add(new StatisticsAge(ageGroup,count));
        }

        return res;

    }

    @Override
    public List<StatisticsAgeSex> nbGlobalGroupByGroupAgeAndSex(Ad ad, Date start, Date end, AdService service) {
        QStatistics qStatistics = QStatistics.statistics;
        JPAQuery query = new JPAQuery(getEntityManager());

        BooleanExpression predicat = globalDefaultPredicat(ad, start, end, qStatistics);

        if(service!=null){
            predicat = predicat.and(qStatistics.service.eq(service));
        }

        query.from(qStatistics).where(predicat);

        query.groupBy(qStatistics.ageGroup).groupBy(qStatistics.sex);

        List<Tuple> list = query.list(qStatistics.count(), qStatistics.ageGroup,qStatistics.sex);
        List<StatisticsAgeSex> res = new ArrayList<StatisticsAgeSex>();
        for (Tuple tuple : list) {
            Long count = tuple.get(qStatistics.count());
            AgeGroup ageGroup = tuple.get(qStatistics.ageGroup);
            Sex sex = tuple.get(qStatistics.sex);
            res.add(new StatisticsAgeSex(ageGroup,sex,count));
        }

        return res;
    }

    @Override
    public List<StatisticsAgeSex> nbValidatedGroupByGroupAgeAndSex(Ad ad, Date start, Date end, AdService service) {
        QStatisticsValidated qStatistics = QStatisticsValidated.statisticsValidated;
        JPAQuery query = new JPAQuery(getEntityManager());

        BooleanExpression predicat = validatedDefaultPredicat(ad, start, end, qStatistics);

        if(service!=null){
            predicat = predicat.and(qStatistics.service.eq(service));
        }

        query.from(qStatistics).where(predicat);

        query.groupBy(qStatistics.ageGroup).groupBy(qStatistics.sex);

        List<Tuple> list = query.list(qStatistics.count(), qStatistics.ageGroup,qStatistics.sex);
        List<StatisticsAgeSex> res = new ArrayList<StatisticsAgeSex>();
        for (Tuple tuple : list) {
            Long count = tuple.get(qStatistics.count());
            AgeGroup ageGroup = tuple.get(qStatistics.ageGroup);
            Sex sex = tuple.get(qStatistics.sex);
            res.add(new StatisticsAgeSex(ageGroup,sex,count));
        }

        return res;
    }

    @Override
    public List<StatisticsCity> nbGlobalGroupByCity(Ad ad, Date start, Date end, AdService service) {
        QStatistics qStatistics = QStatistics.statistics;
        JPAQuery query = new JPAQuery(getEntityManager());

        BooleanExpression predicat = globalDefaultPredicat(ad, start, end, qStatistics);

        if(service!=null){
            predicat = predicat.and(qStatistics.service.eq(service));
        }

        query.from(qStatistics).where(predicat);

        query.groupBy(qStatistics.city);

        List<Tuple> list = query.list(qStatistics.count(), qStatistics.city);
        List<StatisticsCity> res = new ArrayList<StatisticsCity>();
        for (Tuple tuple : list) {
            Long count = tuple.get(qStatistics.count());
            City city = tuple.get(qStatistics.city);
            res.add(new StatisticsCity(city,count));
        }

        return res;
    }

    @Override
    public List<StatisticsCity> nbValidatedGroupByCity(Ad ad, Date start, Date end, AdService service) {
        QStatisticsValidated qStatistics = QStatisticsValidated.statisticsValidated;
        JPAQuery query = new JPAQuery(getEntityManager());

        BooleanExpression predicat = validatedDefaultPredicat(ad, start, end, qStatistics);

        if(service!=null){
            predicat = predicat.and(qStatistics.service.eq(service));
        }

        query.from(qStatistics).where(predicat);

        query.groupBy(qStatistics.city);

        List<Tuple> list = query.list(qStatistics.count(), qStatistics.city);
        List<StatisticsCity> res = new ArrayList<StatisticsCity>();
        for (Tuple tuple : list) {
            Long count = tuple.get(qStatistics.count());
            City city = tuple.get(qStatistics.city);
            res.add(new StatisticsCity(city,count));
        }

        return res;
    }

    @Override
    public List<StatisticsSex> nbGlobalGroupBySex(Ad ad, Date start, Date end, AdService service) {
        QStatistics qStatistics = QStatistics.statistics;
        JPAQuery query = new JPAQuery(getEntityManager());

        BooleanExpression predicat = globalDefaultPredicat(ad, start, end, qStatistics);

        if(service!=null){
            predicat = predicat.and(qStatistics.service.eq(service));
        }

        query.from(qStatistics).where(predicat);

        query.groupBy(qStatistics.sex);

        List<Tuple> list = query.list(qStatistics.count(), qStatistics.sex);
        List<StatisticsSex> res = new ArrayList<StatisticsSex>();
        for (Tuple tuple : list) {
            Long count = tuple.get(qStatistics.count());
            Sex sex = tuple.get(qStatistics.sex);
            res.add(new StatisticsSex(sex,count));
        }

        return res;
    }

    @Override
    public List<StatisticsSex> nbValidatedGroupBySex(Ad ad, Date start, Date end, AdService service) {
        QStatisticsValidated qStatistics = QStatisticsValidated.statisticsValidated;
        JPAQuery query = new JPAQuery(getEntityManager());

        BooleanExpression predicat = validatedDefaultPredicat(ad, start, end, qStatistics);

        if(service!=null){
            predicat = predicat.and(qStatistics.service.eq(service));
        }

        query.from(qStatistics).where(predicat);

        query.groupBy(qStatistics.sex);

        List<Tuple> list = query.list(qStatistics.count(), qStatistics.sex);
        List<StatisticsSex> res = new ArrayList<StatisticsSex>();
        for (Tuple tuple : list) {
            Long count = tuple.get(qStatistics.count());
            Sex sex = tuple.get(qStatistics.sex);
            res.add(new StatisticsSex(sex,count));
        }

        return res;
    }

    @Override
    public long nbGlobal(Ad ad, Date start, Date end, AdService service) {
        QStatistics qStatistics = QStatistics.statistics;
        JPAQuery query = new JPAQuery(getEntityManager());

        BooleanExpression predicat = globalDefaultPredicat(ad, start, end, qStatistics);

        if(service!=null){
            predicat = predicat.and(qStatistics.service.eq(service));
        }

        query.from(qStatistics).where(predicat);

        return query.count();
    }

    @Override
    public long nbValidated(Ad ad, Date start, Date end, AdService service) {
        QStatisticsValidated qStatistics = QStatisticsValidated.statisticsValidated;
        JPAQuery query = new JPAQuery(getEntityManager());

        BooleanExpression predicat = validatedDefaultPredicat(ad, start, end, qStatistics);

        if(service!=null){
            predicat = predicat.and(qStatistics.service.eq(service));
        }

        query.from(qStatistics).where(predicat);

        return query.count();
    }


    /*****************************************************************************************************************/


/*
    @Override
    public long nbGlobal(Ad ad) {
        return nbGlobal(ad,null,null);
    }

    @Override
    public long nbGlobal(Ad ad, Date start, Date end){
        QStatistics qStatistics = QStatistics.statistics;
        JPAQuery query = new JPAQuery(getEntityManager());
        query.from(qStatistics);
        query.where(globalDefaultPredicat(ad, start, end, qStatistics));
        return query.count();
    }



    @Override
    public List<Statistics> nbGlobalGroupByService(Ad ad){
        return nbGlobalGroupByService(ad,null,null,null);
    }


    @Override
    public List<Statistics> nbGlobalGroupByService(Ad ad, Class<? extends AdService> service){
        return nbGlobalGroupByService(ad,null,null,service);
    }

    @Override
    public List<Statistics> nbGlobalGroupByService(Ad ad, Date start, Date end, Class<? extends AdService> service){
        QStatistics qStatistics = QStatistics.statistics;
        JPAQuery query = new JPAQuery(getEntityManager());
        BooleanExpression predicat = globalDefaultPredicat(ad, start, end, qStatistics);

        if(service!=null){
            predicat = predicat.and(qStatistics.service.instanceOf(service));
        }

        query.from(qStatistics).where(predicat);

        query.groupBy(qStatistics.service);
        List<Statistics> res = new ArrayList<Statistics>();
        List<Tuple> list = query.list(qStatistics.count(),qStatistics.service);
        for (Tuple tuple : list) {
            Long count = tuple.get(qStatistics.count());
            AdService adService = tuple.get(qStatistics.service);
            res.add(new Statistics(adService,count));
        }

        return res;
    }





    @Override
    public StatisticsAge nbGlobalGroupByGroupAge(Ad ad, AgeGroup ageGroup) {
        return nbGlobalGroupByGroupAge(ad,null,null,ageGroup);
    }

    @Override
    public StatisticsAge nbGlobalGroupByGroupAge(Ad ad, Date start, Date end, AgeGroup ageGroup) {
        QStatistics qStatistics = QStatistics.statistics;
        JPAQuery query = new JPAQuery(getEntityManager());

        BooleanExpression predicat = globalDefaultPredicat(ad, start, end, qStatistics);

        query.from(qStatistics).where(predicat.and(qStatistics.ageGroup.eq(ageGroup)));

        return new StatisticsAge(ageGroup,query.uniqueResult(qStatistics.count()));
    }



    @Override
    public StatisticsAgeSex nbGlobalGroupByGroupAgeAndSex(Ad ad, AgeGroup ageGroup, Sex sex) {
        return nbGlobalGroupByGroupAgeAndSex(ad, null, null, ageGroup, sex);
    }

    @Override
    public StatisticsAgeSex nbGlobalGroupByGroupAgeAndSex(Ad ad, Date start, Date end, AgeGroup ageGroup, Sex sex) {
        QStatistics qStatistics = QStatistics.statistics;
        JPAQuery query = new JPAQuery(getEntityManager());

        BooleanExpression predicat = globalDefaultPredicat(ad, start, end, qStatistics);

        query.from(qStatistics).where(predicat.and(qStatistics.ageGroup.eq(ageGroup)).and(qStatistics.sex.eq(sex)));

        return new StatisticsAgeSex(ageGroup,sex,query.uniqueResult(qStatistics.count()));
    }



    @Override
    public List<StatisticsAge> nbGlobalGroupByGroupAge(Ad ad) {
        return nbGlobalGroupByGroupAge(ad,null,null);
    }


    @Override
    public List<StatisticsAge> nbGlobalGroupByGroupAge(Ad ad, Date start, Date end) {

        List<StatisticsAge> res = new ArrayList<StatisticsAge>();
        for (AgeGroup ageGroup : AgeGroup.values()) {
            res.add(nbGlobalGroupByGroupAge(ad, start, end, ageGroup));
        }

        return res;
    }


    @Override
    public List<StatisticsAgeSex> nbGlobalGroupByGroupAgeAndSex(Ad ad) {
        return nbGlobalGroupByGroupAgeAndSex(ad,(Date)null,(Date)null);
    }


    @Override
    public List<StatisticsAgeSex> nbGlobalGroupByGroupAgeAndSex(Ad ad, Date start, Date end) {

        List<StatisticsAgeSex> res = new ArrayList<StatisticsAgeSex>();
        for (AgeGroup ageGroup : AgeGroup.values()) {
            Sex[] values = Sex.values();
            for (Sex sex : values) {
                res.add(nbGlobalGroupByGroupAgeAndSex(ad, start, end, ageGroup, sex));
            }
        }

        return res;
    }



    @Override
    public List<StatisticsCity> nbGlobalGroupByCity(Ad ad) {
        return nbGlobalGroupByCity(ad,null,null);
    }

    @Override
    public List<StatisticsCity> nbGlobalGroupByCity(Ad ad, Date start, Date end) {
        QStatistics qStatistics = QStatistics.statistics;
        JPAQuery query = new JPAQuery(getEntityManager());

        BooleanExpression predicat = globalDefaultPredicat(ad, start, end, qStatistics);

        query.from(qStatistics).where(predicat);

        query.groupBy(qStatistics.city);

        List<Tuple> list = query.list(qStatistics.count(),qStatistics.city);
        List<StatisticsCity> res =new ArrayList<StatisticsCity>();
        for (Tuple tuple : list) {
            res.add(new StatisticsCity(tuple.get(qStatistics.city),tuple.get(qStatistics.count())));
        }

        return res;
    }



    */
/***************************************************************************************************************//*








    @Override
    public long nbValidated(Ad ad) {
        return nbGlobal(ad,null,null);
    }

    @Override
    public long nbValidated(Ad ad, Date start, Date end){
        QStatisticsValidated qStatistics = QStatisticsValidated.statisticsValidated;
        JPAQuery query = new JPAQuery(getEntityManager());
        query.from(qStatistics);
        query.where(validatedDefaultPredicat(ad, start, end, qStatistics));
        return query.count();
    }



    @Override
    public List<Statistics> nbValidatedGroupByService(Ad ad){
        return nbValidatedGroupByService(ad,null,null,null);
    }


    @Override
    public List<Statistics> nbValidatedGroupByService(Ad ad, Class<? extends AdService> service){
        return nbValidatedGroupByService(ad,null,null,service);
    }

    @Override
    public List<Statistics> nbValidatedGroupByService(Ad ad, Date start, Date end, Class<? extends AdService> service){
        QStatisticsValidated qStatistics = QStatisticsValidated.statisticsValidated;
        JPAQuery query = new JPAQuery(getEntityManager());
        BooleanExpression predicat = validatedDefaultPredicat(ad, start, end, qStatistics);

        if(service!=null){
            predicat = predicat.and(qStatistics.service.instanceOf(service));
        }

        query.from(qStatistics).where(predicat);

        query.groupBy(qStatistics.service);
        List<Statistics> res = new ArrayList<Statistics>();
        List<Tuple> list = query.list(qStatistics.count(),qStatistics.service);
        for (Tuple tuple : list) {
            Long count = tuple.get(qStatistics.count());
            AdService adService = tuple.get(qStatistics.service);
            res.add(new Statistics(adService,count));
        }

        return res;
    }


    @Override
    public Statistics nbValidatedForService(Ad ad, AdService service){
        return nbValidatedForService(ad, null, null, service);
    }

    @Override
    public Statistics nbValidatedForService(Ad ad, Date start, Date end, AdService service){
        QStatisticsValidated qStatistics = QStatisticsValidated.statisticsValidated;
        JPAQuery query = new JPAQuery(getEntityManager());

        BooleanExpression predicat = validatedDefaultPredicat(ad, start, end, qStatistics);

        if(service!=null){
            predicat = predicat.and(qStatistics.service.eq(service));
        }

        query.from(qStatistics).where(predicat);

        query.groupBy(qStatistics.service);
        List<Statistics> res = new ArrayList<Statistics>();
        Tuple tuple= query.uniqueResult(qStatistics.count(),qStatistics.service);

        Long count = tuple.get(qStatistics.count());
        AdService adService = tuple.get(qStatistics.service);
        return  new Statistics(adService,count);

    }



    @Override
    public StatisticsAge nbValidatedGroupByGroupAge(Ad ad, AgeGroup ageGroup) {
        return nbValidatedGroupByGroupAge(ad,null,null,ageGroup);
    }

    @Override
    public StatisticsAge nbValidatedGroupByGroupAge(Ad ad, Date start, Date end, AgeGroup ageGroup) {
        QStatisticsValidated qStatistics = QStatisticsValidated.statisticsValidated;
        JPAQuery query = new JPAQuery(getEntityManager());

        BooleanExpression predicat = validatedDefaultPredicat(ad, start, end, qStatistics);

        query.from(qStatistics).where(predicat.and(qStatistics.ageGroup.eq(ageGroup)));

        return new StatisticsAge(ageGroup,query.uniqueResult(qStatistics.count()));
    }



    @Override
    public StatisticsAgeSex nbValidatedGroupByGroupAgeAndSex(Ad ad, AgeGroup ageGroup, Sex sex) {
        return nbValidatedGroupByGroupAgeAndSex(ad, null, null, ageGroup, sex);
    }

    @Override
    public StatisticsAgeSex nbValidatedGroupByGroupAgeAndSex(Ad ad, Date start, Date end, AgeGroup ageGroup, Sex sex) {
        QStatisticsValidated qStatistics = QStatisticsValidated.statisticsValidated;
        JPAQuery query = new JPAQuery(getEntityManager());

        BooleanExpression predicat = validatedDefaultPredicat(ad, start, end, qStatistics);

        query.from(qStatistics).where(predicat.and(qStatistics.ageGroup.eq(ageGroup)).and(qStatistics.sex.eq(sex)));

        return new StatisticsAgeSex(ageGroup,sex,query.uniqueResult(qStatistics.count()));
    }



    @Override
    public List<StatisticsAge> nbValidatedGroupByGroupAge(Ad ad) {
        return nbValidatedGroupByGroupAge(ad,null,null);
    }


    @Override
    public List<StatisticsAge> nbValidatedGroupByGroupAge(Ad ad, Date start, Date end) {

        List<StatisticsAge> res = new ArrayList<StatisticsAge>();
        for (AgeGroup ageGroup : AgeGroup.values()) {
            res.add(nbGlobalGroupByGroupAge(ad, start, end, ageGroup));
        }

        return res;
    }


    @Override
    public List<StatisticsAgeSex> nbValidatedGroupByGroupAgeAndSex(Ad ad) {
        return nbValidatedGroupByGroupAgeAndSex(ad,(Date)null,(Date)null);
    }


    @Override
    public List<StatisticsAgeSex> nbValidatedGroupByGroupAgeAndSex(Ad ad, Date start, Date end) {

        List<StatisticsAgeSex> res = new ArrayList<StatisticsAgeSex>();
        for (AgeGroup ageGroup : AgeGroup.values()) {
            Sex[] values = Sex.values();
            for (Sex sex : values) {
                res.add(nbValidatedGroupByGroupAgeAndSex(ad, start, end, ageGroup, sex));
            }
        }

        return res;
    }



    @Override
    public List<StatisticsCity> nbValidatedGroupByCity(Ad ad) {
        return nbValidatedGroupByCity(ad,null,null);
    }

    @Override
    public List<StatisticsCity> nbValidatedGroupByCity(Ad ad, Date start, Date end) {
        QStatisticsValidated qStatistics = QStatisticsValidated.statisticsValidated;
        JPAQuery query = new JPAQuery(getEntityManager());

        BooleanExpression predicat = validatedDefaultPredicat(ad, start, end, qStatistics);

        query.from(qStatistics).where(predicat);

        query.groupBy(qStatistics.city);

        List<Tuple> list = query.list(qStatistics.count(),qStatistics.city);
        List<StatisticsCity> res =new ArrayList<StatisticsCity>();
        for (Tuple tuple : list) {
            res.add(new StatisticsCity(tuple.get(qStatistics.city),tuple.get(qStatistics.count())));
        }

        return res;
    }

    @Override
    public List<StatisticsAgeSex> nbGlobalGroupByGroupAgeAndSexForService(Ad ad, AdService service) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }



    @Override
    public List<StatisticsAgeSex> nbValidatedGroupByGroupAgeAndSexForService(Ad ad, AdService service) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<StatisticsAge> nbValidatedGroupByGroupAgeForService(Ad ad, AdService service) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }


    @Override
    public List<StatisticsAge> nbGlobalGroupByGroupAgeForService(Ad ad, AdService service) {
        return nbGlobalGroupByGroupAgeForService(ad,null,null,service);
    }
*/







}


