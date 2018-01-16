package fr.k2i.adbeback.dao.jpa;

import com.google.common.collect.Lists;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQuery;
import fr.k2i.adbeback.core.business.ad.Ad;
import fr.k2i.adbeback.core.business.ad.Brand;
import fr.k2i.adbeback.core.business.ad.QBrand;
import fr.k2i.adbeback.core.business.ad.rule.*;
import fr.k2i.adbeback.core.business.game.*;
import fr.k2i.adbeback.core.business.player.AgeGroup;
import fr.k2i.adbeback.core.business.player.QPlayer;
import fr.k2i.adbeback.core.business.player.Sex;
import fr.k2i.adbeback.dao.IStatisticsRealTimeDao;
import fr.k2i.adbeback.dao.IViewedAdDao;
import fr.k2i.adbeback.dao.bean.*;
import fr.k2i.adbeback.date.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.util.*;

@Repository
public class StatisticsRealTimeDao  implements IStatisticsRealTimeDao {
    @PersistenceContext
    private javax.persistence.EntityManager entityManager;

    @Autowired
    private IViewedAdDao viewedAdDao;




/*    @Transactional
    @Override
    public List<Tuple> computeResponsesPlayer(Ad ad){
        JPAQuery query = new JPAQuery(entityManager);

        LocalDate now = new LocalDate();
        QAbstractAdGame game = QAbstractAdGame.abstractAdGame;
        QAdScore score = QAdScore.adScore;
        QAdResponsePlayer response = QAdResponsePlayer.adResponsePlayer;
        QPlayer player = QPlayer.player;

        query.from(game).join(game.score,score).join(game.score.answers,response).join(game.player,player).where(
                response.adService.ad.eq(ad)
                        .and(game.generated.between(DateUtils.asDate(now), DateUtils.asDate(now.plusDays(1))))
                        .and(response.responses.isNotEmpty())
        );

        query.groupBy(player.sex,player.ageGroup,response.responses);

        return query.list(player.sex,player.ageGroup,response.responses,response.count());

    }*/


    @Transactional
    @Override
    public Responses computeResponsesPlayer(OpenRule rule){
        JPAQuery query = new JPAQuery(entityManager);

        LocalDate now = LocalDate.now();
        QAbstractAdGame game = QAbstractAdGame.abstractAdGame;
        QAdScore score = QAdScore.adScore;
        QAdResponsePlayer response = QAdResponsePlayer.adResponsePlayer;
        QPlayer player = QPlayer.player;
        QAdResponse qAdResponse = QAdResponse.adResponse;

        QPossibility possibility = QPossibility.possibility;
        QOpenPossibility openPossibility = possibility.as(QOpenPossibility.class);

        query.from(game).join(game.score,score).join(game.score.answers,response).join(response.responses,possibility).join(openPossibility.generatedBy,qAdResponse).join(game.player, player).where(
                response.adService.eq(rule)
                        .and(game.generated.between(DateUtils.asDate(now), DateUtils.asDate(now.plusDays(1))))
                        .and(response.responses.isNotEmpty())
        );

        query.groupBy(player.sex, player.ageGroup, qAdResponse);


        Responses stat = new Responses();
        List<AdResponse> responses = rule.getResponses();
        for (AdResponse adResponse : responses) {
            stat.getResponses().add(adResponse.getResponse());
        }


        List<StatisticsAgeSexResponse> res = new ArrayList<StatisticsAgeSexResponse>();

        List<Tuple> tuples = query.select(player.sex, player.ageGroup, qAdResponse, response.count()).fetch();
        for (Tuple tuple : tuples) {
            AgeGroup ageGroup = tuple.get(player.ageGroup);
            Sex sex = tuple.get(player.sex);
            Long nb = tuple.get(response.count());
            AdResponse adResponse = tuple.get(qAdResponse);
            res.add(new StatisticsAgeSexResponse(ageGroup, sex, nb,adResponse.getResponse()));
        }
        stat.setStatistics(res);

        return stat;
    }

    @Override
    public Responses computeResponsesPlayer(BrandRule rule) {
        JPAQuery query = new JPAQuery(entityManager);

        LocalDate now = LocalDate.now();
        QAbstractAdGame game = QAbstractAdGame.abstractAdGame;
        QAdScore score = QAdScore.adScore;
        QAdResponsePlayer response = QAdResponsePlayer.adResponsePlayer;
        QPlayer player = QPlayer.player;
        QAdResponse qAdResponse = QAdResponse.adResponse;

        QPossibility possibility = QPossibility.possibility;
        QBrandPossibility brandPossibility = possibility.as(QBrandPossibility.class);

        QBrand brand = QBrand.brand;

        query.from(game).join(game.score,score).join(game.score.answers,response).join(response.responses,possibility).join(brandPossibility.brand,brand).join(game.player,player).where(
                response.adService.eq(rule)
                        .and(game.generated.between(DateUtils.asDate(now), DateUtils.asDate(now.plusDays(1))))
                        .and(response.responses.isNotEmpty())
        );

        query.groupBy(player.sex,player.ageGroup,brand);

        Responses stat = new Responses();
        stat.getResponses().add("logo");

        List<StatisticsAgeSexBrand> res = new ArrayList<StatisticsAgeSexBrand>();

        List<Tuple> tuples = query.select(player.sex,player.ageGroup,brand,response.count()).fetch();

        for (Tuple tuple : tuples) {
            AgeGroup ageGroup = tuple.get(player.ageGroup);
            Sex sex = tuple.get(player.sex);
            Long nb = tuple.get(response.count());
            Brand b = tuple.get(brand);
            res.add(new StatisticsAgeSexBrand(ageGroup, sex, nb,b.getLogo()));
        }

        stat.setStatistics(res);

        return stat;

    }

    @Override
    public Responses computeResponsesPlayer(MultiResponseRule rule) {

        List<AdResponse> responses = rule.getResponses();
        AdResponse[] adResponses = responses.toArray(new AdResponse[responses.size()]);

        LocalDate now = LocalDate.now();

        Responses stat = new Responses();

        for (AdResponse adResponse : adResponses) {
            stat.getResponses().add(adResponse.getResponse());
        }


        for (int i = 1; i <8; i++) {
            List<String> responded = Lists.newArrayList(null,null,null);

            List<AdResponse> responses4Query = new ArrayList<AdResponse>();
            if((i & 0x1)==1){
                responded.set(0,adResponses[0].getResponse());
                responses4Query.add(adResponses[0]);
            }

            if((i & 0x2)==2){
                responded.set(1,adResponses[1].getResponse());
                responses4Query.add(adResponses[1]);
            }

            if((i & 0x4)==4){
                responded.set(2,adResponses[2].getResponse());
                responses4Query.add(adResponses[2]);
            }


            QAbstractAdGame game = QAbstractAdGame.abstractAdGame;
            QAdScore score = QAdScore.adScore;

            QPlayer player = QPlayer.player;

            QAdResponsePlayer response = QAdResponsePlayer.adResponsePlayer;
            QAdResponsePlayer subResponse = new QAdResponsePlayer("subResponse");
            QPossibility possibility = QPossibility.possibility;
            QOpenPossibility openPossibility = possibility.as(QOpenPossibility.class);


            JPQLQuery<AdResponsePlayer> subQuery = JPAExpressions.select(subResponse).from(subResponse).join(subResponse.adScore).join(subResponse.adScore.game, game).join(subResponse.responses, possibility).where(
                    subResponse.adService.eq(rule)
                            .and(game.generated.between(DateUtils.asDate(now), DateUtils.asDate(now.plusDays(1))))
                            .and(openPossibility.generatedBy.in(responses4Query))
            ).groupBy(subResponse).having(subResponse.responses.size().eq(responses4Query.size())).distinct();


            JPAQuery query = new JPAQuery(entityManager);
            query.from(game).join(game.score,score).join(game.score.answers,response).join(game.player, player).where(
                    response.in(subQuery)
            ).groupBy(player.sex, player.ageGroup);


            query.distinct();
            List<Tuple> tuples = query.select(player.sex, player.ageGroup,response.count()).fetch();


            List statistics = stat.getStatistics();

            for (Tuple tuple : tuples) {
                AgeGroup ageGroup = tuple.get(player.ageGroup);
                Sex sex = tuple.get(player.sex);
                Long nb = tuple.get(response.count());

                StatisticsAgeSex r = new StatisticsAgeSexMulti(ageGroup, sex, nb, responded);

                statistics.add(r);
            }


        }


        return stat;
    }

    @Override
    public Double computeAverageBid(AdService service) {
        JPAQuery query = new JPAQuery(entityManager);

        LocalDate now = LocalDate.now();
        QAbstractAdGame game = QAbstractAdGame.abstractAdGame;
        QAdScore score = QAdScore.adScore;
        QAdResponsePlayer response = QAdResponsePlayer.adResponsePlayer;
        QAdChoise adChoise = QAdChoise.adChoise;

        query.from(game).join(game.score,score).join(game.score.answers,response).join(game.choises,adChoise).where(
                response.adService.eq(service)
                        .and(game.generated.between(DateUtils.asDate(now), DateUtils.asDate(now.plusDays(1))))
        );

        return (Double) query.select(adChoise.winBidPrice.avg()).fetchOne();
    }


    @Override
    public List<StatisticsAgeSex> computeResponsesPlayerKo(AdService service) {
        JPAQuery query = new JPAQuery(entityManager);

        LocalDate now = LocalDate.now();
        QAbstractAdGame game = QAbstractAdGame.abstractAdGame;
        QAdScore score = QAdScore.adScore;
        QAdResponsePlayer response = QAdResponsePlayer.adResponsePlayer;
        QPlayer player = QPlayer.player;

        query.from(game).join(game.score,score).join(game.score.answers,response).join(game.player,player).where(
                response.adService.eq(service)
                        .and(game.generated.between(DateUtils.asDate(now), DateUtils.asDate(now.plusDays(1))))
                        .and(response.correctAnswer.eq(false))
                        .and(response.responses.isNotEmpty())
        );

        query.groupBy(player.sex,player.ageGroup);


        List<StatisticsAgeSex> res = new ArrayList<StatisticsAgeSex>();

        List<Tuple> tuples = query.select(player.sex,player.ageGroup,response.count()).fetch();

        for (Tuple tuple : tuples) {
            AgeGroup ageGroup = tuple.get(player.ageGroup);
            Sex sex = tuple.get(player.sex);
            Long nb = tuple.get(response.count());
            res.add(new StatisticsAgeSex(ageGroup, sex, nb));
        }

        return res;

    }



    @Override
    public List<Tuple> computeResponsesPlayerKo(Ad ad) {
        JPAQuery query = new JPAQuery(entityManager);

        LocalDate now = LocalDate.now();
        QAbstractAdGame game = QAbstractAdGame.abstractAdGame;
        QAdScore score = QAdScore.adScore;
        QAdResponsePlayer response = QAdResponsePlayer.adResponsePlayer;
        QPlayer player = QPlayer.player;

        query.from(game).join(game.score,score).join(game.score.answers,response).join(game.player,player).where(
                response.adService.ad.eq(ad)
                        .and(game.generated.between(DateUtils.asDate(now), DateUtils.asDate(now.plusDays(1))))
                        .and(response.correctAnswer.eq(false))
                        .and(response.responses.isNotEmpty())
        );

        query.groupBy(player.sex,player.ageGroup);

        return query.select(player.sex,player.ageGroup,response.count()).fetch();

    }

    @Override
    public List<StatisticsAgeSex> computeResponsesPlayerOk(AdService service) {
        JPAQuery query = new JPAQuery(entityManager);

        LocalDate now = LocalDate.now();
        QAbstractAdGame game = QAbstractAdGame.abstractAdGame;
        QAdScore score = QAdScore.adScore;
        QAdResponsePlayer response = QAdResponsePlayer.adResponsePlayer;
        QPlayer player = QPlayer.player;

        query.from(game).join(game.score,score).join(game.score.answers,response).join(game.player,player).where(
                response.adService.eq(service)
                        .and(game.generated.between(DateUtils.asDate(now), DateUtils.asDate(now.plusDays(1))))
                        .and(response.correctAnswer.eq(true))
                        .and(response.responses.isNotEmpty())
        );

        query.groupBy(player.sex,player.ageGroup);


        List<StatisticsAgeSex> res = new ArrayList<StatisticsAgeSex>();

        List<Tuple> tuples = query.select(player.sex,player.ageGroup,response.count()).fetch();

        for (Tuple tuple : tuples) {
            AgeGroup ageGroup = tuple.get(player.ageGroup);
            Sex sex = tuple.get(player.sex);
            Long nb = tuple.get(response.count());
            res.add(new StatisticsAgeSex(ageGroup, sex, nb));
        }

        return res;

    }


    @Override
    public List<Tuple> computeResponsesPlayerOk(Ad ad) {
        JPAQuery query = new JPAQuery(entityManager);

        LocalDate now = LocalDate.now();
        QAbstractAdGame game = QAbstractAdGame.abstractAdGame;
        QAdScore score = QAdScore.adScore;
        QAdResponsePlayer response = QAdResponsePlayer.adResponsePlayer;
        QPlayer player = QPlayer.player;

        query.from(game).join(game.score,score).join(game.score.answers,response).join(game.player,player).where(
                response.adService.ad.eq(ad)
                        .and(game.generated.between(DateUtils.asDate(now), DateUtils.asDate(now.plusDays(1))))
                        .and(response.correctAnswer.eq(true))
                        .and(response.responses.isNotEmpty())
        );

        query.groupBy(player.sex,player.ageGroup);

        return query.select(player.sex,player.ageGroup,response.count()).fetch();
    }

    @Override
    public List<StatisticsAgeSex> computeNoResponses(AdService service) {
        JPAQuery query = new JPAQuery(entityManager);

        LocalDate now = LocalDate.now();
        QAbstractAdGame game = QAbstractAdGame.abstractAdGame;
        QAdScore score = QAdScore.adScore;
        QAdResponsePlayer response = QAdResponsePlayer.adResponsePlayer;
        QPlayer player = QPlayer.player;

        query.from(game).join(game.score,score).join(game.score.answers,response).join(game.player,player).where(
                response.adService.eq(service)
                        .and(game.generated.between(DateUtils.asDate(now), DateUtils.asDate(now.plusDays(1))))
                        .and(response.correctAnswer.eq(false))
                        .and(response.responses.isEmpty())
        );

        query.groupBy(player.sex,player.ageGroup);


        List<StatisticsAgeSex> res = new ArrayList<StatisticsAgeSex>();

        List<Tuple> tuples = query.select(player.sex,player.ageGroup,response.count()).fetch();

        for (Tuple tuple : tuples) {
            AgeGroup ageGroup = tuple.get(player.ageGroup);
            Sex sex = tuple.get(player.sex);
            Long nb = tuple.get(response.count());
            res.add(new StatisticsAgeSex(ageGroup, sex, nb));
        }

        return res;

    }


    @Override
    public List<Tuple> computeNoResponses(Ad ad) {
        JPAQuery query = new JPAQuery(entityManager);

        LocalDate now = LocalDate.now();
        QAbstractAdGame game = QAbstractAdGame.abstractAdGame;
        QAdScore score = QAdScore.adScore;
        QAdResponsePlayer response = QAdResponsePlayer.adResponsePlayer;
        QPlayer player = QPlayer.player;

        query.from(game).join(game.score,score).join(game.score.answers,response).join(game.player,player).where(
                response.adService.ad.eq(ad)
                        .and(game.generated.between(DateUtils.asDate(now), DateUtils.asDate(now.plusDays(1))))
                        .and(response.correctAnswer.eq(false))
                        .and(response.responses.isEmpty())
        );

        query.groupBy(player.sex,player.ageGroup);

        return query.select(player.sex,player.ageGroup,response.count()).fetch();
    }

    @Transactional
    @Override
    public Long computeNbView(AdService service){
        JPAQuery query = new JPAQuery(entityManager);

        LocalDate now = LocalDate.now();
        QAbstractAdGame game = QAbstractAdGame.abstractAdGame;
        QAdScore score = QAdScore.adScore;
        QAdResponsePlayer response = QAdResponsePlayer.adResponsePlayer;
        QPlayer player = QPlayer.player;

        query.from(game).join(game.score,score).join(game.score.answers,response).join(game.player,player).where(
                response.adService.eq(service)
                        .and(game.generated.between(DateUtils.asDate(now), DateUtils.asDate(now.plusDays(1))))
        );


        return (Long) query.select(response.count()).fetchOne();
    }



    @Transactional
    @Override
    public Long computeNbView(Ad ad){
        JPAQuery query = new JPAQuery(entityManager);

        LocalDate now = LocalDate.now();
        QAbstractAdGame game = QAbstractAdGame.abstractAdGame;
        QAdScore score = QAdScore.adScore;
        QAdResponsePlayer response = QAdResponsePlayer.adResponsePlayer;
        QPlayer player = QPlayer.player;

        query.from(game).join(game.score,score).join(game.score.answers,response).join(game.player,player).where(
                response.adService.ad.eq(ad)
                        .and(game.generated.between(DateUtils.asDate(now), DateUtils.asDate(now.plusDays(1))))
                        .and(response.correctAnswer.eq(false))
        );


        return (Long) query.select(response.count()).fetchOne();
    }

    @Transactional
    @Override
    public Long computeNbViewGlobal(Ad ad){
        JPAQuery query = new JPAQuery(entityManager);
        QAdResponsePlayer response = QAdResponsePlayer.adResponsePlayer;
        LocalDate now = LocalDate.now();
        QAbstractAdGame game = QAbstractAdGame.abstractAdGame;
        QAdScore score = QAdScore.adScore;

        query.from(game).join(game.score,score).join(game.score.answers,response).where(
                response.adService.ad.eq(ad)
                .and(game.generated.between(DateUtils.asDate(now), DateUtils.asDate(now.plusDays(1))))
        );
        query.distinct();
        return (Long) query.select(response.count()).fetchOne();
    }

    @Override
    public Long computeNbValidated(Ad ad) {
        JPAQuery query = new JPAQuery(entityManager);
        QAdResponsePlayer response = QAdResponsePlayer.adResponsePlayer;
        LocalDate now = LocalDate.now();
        QAbstractAdGame game = QAbstractAdGame.abstractAdGame;
        QAdScore score = QAdScore.adScore;

        query.from(game).join(game.score,score).join(game.score.answers,response).where(
                response.adService.ad.eq(ad)
                        .and(game.generated.between(DateUtils.asDate(now), DateUtils.asDate(now.plusDays(1))))
                        .and(response.correctAnswer.eq(true))
        );

        query.distinct();
        return (Long) query.select(response.count()).fetchOne();

    }


}

