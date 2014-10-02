package fr.k2i.adbeback.dao;

import fr.k2i.adbeback.core.business.game.AbstractAdGame;
import fr.k2i.adbeback.core.business.game.AdGame;
import fr.k2i.adbeback.core.business.player.Player;
import fr.k2i.adbeback.core.business.transaction.Empreint;
import fr.k2i.adbeback.core.business.transaction.Transaction;
import fr.k2i.adbeback.core.business.transaction.TransactionHistory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * User: dimitri
 * Date: 24/09/14
 * Time: 11:56
 * Goal:
 */
public interface ITransactionDao extends IGenericDao<Transaction, Long>{

    List<Empreint> getActiveBorrows(Player player);

    List<fr.k2i.adbeback.core.business.game.AdGame> getHistoriesBorrowGame(Player player, Long tr, Pageable pageRequest);

    boolean isTransactionOkForPlayer(Long tr, Player player);

    long countHistoryBorrowGame(Long tr);

    long countHistoryCreditGame(Long idPlayer);

    Integer calculateAmountActiveBorrow(Player player);

    List<AbstractAdGame> getHistoriesCreditGame(Player player, Pageable pageRequest);

    List<Transaction> getHistoriesPurchase(Player player, Pageable pageRequest);

    long countHistoryPurchase(Long idPlayer);
}
