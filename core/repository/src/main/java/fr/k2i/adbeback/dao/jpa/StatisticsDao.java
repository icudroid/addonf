package fr.k2i.adbeback.dao.jpa;

import com.mysema.query.Tuple;
import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.types.Predicate;
import com.mysema.query.types.expr.BooleanExpression;
import com.mysema.query.types.path.EntityPathBase;
import com.mysema.query.types.path.PathBuilder;
import fr.k2i.adbeback.core.business.ad.Ad;
import fr.k2i.adbeback.core.business.ad.QViewedAd;
import fr.k2i.adbeback.core.business.ad.rule.*;
import fr.k2i.adbeback.core.business.country.City;
import fr.k2i.adbeback.core.business.country.QCity;
import fr.k2i.adbeback.core.business.game.QAbstractAdGame;
import fr.k2i.adbeback.core.business.game.QAdChoise;
import fr.k2i.adbeback.core.business.game.QAdResponsePlayer;
import fr.k2i.adbeback.core.business.game.QAdScore;
import fr.k2i.adbeback.core.business.player.AgeGroup;
import fr.k2i.adbeback.core.business.player.Player;
import fr.k2i.adbeback.core.business.player.QPlayer;
import fr.k2i.adbeback.core.business.player.Sex;
import fr.k2i.adbeback.core.business.statistic.*;
import fr.k2i.adbeback.core.business.statistic.Statistics;
import fr.k2i.adbeback.dao.IStatisticsDao;
import fr.k2i.adbeback.dao.bean.*;
import org.joda.time.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
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


        JPAQuery query = new JPAQuery(getEntityManager());

        LocalDate now = new LocalDate(day);
        QAbstractAdGame game = QAbstractAdGame.abstractAdGame;
        QAdScore score = QAdScore.adScore;
        QAdResponsePlayer response = QAdResponsePlayer.adResponsePlayer;
        QPlayer player = QPlayer.player;
        QCity city = QCity.city1;
        QAdService adRule = QAdService.adService;
        QAdChoise choise = QAdChoise.adChoise;



        query.from(game).join(game.score.answers,response).join(game.player, player).leftJoin(player.address.city,city).join(game.choises,choise).join(choise.generatedBy,adRule).where(
                game.generated.between(now.toDate(), now.plusDays(1).toDate())
        );



        query.groupBy(city,player.sex,player.ageGroup,adRule);


        List<StatisticsViewed> res = new ArrayList<StatisticsViewed>();
        List<Tuple> list = query.list(city,player.sex,player.ageGroup,adRule ,response.count());
        for (Tuple tuple : list) {
            StatisticsViewed viewed = new StatisticsViewed(day, tuple.get(player.sex), tuple.get(player.address.city), tuple.get(adRule), tuple.get(player.ageGroup), tuple.get(response.count()));
            res.add((StatisticsViewed) save(viewed));
        }

        return res;
    }


    @Override
    public List<StatisticsValidated> doStatisticsValidatedForDay(Date day) {


        JPAQuery query = new JPAQuery(getEntityManager());

        LocalDate now = new LocalDate(day);
        QAbstractAdGame game = QAbstractAdGame.abstractAdGame;
        QAdScore score = QAdScore.adScore;
        QAdResponsePlayer response = QAdResponsePlayer.adResponsePlayer;
        QPlayer player = QPlayer.player;
        QCity city = QCity.city1;
        QAdService adRule = QAdService.adService;
        QAdChoise choise = QAdChoise.adChoise;



        query.from(game).join(game.score.answers,response).join(game.player, player).leftJoin(player.address.city,city).join(game.choises,choise).join(choise.generatedBy,adRule).where(
                game.generated.between(now.toDate(), now.plusDays(1).toDate())
                        .and(response.correctAnswer.eq(true))
        );



        query.groupBy(city,player.sex,player.ageGroup,adRule);


        List<StatisticsValidated> res = new ArrayList<StatisticsValidated>();
        List<Tuple> list = query.list(city,player.sex,player.ageGroup ,adRule,response.count());
        for (Tuple tuple : list) {
            StatisticsValidated viewed = new StatisticsValidated(day, tuple.get(player.sex), tuple.get(player.address.city), tuple.get(adRule), tuple.get(player.ageGroup), tuple.get(response.count()));
            res.add((StatisticsValidated) save(viewed));
        }

        return res;
    }

    @Override
    public List<StatisticsNoResponse> doStatisticsNoResponseForDay(Date day) {
        JPAQuery query = new JPAQuery(getEntityManager());

        LocalDate now = new LocalDate(day);

        QAbstractAdGame game = QAbstractAdGame.abstractAdGame;
        QAdScore score = QAdScore.adScore;
        QAdResponsePlayer response = QAdResponsePlayer.adResponsePlayer;
        QPlayer player = QPlayer.player;
        QAdChoise choise = QAdChoise.adChoise;
        QAdService adService = QAdService.adService;
        QCity city =QCity.city1;

        query.from(game).join(game.score,score).join(game.score.answers,response).join(game.player,player).leftJoin(player.address.city,city).join(game.choises,choise).join(choise.generatedBy,adService).where(
                game.generated.between(now.toDate(), now.plusDays(1).toDate())
                        .and(response.correctAnswer.eq(false))
                        .and(response.responses.isEmpty())
        );

        query.groupBy(city,player.sex,player.ageGroup,adService);


        List<StatisticsNoResponse> res = new ArrayList<StatisticsNoResponse>();

        List<Tuple> tuples = query.list(city,player.sex,player.ageGroup,adService,response.count());

        for (Tuple tuple : tuples) {
            AgeGroup ageGroup = tuple.get(player.ageGroup);
            City c = tuple.get(city);
            Sex sex = tuple.get(player.sex);
            Long nb = tuple.get(response.count());
            AdService service = tuple.get(adService);
            res.add(new StatisticsNoResponse(day,sex,c,service,ageGroup,nb));
        }

        return res;
    }

    @Override
    public List<StatisticsNotValidated> doStatisticsNotValidatedForDay(Date day) {
        JPAQuery query = new JPAQuery(getEntityManager());

        LocalDate now = new LocalDate(day);
        QAbstractAdGame game = QAbstractAdGame.abstractAdGame;
        QAdScore score = QAdScore.adScore;
        QAdResponsePlayer response = QAdResponsePlayer.adResponsePlayer;
        QPlayer player = QPlayer.player;
        QCity city = QCity.city1;
        QAdService adRule = QAdService.adService;
        QAdChoise choise = QAdChoise.adChoise;



        query.from(game).join(game.score.answers,response).join(game.player, player).leftJoin(player.address.city,city).join(game.choises,choise).join(choise.generatedBy,adRule).where(
                game.generated.between(now.toDate(), now.plusDays(1).toDate())
                        .and(response.correctAnswer.eq(false))
                        .and(response.responses.isNotEmpty())
        );



        query.groupBy(city,player.sex,player.ageGroup,adRule);


        List<StatisticsNotValidated> res = new ArrayList<StatisticsNotValidated>();
        List<Tuple> list = query.list(city,player.sex,player.ageGroup ,adRule,response.count());
        for (Tuple tuple : list) {
            StatisticsNotValidated viewed = new StatisticsNotValidated(day, tuple.get(player.sex), tuple.get(player.address.city), tuple.get(adRule), tuple.get(player.ageGroup), tuple.get(response.count()));
            res.add((StatisticsNotValidated) save(viewed));
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

    @Override
    public Page<Statistics> findBy(Date startAsDate, Date endAsDate, Long idAd, Long serviceId, AdViewedType adViewedType, List<AgeGroup> ageGroups, List<Sex> sexes, Pageable pageRequest) {
        JPAQuery query = new JPAQuery(getEntityManager());

        QStatistics qStatistics = QStatistics.statistics;
        QStatisticsValidated validated = qStatistics.as(QStatisticsValidated.class);
        QStatisticsViewed viewed = qStatistics.as(QStatisticsViewed.class);
        QStatisticsNoResponse noResponse = qStatistics.as(QStatisticsNoResponse.class);
        QStatisticsNotValidated notValidated = qStatistics.as(QStatisticsNotValidated.class);

        EntityPathBase toUse = null;

        BooleanExpression predicate = null;

        switch (adViewedType){
            case NO_RESPONSE:
                query.from(qStatistics);
                predicate = qStatistics.instanceOf(noResponse.getType());
                break;
            case NOT_VALIDATED:
                query.from(qStatistics);
                predicate =qStatistics.instanceOf(notValidated.getType());
                break;
            case VALIDATED:
                query.from(qStatistics);
                predicate =qStatistics.instanceOf(validated.getType());
                break;
            case VIEW:
                query.from(qStatistics);
                predicate =qStatistics.instanceOf(viewed.getType());
                break;
        }

        predicate = predicate.and(qStatistics.service.ad.id.eq(idAd));

        if(startAsDate!=null && endAsDate!=null){
            predicate = predicate.and(qStatistics.day.between(startAsDate, endAsDate));
        }

        if(serviceId!=null){
            predicate = predicate.and(qStatistics.service.id.eq(serviceId));
        }

        if(ageGroups!=null && !ageGroups.isEmpty()){
            predicate = predicate.and(qStatistics.ageGroup.in(ageGroups));
        }

        if(sexes!=null && !sexes.isEmpty()){
            predicate = predicate.and(qStatistics.sex.in(sexes));
        }

        query.where(predicate);

        query
                .offset(pageRequest.getOffset())
                .limit(pageRequest.getPageSize());

        return new PageImpl<Statistics>(query.list(qStatistics),pageRequest,query.count());
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


