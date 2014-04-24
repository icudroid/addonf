package fr.k2i.adbeback.dao.jpa;

import com.mysema.query.Tuple;
import com.mysema.query.jpa.impl.JPAQuery;
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
import fr.k2i.adbeback.dao.bean.StatisticsAgeSex;
import fr.k2i.adbeback.dao.bean.StatisticsAgeSexBrand;
import fr.k2i.adbeback.dao.bean.StatisticsAgeSexResponse;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.PersistenceContext;
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
                        .and(game.generated.between(now.toDate(), now.plusDays(1).toDate()))
                        .and(response.responses.isNotEmpty())
        );

        query.groupBy(player.sex,player.ageGroup,response.responses);

        return query.list(player.sex,player.ageGroup,response.responses,response.count());

    }*/


    @Transactional
    @Override
    public List<StatisticsAgeSexResponse> computeResponsesPlayer(OpenRule rule){
        JPAQuery query = new JPAQuery(entityManager);

        LocalDate now = new LocalDate();
        QAbstractAdGame game = QAbstractAdGame.abstractAdGame;
        QAdScore score = QAdScore.adScore;
        QAdResponsePlayer response = QAdResponsePlayer.adResponsePlayer;
        QPlayer player = QPlayer.player;
        QAdResponse qAdResponse = QAdResponse.adResponse;

        QPossibility possibility = QPossibility.possibility;
        QOpenPossibility openPossibility = possibility.as(QOpenPossibility.class);

        query.from(game).join(game.score,score).join(game.score.answers,response).join(response.responses,possibility).join(openPossibility.generatedBy,qAdResponse).join(game.player, player).where(
                response.adService.eq(rule)
                        .and(game.generated.between(now.toDate(), now.plusDays(1).toDate()))
                        .and(response.responses.isNotEmpty())
        );

        query.groupBy(player.sex, player.ageGroup, qAdResponse);

        List<StatisticsAgeSexResponse> res = new ArrayList<StatisticsAgeSexResponse>();

        List<Tuple> tuples = query.list(player.sex, player.ageGroup, qAdResponse, response.count());
        for (Tuple tuple : tuples) {
            AgeGroup ageGroup = tuple.get(player.ageGroup);
            Sex sex = tuple.get(player.sex);
            Long nb = tuple.get(response.count());
            AdResponse adResponse = tuple.get(qAdResponse);
            res.add(new StatisticsAgeSexResponse(ageGroup, sex, nb,adResponse.getResponse()));
        }

        return res;
    }

    @Override
    public List<StatisticsAgeSexBrand> computeResponsesPlayer(BrandRule rule) {
        JPAQuery query = new JPAQuery(entityManager);

        LocalDate now = new LocalDate();
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
                        .and(game.generated.between(now.toDate(), now.plusDays(1).toDate()))
                        .and(response.responses.isNotEmpty())
        );

        query.groupBy(player.sex,player.ageGroup,brand);



        List<StatisticsAgeSexBrand> res = new ArrayList<StatisticsAgeSexBrand>();

        List<Tuple> tuples = query.list(player.sex,player.ageGroup,brand,response.count());

        for (Tuple tuple : tuples) {
            AgeGroup ageGroup = tuple.get(player.ageGroup);
            Sex sex = tuple.get(player.sex);
            Long nb = tuple.get(response.count());
            Brand b = tuple.get(brand);
            res.add(new StatisticsAgeSexBrand(ageGroup, sex, nb,b.getLogo()));
        }

        return res;

    }

    @Override
    public List<Tuple> computeResponsesPlayer(MultiResponseRule rule) {
        JPAQuery query = new JPAQuery(entityManager);

        LocalDate now = new LocalDate();
        QAbstractAdGame game = QAbstractAdGame.abstractAdGame;
        QAdScore score = QAdScore.adScore;
        QAdResponsePlayer response = QAdResponsePlayer.adResponsePlayer;
        QPlayer player = QPlayer.player;
        QAdResponse qAdResponse = QAdResponse.adResponse;

        QPossibility possibility = QPossibility.possibility;
        QOpenPossibility openPossibility = possibility.as(QOpenPossibility.class);

        query.from(game).join(game.score,score).join(game.score.answers,response).join(response.responses,possibility).join(openPossibility.generatedBy,qAdResponse).join(game.player, player).where(
                response.adService.eq(rule)
                        .and(game.generated.between(now.toDate(), now.plusDays(1).toDate()))
                        .and(response.responses.isNotEmpty())
        );

        query.groupBy(player.sex,player.ageGroup,openPossibility);

        return query.list(player.sex,player.ageGroup,openPossibility,response.count());

    }


    @Override
    public List<StatisticsAgeSex> computeResponsesPlayerKo(AdService service) {
        JPAQuery query = new JPAQuery(entityManager);

        LocalDate now = new LocalDate();
        QAbstractAdGame game = QAbstractAdGame.abstractAdGame;
        QAdScore score = QAdScore.adScore;
        QAdResponsePlayer response = QAdResponsePlayer.adResponsePlayer;
        QPlayer player = QPlayer.player;

        query.from(game).join(game.score,score).join(game.score.answers,response).join(game.player,player).where(
                response.adService.eq(service)
                        .and(game.generated.between(now.toDate(), now.plusDays(1).toDate()))
                        .and(response.correctAnswer.eq(false))
                        .and(response.responses.isNotEmpty())
        );

        query.groupBy(player.sex,player.ageGroup);


        List<StatisticsAgeSex> res = new ArrayList<StatisticsAgeSex>();

        List<Tuple> tuples = query.list(player.sex,player.ageGroup,response.count());

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

        LocalDate now = new LocalDate();
        QAbstractAdGame game = QAbstractAdGame.abstractAdGame;
        QAdScore score = QAdScore.adScore;
        QAdResponsePlayer response = QAdResponsePlayer.adResponsePlayer;
        QPlayer player = QPlayer.player;

        query.from(game).join(game.score,score).join(game.score.answers,response).join(game.player,player).where(
                response.adService.ad.eq(ad)
                        .and(game.generated.between(now.toDate(), now.plusDays(1).toDate()))
                        .and(response.correctAnswer.eq(false))
                        .and(response.responses.isNotEmpty())
        );

        query.groupBy(player.sex,player.ageGroup);

        return query.list(player.sex,player.ageGroup,response.count());

    }

    @Override
    public List<StatisticsAgeSex> computeResponsesPlayerOk(AdService service) {
        JPAQuery query = new JPAQuery(entityManager);

        LocalDate now = new LocalDate();
        QAbstractAdGame game = QAbstractAdGame.abstractAdGame;
        QAdScore score = QAdScore.adScore;
        QAdResponsePlayer response = QAdResponsePlayer.adResponsePlayer;
        QPlayer player = QPlayer.player;

        query.from(game).join(game.score,score).join(game.score.answers,response).join(game.player,player).where(
                response.adService.eq(service)
                        .and(game.generated.between(now.toDate(), now.plusDays(1).toDate()))
                        .and(response.correctAnswer.eq(true))
                        .and(response.responses.isNotEmpty())
        );

        query.groupBy(player.sex,player.ageGroup);


        List<StatisticsAgeSex> res = new ArrayList<StatisticsAgeSex>();

        List<Tuple> tuples = query.list(player.sex,player.ageGroup,response.count());

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

        LocalDate now = new LocalDate();
        QAbstractAdGame game = QAbstractAdGame.abstractAdGame;
        QAdScore score = QAdScore.adScore;
        QAdResponsePlayer response = QAdResponsePlayer.adResponsePlayer;
        QPlayer player = QPlayer.player;

        query.from(game).join(game.score,score).join(game.score.answers,response).join(game.player,player).where(
                response.adService.ad.eq(ad)
                        .and(game.generated.between(now.toDate(), now.plusDays(1).toDate()))
                        .and(response.correctAnswer.eq(true))
                        .and(response.responses.isNotEmpty())
        );

        query.groupBy(player.sex,player.ageGroup);

        return query.list(player.sex,player.ageGroup,response.count());
    }

    @Override
    public List<StatisticsAgeSex> computeNoResponses(AdService service) {
        JPAQuery query = new JPAQuery(entityManager);

        LocalDate now = new LocalDate();
        QAbstractAdGame game = QAbstractAdGame.abstractAdGame;
        QAdScore score = QAdScore.adScore;
        QAdResponsePlayer response = QAdResponsePlayer.adResponsePlayer;
        QPlayer player = QPlayer.player;

        query.from(game).join(game.score,score).join(game.score.answers,response).join(game.player,player).where(
                response.adService.eq(service)
                        .and(game.generated.between(now.toDate(), now.plusDays(1).toDate()))
                        .and(response.correctAnswer.eq(false))
                        .and(response.responses.isEmpty())
        );

        query.groupBy(player.sex,player.ageGroup);


        List<StatisticsAgeSex> res = new ArrayList<StatisticsAgeSex>();

        List<Tuple> tuples = query.list(player.sex,player.ageGroup,response.count());

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

        LocalDate now = new LocalDate();
        QAbstractAdGame game = QAbstractAdGame.abstractAdGame;
        QAdScore score = QAdScore.adScore;
        QAdResponsePlayer response = QAdResponsePlayer.adResponsePlayer;
        QPlayer player = QPlayer.player;

        query.from(game).join(game.score,score).join(game.score.answers,response).join(game.player,player).where(
                response.adService.ad.eq(ad)
                        .and(game.generated.between(now.toDate(), now.plusDays(1).toDate()))
                        .and(response.correctAnswer.eq(false))
                        .and(response.responses.isEmpty())
        );

        query.groupBy(player.sex,player.ageGroup);

        return query.list(player.sex,player.ageGroup,response.count());
    }

    @Transactional
    @Override
    public Long computeNbView(AdService service){
        JPAQuery query = new JPAQuery(entityManager);

        LocalDate now = new LocalDate();
        QAbstractAdGame game = QAbstractAdGame.abstractAdGame;
        QAdScore score = QAdScore.adScore;
        QAdResponsePlayer response = QAdResponsePlayer.adResponsePlayer;
        QPlayer player = QPlayer.player;

        query.from(game).join(game.score,score).join(game.score.answers,response).join(game.player,player).where(
                response.adService.eq(service)
                        .and(game.generated.between(now.toDate(), now.plusDays(1).toDate()))
        );


        return query.uniqueResult(response.count());
    }



    @Transactional
    @Override
    public Long computeNbView(Ad ad){
        JPAQuery query = new JPAQuery(entityManager);

        LocalDate now = new LocalDate();
        QAbstractAdGame game = QAbstractAdGame.abstractAdGame;
        QAdScore score = QAdScore.adScore;
        QAdResponsePlayer response = QAdResponsePlayer.adResponsePlayer;
        QPlayer player = QPlayer.player;

        query.from(game).join(game.score,score).join(game.score.answers,response).join(game.player,player).where(
                response.adService.ad.eq(ad)
                        .and(game.generated.between(now.toDate(), now.plusDays(1).toDate()))
                        .and(response.correctAnswer.eq(false))
        );


        return query.uniqueResult(response.count());
    }

    @Transactional
    @Override
    public Long computeNbViewGlobal(Ad ad){
        JPAQuery query = new JPAQuery(entityManager);
        QAdResponsePlayer response = QAdResponsePlayer.adResponsePlayer;
        LocalDate now = new LocalDate();
        QAbstractAdGame game = QAbstractAdGame.abstractAdGame;
        QAdScore score = QAdScore.adScore;

        query.from(game).join(game.score,score).join(game.score.answers,response).where(
                response.adService.ad.eq(ad)
                .and(game.generated.between(now.toDate(), now.plusDays(1).toDate()))
        );
        query.distinct();
        return query.uniqueResult(response.count());
    }

    @Override
    public Long computeNbValidated(Ad ad) {
        JPAQuery query = new JPAQuery(entityManager);
        QAdResponsePlayer response = QAdResponsePlayer.adResponsePlayer;
        LocalDate now = new LocalDate();
        QAbstractAdGame game = QAbstractAdGame.abstractAdGame;
        QAdScore score = QAdScore.adScore;

        query.from(game).join(game.score,score).join(game.score.answers,response).where(
                response.adService.ad.eq(ad)
                        .and(game.generated.between(now.toDate(), now.plusDays(1).toDate()))
                        .and(response.correctAnswer.eq(true))
        );

        query.distinct();
        return query.uniqueResult(response.count());

    }


}

