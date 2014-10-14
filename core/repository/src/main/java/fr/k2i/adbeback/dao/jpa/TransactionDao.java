package fr.k2i.adbeback.dao.jpa;

import com.mysema.query.jpa.impl.JPAQuery;
import fr.k2i.adbeback.core.business.game.*;
import fr.k2i.adbeback.core.business.player.Player;
import fr.k2i.adbeback.core.business.player.QPlayer;
import fr.k2i.adbeback.core.business.transaction.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * User: dimitri
 * Date: 24/09/14
 * Time: 11:59
 * Goal:
 */

@Repository
public class TransactionDao extends GenericDaoJpa<Transaction, Long> implements fr.k2i.adbeback.dao.ITransactionDao {

    public TransactionDao() {
        super(Transaction.class);
    }


    @Override
    public List<Empreint> getActiveBorrows(Player player) {
        JPAQuery query = new JPAQuery(getEntityManager());

        QPlayer qPlayer = QPlayer.player;

        QTransaction qTransaction = QTransaction.transaction;
        QEmpreint qEmpreint = qTransaction.as(QEmpreint.class);

        QWallet qWallet = QWallet.wallet;

        query.from(qPlayer).join(qPlayer.wallet,qWallet).join(qWallet.transactions,qTransaction).where(
                qTransaction.instanceOf(qEmpreint.getType())
                .and(qEmpreint.status.in(EmpreintStatus.WAITING,EmpreintStatus.STARTED))
                .and(qPlayer.eq(player))
        );
        return query.list(qEmpreint);
    }


    @Override
    public List<AbstractAdGame> getHistoriesCreditGame(Player player, Pageable pageRequest) {
        JPAQuery query = new JPAQuery(getEntityManager());

        QPlayer qPlayer = QPlayer.player;
        QWallet qWallet = QWallet.wallet;

        QTransaction qTransaction = QTransaction.transaction;

        QCreditAdGame qCreditAdGame = qTransaction.as(QCreditAdGame.class);
        QCreditLostMicroPurchase qCreditLostMicroPurchase = qTransaction.as(QCreditLostMicroPurchase.class);

        query.from(qPlayer)
                .join(qPlayer.wallet,qWallet)
                .join(qWallet.transactions,qTransaction)
                .where(
                        qPlayer.eq(player).and(qTransaction.instanceOfAny(qCreditAdGame.getType(), qCreditLostMicroPurchase.getType()))
                );

        query.orderBy(qCreditAdGame.adGame.generated.desc(), qCreditLostMicroPurchase.microPurchase.adGame.generated.desc());
        query
                .offset(pageRequest.getOffset())
                .limit(pageRequest.getPageSize());

        List<Transaction> transactions = query.list(qTransaction);


        List<AbstractAdGame> res = new ArrayList<>();

        for (Transaction transaction : transactions) {
            if (transaction instanceof ICreditAdGame) {
                CreditAdGame iCreditAdGame = (CreditAdGame) transaction;
                res.add(iCreditAdGame.getAdGame());

            }else if (transaction instanceof ICreditLostMicroPurchase) {
                CreditLostMicroPurchase iCreditAdGame = (CreditLostMicroPurchase) transaction;
                res.add(iCreditAdGame.getMicroPurchase().getAdGame());
            }
        }

        return res;
    }

    @Override
    public List<Transaction> getHistoriesPurchase(Player player, Pageable pageRequest) {

        JPAQuery query = new JPAQuery(getEntityManager());

        QPlayer qPlayer = QPlayer.player;
        QWallet qWallet = QWallet.wallet;

        QTransaction qTransaction = QTransaction.transaction;

        QDebit qDebit = qTransaction.as(QDebit.class);
        QMicroPurchase qMicroPurchase = qTransaction.as(QMicroPurchase.class);

        query.from(qPlayer)
                .join(qPlayer.wallet,qWallet)
                .join(qWallet.transactions,qTransaction)
                .where(
                        qPlayer.eq(player).and(qTransaction.instanceOfAny(qDebit.getType(), qMicroPurchase.getType()))
                );

        query.orderBy(qDebit.order.orderDate.desc(), qMicroPurchase.order.orderDate.desc());
        query
                .offset(pageRequest.getOffset())
                .limit(pageRequest.getPageSize());

        return query.list(qTransaction);
    }

    @Override
    public long countHistoryPurchase(Long idPlayer) {
        JPAQuery query = new JPAQuery(getEntityManager());

        QPlayer qPlayer = QPlayer.player;
        QWallet qWallet = QWallet.wallet;

        QTransaction qTransaction = QTransaction.transaction;

        QDebit qDebit = qTransaction.as(QDebit.class);
        QMicroPurchase qMicroPurchase = qTransaction.as(QMicroPurchase.class);

        query.from(qPlayer)
                .join(qPlayer.wallet,qWallet)
                .join(qWallet.transactions,qTransaction)
                .where(
                        qPlayer.id.eq(idPlayer).and(qTransaction.instanceOfAny(qDebit.getType(), qMicroPurchase.getType()))
                );

        return query.singleResult(qTransaction.count());
    }

    @Override
    public boolean isLotteryWinWithTransactionId(String lastTransactionId) {
        JPAQuery query = new JPAQuery(getEntityManager());

        QAdGameTransaction gameTransaction = QAdGameTransaction.adGameTransaction;

        query.from(gameTransaction)
                .where(
                        gameTransaction.statusGame.eq(StatusGame.Win).and(gameTransaction.idTransaction.eq(lastTransactionId))
                );

        return query.exists();
    }

    @Override
    public List<AdGame> getHistoriesBorrowGame(Player player, Long tr, Pageable pageRequest) {
        JPAQuery query = new JPAQuery(getEntityManager());

        //QPlayer qPlayer = QPlayer.player;

        //QTransaction qTransaction = QTransaction.transaction;
        QEmpreint qEmpreint = QEmpreint.empreint;
        QCreditRefundBorrow credits = QCreditRefundBorrow.creditRefundBorrow;
        QAdGame qAdGame = QAdGame.adGame;

        query.from(qEmpreint).join(qEmpreint.credits,credits).join(credits.adGame,qAdGame).where(
                qEmpreint.id.eq(tr)

        );

        query.orderBy(qAdGame.generated.desc());
        query
                .offset(pageRequest.getOffset())
                .limit(pageRequest.getPageSize());

        return query.list(qAdGame);
    }

    @Override
    public boolean isTransactionOkForPlayer(Long tr, Player player) {

        JPAQuery query = new JPAQuery(getEntityManager());

        QPlayer qPlayer = QPlayer.player;

        QTransaction qTransaction = QTransaction.transaction;
        QEmpreint qEmpreint = qTransaction.as(QEmpreint.class);

        QWallet qWallet = QWallet.wallet;

        query.from(qPlayer).join(qPlayer.wallet,qWallet).join(qWallet.transactions, qTransaction).where(
                qTransaction.instanceOf(qEmpreint.getType())
                        .and(qTransaction.id.eq(tr))
        );

        return query.exists();
    }

    @Override
    public long countHistoryBorrowGame(Long tr) {
        JPAQuery query = new JPAQuery(getEntityManager());

        QEmpreint qEmpreint = QEmpreint.empreint;

        QCreditRefundBorrow qCredit = QCreditRefundBorrow.creditRefundBorrow;

        query.from(qEmpreint).join(qEmpreint.credits,qCredit).where(
                qEmpreint.id.eq(tr)
        );

        return query.singleResult(qCredit.count());
    }


    @Override
    public long countHistoryCreditGame(Long playerId) {
        JPAQuery query = new JPAQuery(getEntityManager());

        QPlayer qPlayer = QPlayer.player;
        QWallet qWallet = QWallet.wallet;

        QTransaction qTransaction = QTransaction.transaction;

        QCreditAdGame qCreditAdGame = qTransaction.as(QCreditAdGame.class);
        QCreditLostMicroPurchase qCreditLostMicroPurchase = qTransaction.as(QCreditLostMicroPurchase.class);


        query.from(qPlayer)
                .join(qPlayer.wallet, qWallet)
                .join(qWallet.transactions, qTransaction)
                .where(
                        qPlayer.id.eq(playerId).and(qTransaction.instanceOfAny(qCreditAdGame.getType(), qCreditLostMicroPurchase.getType()))
                );

        return query.singleResult(qTransaction.count());
    }

    @Override
    public Integer calculateAmountActiveBorrow(Player player) {
        JPAQuery query = new JPAQuery(getEntityManager());

        QPlayer qPlayer = QPlayer.player;

        QTransaction qTransaction = QTransaction.transaction;
        QEmpreint qEmpreint = qTransaction.as(QEmpreint.class);

        QWallet qWallet = QWallet.wallet;

        query.from(qPlayer).join(qPlayer.wallet,qWallet).join(qWallet.transactions,qTransaction).where(
                qTransaction.instanceOf(qEmpreint.getType())
                        .and(qEmpreint.status.in(EmpreintStatus.WAITING,EmpreintStatus.STARTED))
                        .and(qPlayer.eq(player))
        );
        return query.uniqueResult(qEmpreint.adAmountLeft.sum());
    }




}
