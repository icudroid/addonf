package fr.k2i.adbeback.dao.jpa;

import com.mysema.query.Tuple;
import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.types.Expression;
import com.mysema.query.types.Path;
import com.mysema.query.types.expr.BooleanExpression;
import com.mysema.query.types.path.NumberPath;
import com.mysema.query.types.path.PathBuilder;
import com.mysema.query.types.path.SimplePath;
import fr.k2i.adbeback.core.business.ad.Ad;
import fr.k2i.adbeback.core.business.ad.QViewedAd;
import fr.k2i.adbeback.core.business.ad.rule.*;
import fr.k2i.adbeback.core.business.country.City;
import fr.k2i.adbeback.core.business.game.*;
import fr.k2i.adbeback.dao.IStatisticsDao;
import lombok.Data;
import org.joda.time.*;
import org.springframework.stereotype.Repository;

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


    @Data
    public class StatisticsCity{
        private City city;
        private Long count;

        public StatisticsCity(City city, Long count) {
            this.city = city;
            this.count = count;
        }
    }


    @Override
    public long nbGlobal(Ad ad) {
        return nbGlobal(ad,null,null);
    }


    @Override
    public long nbGlobal(Ad ad, Date start, Date end){
        QViewedAd viewedAd = QViewedAd.viewedAd;

        JPAQuery query = new JPAQuery(getEntityManager());
        BooleanExpression predicat = viewedAd.adRule.ad.eq(ad);
        if(start != null && end != null){
            predicat.and(viewedAd.date.between(start, end));
        }else if(start != null && end == null){
            predicat.and(viewedAd.date.gt(start));
        }else if(start == null && end != null){
            predicat.and(viewedAd.date.lt(end));
        }

        query.from(viewedAd)
                .where(
                        predicat

                );
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
        QViewedAd viewedAd = QViewedAd.viewedAd;

        JPAQuery query = new JPAQuery(getEntityManager());

        BooleanExpression predicat = viewedAd.adRule.ad.eq(ad);
        if(start != null && end != null){
            predicat.and(viewedAd.date.between(start, end));
        }else if(start != null && end == null){
            predicat.and(viewedAd.date.gt(start));
        }else if(start == null && end != null){
            predicat.and(viewedAd.date.lt(end));
        }

        if(service!=null){
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
    public StatisticsAge nbGlobalGroupByGroupAge(Ad ad, AgeGroup ageGroup) {
        return nbGlobalGroupByGroupAge(ad,null,null,ageGroup);
    }

        @Override
    public StatisticsAge nbGlobalGroupByGroupAge(Ad ad, Date start, Date end, AgeGroup ageGroup) {
        QViewedAd viewedAd = QViewedAd.viewedAd;

        JPAQuery query = new JPAQuery(getEntityManager());
        BooleanExpression predicat = viewedAd.adRule.ad.eq(ad);
        if(start != null && end != null){
            predicat.and(viewedAd.date.between(start, end));
        }else if(start != null && end == null){
            predicat.and(viewedAd.date.gt(start));
        }else if(start == null && end != null){
            predicat.and(viewedAd.date.lt(end));
        }

        query.from(viewedAd)
                .where(
                        predicat.and(viewedAd.player.birthday.between(ageGroup.minDate(), ageGroup.maxDate()))
                );

        return new StatisticsAge(ageGroup,query.uniqueResult(viewedAd.count()));
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
    public List<StatisticsCity> nbGlobalGroupByCity(Ad ad) {
        return nbGlobalGroupByCity(ad,null,null);
    }

        @Override
    public List<StatisticsCity> nbGlobalGroupByCity(Ad ad, Date start, Date end) {
        QViewedAd viewedAd = QViewedAd.viewedAd;

        JPAQuery query = new JPAQuery(getEntityManager());

        BooleanExpression predicat = viewedAd.adRule.ad.eq(ad);
        if(start != null && end != null){
            predicat.and(viewedAd.date.between(start, end));
        }else if(start != null && end == null){
            predicat.and(viewedAd.date.gt(start));
        }else if(start == null && end != null){
            predicat.and(viewedAd.date.lt(end));
        }

        query.from(viewedAd)
                .where(
                        predicat
                );

        query.groupBy(viewedAd.player.address.city);
        query.having(viewedAd.player.count().gt(0));

        List<Tuple> list = query.list(viewedAd.count(), viewedAd.player.address.city);
        List<StatisticsCity> res =new ArrayList<StatisticsCity>();
        for (Tuple tuple : list) {
            res.add(new StatisticsCity(tuple.get(viewedAd.player.address.city),tuple.get(viewedAd.count())));
        }

        return res;
    }


    /***************************************************************************************************************/

    @Override
    public long nbValidated(Ad ad){
        return nbValidated(ad,null,null);
    }


        @Override
    public long nbValidated(Ad ad, Date start, Date end){
        QAbstractAdGame game = QAbstractAdGame.abstractAdGame;

        JPAQuery jpaQuery = new JPAQuery(getEntityManager());

        PathBuilder<AdChoise> choise = new PathBuilder<AdChoise>(AdChoise.class, "choise");
        PathBuilder<AdResponsePlayer> answer = new PathBuilder<AdResponsePlayer>(AdResponsePlayer.class, "answer");

        jpaQuery.from(game).join(game.choises,choise).join(game.score.answers,answer);

        BooleanExpression predicat = choise.get("generatedBy.ad").eq(ad).and(answer.get("response").eq(choise.get("correct")));

        if(start != null && end != null){
            predicat.and(game.generated.between(start,end));
        }else if(start != null && end == null){
            predicat.and(game.generated.gt(start));
        }else if(start == null && end != null){
            predicat.and(game.generated.lt(end));
        }


        jpaQuery.where(
                predicat
        );

/*        Query query = getEntityManager().createQuery("select count(answer) from AbstractAdGame game join game.choises  as choise join game.score.answers as answer where choise.generatedBy.ad=:ad and game.generated between :start and :end and answer.response = choise.correct");
        query.setParameter("ad",ad);
        query.setParameter("end",end);
        query.setParameter("start",start);
        return (Long) query.getSingleResult();*/

        return jpaQuery.uniqueResult(answer.count());

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


        QAbstractAdGame game = QAbstractAdGame.abstractAdGame;

        JPAQuery jpaQuery = new JPAQuery(getEntityManager());

        PathBuilder<AdChoise> choise = new PathBuilder<AdChoise>(AdChoise.class, "choise");
        PathBuilder<AdResponsePlayer> answer = new PathBuilder<AdResponsePlayer>(AdResponsePlayer.class, "answer");

        jpaQuery.from(game).join(game.choises,choise).join(game.score.answers,answer);

        BooleanExpression predicat = choise.get("generatedBy.ad").eq(ad).and(answer.get("response").eq(choise.get("correct"))).and(choise.get("generatedBy").instanceOf(service));

        if(start != null && end != null){
            predicat.and(game.generated.between(start,end));
        }else if(start != null && end == null){
            predicat.and(game.generated.gt(start));
        }else if(start == null && end != null){
            predicat.and(game.generated.lt(end));
        }


        jpaQuery.where(
                predicat
        );

        jpaQuery.groupBy(choise.get("generatedBy"));

        /*StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("select count(answer), choise.generatedBy from AbstractAdGame game join game.choises  as choise join game.score.answers as answer where choise.generatedBy.ad=:ad and game.generated between :start and :end and answer.response = choise.correct ");

        if(BrandRule.class.equals(service)){
            queryBuilder.append(" and choise.generatedBy.class = BrandRule ");
        }else if(OpenRule.class.equals(service)){
            queryBuilder.append(" and choise.generatedBy.class = OpenRule ");
        }else if(service == null){

        }else{
            return new ArrayList<Statistics>();
        }

        queryBuilder.append(" group by choise.generatedBy");

        Query query = getEntityManager().createQuery(queryBuilder.toString());
        query.setParameter("ad",ad);
        query.setParameter("end",end);
        query.setParameter("start",start);


        List resultList = query.getResultList();

        List<Statistics> res =new ArrayList<Statistics>();

        for (Object o : resultList) {
            Object result[] = (Object[]) o;
            res.add(new Statistics((AdService)result[1],(Long)result[0]));
        }*/
        List<Statistics> res =new ArrayList<Statistics>();

        List<Tuple> tuples = jpaQuery.list(answer.count(), choise.get("generatedBy"));
        for (Tuple tuple : tuples) {
            res.add(new Statistics((AdService)tuple.get(choise.get("generatedBy")),tuple.get(answer.count())));
        }


        return res;
    }


    @Override
    public StatisticsAge nbValidatedGroupByGroupAge(Ad ad, AgeGroup ageGroup) {
        return nbValidatedGroupByGroupAge(ad,null,null,ageGroup);
    }


        @Override
    public StatisticsAge nbValidatedGroupByGroupAge(Ad ad, Date start, Date end, AgeGroup ageGroup) {


        QAbstractAdGame game = QAbstractAdGame.abstractAdGame;

        JPAQuery jpaQuery = new JPAQuery(getEntityManager());

        PathBuilder<AdChoise> choise = new PathBuilder<AdChoise>(AdChoise.class, "choise");
        PathBuilder<AdResponsePlayer> answer = new PathBuilder<AdResponsePlayer>(AdResponsePlayer.class, "answer");

        jpaQuery.from(game).join(game.choises,choise).join(game.score.answers,answer);


        BooleanExpression predicat = choise.get("generatedBy.ad").eq(ad).and(answer.get("response").eq(choise.get("correct"))).and(game.player.birthday.between(ageGroup.minDate(),ageGroup.maxDate()));

        if(start != null && end != null){
            predicat.and(game.generated.between(start,end));
        }else if(start != null && end == null){
            predicat.and(game.generated.gt(start));
        }else if(start == null && end != null){
            predicat.and(game.generated.lt(end));
        }


        jpaQuery.where(
                predicat
        );



        return new StatisticsAge(ageGroup, jpaQuery.uniqueResult(answer.count()));


        /*Query query = getEntityManager().createQuery("select count(answer) from AbstractAdGame game " +
                                                                "join game.choises  as choise " +
                                                                "join game.score.answers as answer " +
                                                                "join game.player as player " +
                                                                "where " +
                                                                    "player.birthday between :min and :max " +
                                                                    "and choise.generatedBy.ad=:ad " +
                                                                    "and game.generated between :start and :end " +
                                                                    "and answer.response = choise.correct ");
        query.setParameter("ad",ad);
        query.setParameter("end",end);
        query.setParameter("start",start);
        query.setParameter("min",ageGroup.minDate());
        query.setParameter("max",ageGroup.maxDate());

        return new StatisticsAge(ageGroup, (Long) query.getSingleResult());*/
    }


    @Override
    public List<StatisticsAge> nbValidatedGroupByGroupAge(Ad ad) {
        return nbValidatedGroupByGroupAge(ad,null,null);
    }


    @Override
    public List<StatisticsAge> nbValidatedGroupByGroupAge(Ad ad, Date start, Date end) {

        List<StatisticsAge> res = new ArrayList<StatisticsAge>();
        for (AgeGroup ageGroup : AgeGroup.values()) {
            res.add(nbValidatedGroupByGroupAge(ad, start, end, ageGroup));
        }

        return res;
    }



    @Override
    public List<StatisticsCity> nbValidatedGroupByCity(Ad ad) {
        return nbValidatedGroupByCity(ad,null,null);
    }

    @Override
    public List<StatisticsCity> nbValidatedGroupByCity(Ad ad, Date start, Date end) {
        List<StatisticsCity> res = new ArrayList<StatisticsCity>();



        QAbstractAdGame game = QAbstractAdGame.abstractAdGame;

        JPAQuery jpaQuery = new JPAQuery(getEntityManager());

        PathBuilder<AdChoise> choise = new PathBuilder<AdChoise>(AdChoise.class, "choise");
        PathBuilder<AdResponsePlayer> answer = new PathBuilder<AdResponsePlayer>(AdResponsePlayer.class, "answer");

        jpaQuery.from(game).join(game.choises,choise).join(game.score.answers,answer);


        BooleanExpression predicat = choise.get("generatedBy.ad").eq(ad).and(answer.get("response").eq(choise.get("correct")));

        if(start != null && end != null){
            predicat.and(game.generated.between(start,end));
        }else if(start != null && end == null){
            predicat.and(game.generated.gt(start));
        }else if(start == null && end != null){
            predicat.and(game.generated.lt(end));
        }


        jpaQuery.where(
                predicat
        );

        jpaQuery.groupBy(game.player.address.city).having(game.player.count().gt(0));

        List<Tuple> tuples = jpaQuery.list(game.player.address.city, answer.count());
        for (Tuple tuple : tuples) {
            res.add(new StatisticsCity(tuple.get(game.player.address.city),tuple.get(answer.count())));
        }

        /*Query query = getEntityManager().createQuery("select player.address.city, count(answer) from AbstractAdGame game " +
                "join game.choises  as choise " +
                "join game.score.answers as answer " +
                "join game.player as player " +
                "where " +
                "choise.generatedBy.ad=:ad " +
                "and game.generated between :start and :end " +
                "and answer.response = choise.correct " +
                "group by player.address.city " +
                "having count (player) > 0");

        query.setParameter("ad",ad);
        query.setParameter("end",end);
        query.setParameter("start",start);


        List resultList = query.getResultList();

        for (Object o : resultList) {
            Object result[] = (Object[]) o;
            res.add(new StatisticsCity((City)result[0],(Long)result[1]));
        }*/

        return res;
    }



    /***************************************************************************************************************/

    @Override
    public long nbNotValidated(Ad ad){
        return nbNotValidated(ad,null,null);
    }


        @Override
    public long nbNotValidated(Ad ad, Date start, Date end){
        QAbstractAdGame game = QAbstractAdGame.abstractAdGame;

        JPAQuery jpaQuery = new JPAQuery(getEntityManager());

        PathBuilder<AdChoise> choise = new PathBuilder<AdChoise>(AdChoise.class, "choise");
        PathBuilder<AdResponsePlayer> answer = new PathBuilder<AdResponsePlayer>(AdResponsePlayer.class, "answer");

        jpaQuery.from(game).join(game.choises,choise).join(game.score.answers,answer);

        BooleanExpression predicat = choise.get("generatedBy.ad").eq(ad).and(answer.get("response").ne(choise.get("correct")));

        if(start != null && end != null){
            predicat.and(game.generated.between(start,end));
        }else if(start != null && end == null){
            predicat.and(game.generated.gt(start));
        }else if(start == null && end != null){
            predicat.and(game.generated.lt(end));
        }


        jpaQuery.where(
                predicat
        );



        return jpaQuery.uniqueResult(answer.count());

    }


    @Override
    public List<Statistics> nbNotValidatedGroupByService(Ad ad){
    return nbNotValidatedGroupByService(ad,null,null,null);
    }


    @Override
    public List<Statistics> nbNotValidatedGroupByService(Ad ad, Class<? extends AdService> service){
        return nbNotValidatedGroupByService(ad,null,null,service);
    }


    @Override
    public List<Statistics> nbNotValidatedGroupByService(Ad ad, Date start, Date end, Class<? extends AdService> service){


        QAbstractAdGame game = QAbstractAdGame.abstractAdGame;

        JPAQuery jpaQuery = new JPAQuery(getEntityManager());

        PathBuilder<AdChoise> choise = new PathBuilder<AdChoise>(AdChoise.class, "choise");
        PathBuilder<AdResponsePlayer> answer = new PathBuilder<AdResponsePlayer>(AdResponsePlayer.class, "answer");

        jpaQuery.from(game).join(game.choises,choise).join(game.score.answers,answer);

        BooleanExpression predicat = choise.get("generatedBy.ad").eq(ad).and(answer.get("response").ne(choise.get("correct"))).and(choise.get("generatedBy").instanceOf(service));

        if(start != null && end != null){
            predicat.and(game.generated.between(start,end));
        }else if(start != null && end == null){
            predicat.and(game.generated.gt(start));
        }else if(start == null && end != null){
            predicat.and(game.generated.lt(end));
        }


        jpaQuery.where(
                predicat
        );

        jpaQuery.groupBy(choise.get("generatedBy"));


        List<Statistics> res =new ArrayList<Statistics>();

        List<Tuple> tuples = jpaQuery.list(answer.count(), choise.get("generatedBy"));
        for (Tuple tuple : tuples) {
            res.add(new Statistics((AdService)tuple.get(choise.get("generatedBy")),tuple.get(answer.count())));
        }


        return res;
    }


    @Override
    public StatisticsAge nbNotValidatedGroupByGroupAge(Ad ad, AgeGroup ageGroup) {
        return nbNotValidatedGroupByGroupAge(ad,null,null,ageGroup);
    }


    @Override
    public StatisticsAge nbNotValidatedGroupByGroupAge(Ad ad, Date start, Date end, AgeGroup ageGroup) {


        QAbstractAdGame game = QAbstractAdGame.abstractAdGame;

        JPAQuery jpaQuery = new JPAQuery(getEntityManager());

        PathBuilder<AdChoise> choise = new PathBuilder<AdChoise>(AdChoise.class, "choise");
        PathBuilder<AdResponsePlayer> answer = new PathBuilder<AdResponsePlayer>(AdResponsePlayer.class, "answer");

        jpaQuery.from(game).join(game.choises,choise).join(game.score.answers,answer);


        BooleanExpression predicat = choise.get("generatedBy.ad").eq(ad).and(answer.get("response").ne(choise.get("correct"))).and(game.player.birthday.between(ageGroup.minDate(),ageGroup.maxDate()));

        if(start != null && end != null){
            predicat.and(game.generated.between(start,end));
        }else if(start != null && end == null){
            predicat.and(game.generated.gt(start));
        }else if(start == null && end != null){
            predicat.and(game.generated.lt(end));
        }


        jpaQuery.where(
                predicat
        );



        return new StatisticsAge(ageGroup, jpaQuery.uniqueResult(answer.count()));


    }


    @Override
    public List<StatisticsAge> nbNotValidatedGroupByGroupAge(Ad ad) {
        return nbNotValidatedGroupByGroupAge(ad,null,null);
    }

    @Override
    public List<StatisticsAge> nbNotValidatedGroupByGroupAge(Ad ad, Date start, Date end) {

        List<StatisticsAge> res = new ArrayList<StatisticsAge>();
        for (AgeGroup ageGroup : AgeGroup.values()) {
            res.add(nbNotValidatedGroupByGroupAge(ad, start, end, ageGroup));
        }

        return res;
    }


    @Override
    public List<StatisticsCity> nbNotValidatedGroupByCity(Ad ad) {
        return nbNotValidatedGroupByCity(ad,null,null);
    }

    @Override
    public List<StatisticsCity> nbNotValidatedGroupByCity(Ad ad, Date start, Date end) {
        List<StatisticsCity> res = new ArrayList<StatisticsCity>();



        QAbstractAdGame game = QAbstractAdGame.abstractAdGame;

        JPAQuery jpaQuery = new JPAQuery(getEntityManager());

        PathBuilder<AdChoise> choise = new PathBuilder<AdChoise>(AdChoise.class, "choise");
        PathBuilder<AdResponsePlayer> answer = new PathBuilder<AdResponsePlayer>(AdResponsePlayer.class, "answer");

        jpaQuery.from(game).join(game.choises,choise).join(game.score.answers,answer);


        BooleanExpression predicat = choise.get("generatedBy.ad").eq(ad).and(answer.get("response").ne(choise.get("correct")));

        if(start != null && end != null){
            predicat.and(game.generated.between(start,end));
        }else if(start != null && end == null){
            predicat.and(game.generated.gt(start));
        }else if(start == null && end != null){
            predicat.and(game.generated.lt(end));
        }


        jpaQuery.where(
                predicat
        );

        jpaQuery.groupBy(game.player.address.city).having(game.player.count().gt(0));

        List<Tuple> tuples = jpaQuery.list(game.player.address.city, answer.count());
        for (Tuple tuple : tuples) {
            res.add(new StatisticsCity(tuple.get(game.player.address.city),tuple.get(answer.count())));
        }

        return res;
    }




}

