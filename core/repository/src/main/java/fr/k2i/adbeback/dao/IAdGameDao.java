package fr.k2i.adbeback.dao;

import fr.k2i.adbeback.core.business.ad.Brand;
import fr.k2i.adbeback.core.business.ad.rule.AdService;
import fr.k2i.adbeback.core.business.game.AbstractAdGame;
import fr.k2i.adbeback.core.business.game.AdGameTransaction;
import fr.k2i.adbeback.core.business.game.StatusGame;
import fr.k2i.adbeback.core.business.user.Media;
import org.joda.time.LocalDate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * User Data Access Object (IGenericDao) interface.
 *
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 */
public interface IAdGameDao extends IGenericDao<AbstractAdGame, Long> {

    @Transactional
    boolean RuleIsUsed(AdService adRule);

    @Transactional
    Double sumTransactionForDay(Media media, Date date);

    @Transactional
    Double sumWinBidsForDate(Brand brand, Date date);

    List<Double> sumTransactionByHourForDay(Media media, Date date);

    List<AdGameTransaction> findTransactionsForDay(Media media, Date date, StatusGame ... statusGame);

    Page<AdGameTransaction> findTransactionsForDayPageable(Media media, Date date, Pageable pageable);

    Long countTodayTransactionsOk(Media media);

    Long countTodayTransactionsFailed(Media media);

}

