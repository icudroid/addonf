package fr.k2i.adbeback.dao.jpa;

import com.mysema.query.jpa.impl.JPAQuery;
import fr.k2i.adbeback.core.business.ad.Brand;
import fr.k2i.adbeback.core.business.ad.rule.AdService;
import fr.k2i.adbeback.core.business.game.*;
import fr.k2i.adbeback.core.business.user.Media;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * This class interacts with Spring's HibernateTemplate to save/delete and
 * retrieve User objects.
 *
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 *   Modified by <a href="mailto:dan@getrolling.com">Dan Kibler</a>
 *   Extended to implement Acegi UserDetailsService interface by David Carter david@carter.net
 *   Modified by <a href="mailto:bwnoll@gmail.com">Bryan Noll</a> to work with 
 *   the new BaseDaoHibernate implementation that uses generics.
*/
@Repository("adGameDao")
public class AdGameDao extends GenericDaoJpa<AbstractAdGame, Long> implements fr.k2i.adbeback.dao.IAdGameDao {

    /**
     * Constructor that sets the entity to User.class.
     */
    public AdGameDao() {
        super(AbstractAdGame.class);
    }


    @Override
    public boolean RuleIsUsed(AdService adRule) {
        QAdChoise qAdChoise = QAdChoise.adChoise;
        JPAQuery query = new JPAQuery(getEntityManager());
        query.from(qAdChoise).where(qAdChoise.generatedBy.eq(adRule));
          return query.exists();
    }


    @Override
    public List<Double> sumTransactionByHourForDay(Media media, Date date) {
        LocalDate start = new LocalDate(date);
        LocalDate end = new LocalDate(date).plusDays(1);

        QAdGameTransaction transaction = QAdGameTransaction.adGameTransaction;


        List<Double> res = new ArrayList<Double>();
        int plusHours = 0;
        LocalDateTime begin = new LocalDateTime(start).plusHours(plusHours);
        do{
            JPAQuery query = new JPAQuery(getEntityManager());

            query.from(transaction).where(
                    transaction.media.eq(media),
                    transaction.generated.between(begin.toDate(),begin.plusHours(1).toDate()),
                    transaction.statusGame.eq(StatusGame.Win)
            );

            res.add(query.uniqueResult(transaction.amount.sum()));
            plusHours++;
            begin = new LocalDateTime(date).plusHours(plusHours);
        }while (begin.isAfter(end));


        return res;
    }


    @Override
    public List<AdGameTransaction> findTransactionsForDay(Media media, Date date,StatusGame ...statusGame) {
        LocalDate start = new LocalDate(date);
        LocalDate end = new LocalDate(date).plusDays(1);

        QAdGameTransaction transaction = QAdGameTransaction.adGameTransaction;
        JPAQuery query = new JPAQuery(getEntityManager());


        query.from(transaction).where(
                transaction.media.eq(media),
                transaction.generated.between(start.toDate(),end.toDate()),
                transaction.statusGame.in(statusGame)
        );
        query.orderBy(transaction.generated.asc());
        return query.list(transaction);
    }

    @Override
    public Page<AdGameTransaction> findTransactionsForDayPageable(Media media, Date date, Pageable pageable) {
        LocalDate start = new LocalDate(date);
        LocalDate end = new LocalDate(date).plusDays(1);

        QAdGameTransaction transaction = QAdGameTransaction.adGameTransaction;
        JPAQuery query = new JPAQuery(getEntityManager());


        query.from(transaction).where(
                transaction.media.eq(media),
                transaction.generated.between(start.toDate(),end.toDate())
        );

        long count = query.count();

        query.orderBy(transaction.generated.asc());

        query
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize());

        return new PageImpl<AdGameTransaction>(query.list(transaction),pageable,count);
    }

    @Override
    public Long countTodayTransactionsOk(Media media) {
        Date date = new Date();
        LocalDate start = new LocalDate(date);
        LocalDate end = new LocalDate(date).plusDays(1);

        QAdGameTransaction transaction = QAdGameTransaction.adGameTransaction;
        JPAQuery query = new JPAQuery(getEntityManager());


        query.from(transaction).where(
                transaction.media.eq(media),
                transaction.generated.between(start.toDate(),end.toDate()),
                transaction.statusGame.eq(StatusGame.Win)
        );

        return query.count();
    }

    @Override
    public Long countTodayTransactionsFailed(Media media) {
        Date date = new Date();
        LocalDate start = new LocalDate(date);
        LocalDate end = new LocalDate(date).plusDays(1);

        QAdGameTransaction transaction = QAdGameTransaction.adGameTransaction;
        JPAQuery query = new JPAQuery(getEntityManager());


        query.from(transaction).where(
                transaction.media.eq(media),
                transaction.generated.between(start.toDate(),end.toDate()),
                transaction.statusGame.in(StatusGame.Playing,StatusGame.Lost)
        );

        return query.count();
    }

    @Override
    public Double sumTransactionForDay(Media media, Date date) {
        LocalDate start = new LocalDate(date);
        LocalDate end = new LocalDate(date).plusDays(1);

        QAdGameTransaction transaction = QAdGameTransaction.adGameTransaction;
        JPAQuery query = new JPAQuery(getEntityManager());


        query.from(transaction).where(
                transaction.media.eq(media),
                transaction.generated.between(start.toDate(),end.toDate()),
                transaction.statusGame.eq(StatusGame.Win)
        );

        return query.uniqueResult(transaction.amount.sum());
    }

    @Override
    public Double sumWinBidsForDate(Brand brand, Date date) {
        LocalDate start = new LocalDate(date);
        LocalDate end = new LocalDate(date).plusDays(1);

        QAdGameTransaction transaction = QAdGameTransaction.adGameTransaction;
        QAdChoise adChoise = QAdChoise.adChoise;
        JPAQuery query = new JPAQuery(getEntityManager());

        query.from(transaction).join(transaction.choises, adChoise).where(
                adChoise.generatedBy.ad.brand.eq(brand),
                transaction.generated.between(start.toDate(), end.toDate())
        );

        BigDecimal sum = new BigDecimal(0);

        List<AdChoise> list = query.list(adChoise);
        for (AdChoise choise : list) {
            Integer number = choise.getNumber();
            Double winBidPrice = choise.getWinBidPrice();
            Map<Integer, AdResponsePlayer> answers = choise.getAdGame().getScore().getAnswers();
            AdResponsePlayer responsePlayer = answers.get(number);
            if(responsePlayer.getCorrectAnswer()){
                sum = sum.add(new BigDecimal(""+winBidPrice));
            }

        }

        return sum.doubleValue();
    }


}

