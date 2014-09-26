package fr.k2i.adbeback.dao.jpa;

import com.mysema.query.jpa.impl.JPAQuery;
import fr.k2i.adbeback.core.business.game.AdGame;
import fr.k2i.adbeback.core.business.game.QAdGame;
import fr.k2i.adbeback.core.business.player.Player;
import fr.k2i.adbeback.core.business.player.QPlayer;
import fr.k2i.adbeback.core.business.transaction.*;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

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
    public List<AdGame> getHistoriesBorrowGame(Player player, Long tr, Pageable pageRequest) {
        JPAQuery query = new JPAQuery(getEntityManager());

        //QPlayer qPlayer = QPlayer.player;

        //QTransaction qTransaction = QTransaction.transaction;
        QEmpreint qEmpreint = QEmpreint.empreint;
        QCredit credits = QCredit.credit;
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
    public long countHistoryGame(Long tr) {
        JPAQuery query = new JPAQuery(getEntityManager());

        QEmpreint qEmpreint = QEmpreint.empreint;

        QCredit qCredit = QCredit.credit;

        query.from(qEmpreint).join(qEmpreint.credits,qCredit).where(
                qEmpreint.id.eq(tr)
        );

        return query.singleResult(qCredit.count());
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
