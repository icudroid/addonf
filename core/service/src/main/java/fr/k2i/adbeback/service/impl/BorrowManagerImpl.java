package fr.k2i.adbeback.service.impl;

import fr.k2i.adbeback.core.business.player.Player;
import fr.k2i.adbeback.core.business.transaction.*;
import fr.k2i.adbeback.core.business.user.Media;
import fr.k2i.adbeback.dao.ITransactionDao;
import fr.k2i.adbeback.exception.LimitBorrowException;
import fr.k2i.adbeback.service.BorrowManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class BorrowManagerImpl extends GenericManagerImpl<Empreint, Long> implements BorrowManager {


    @Autowired
    private ITransactionDao transactionDao;


    @Override
    @Transactional
    public void createBorrow(Player player, Order order, Double amountBorrow) throws LimitBorrowException {


        Integer adAmountActive = transactionDao.calculateAmountActiveBorrow(player);
        if(adAmountActive!=null && adAmountActive>Empreint.MAX_AD_AMOUNT_BORROW){
            throw new LimitBorrowException();
        }


        Empreint empreint = new Empreint();

        LocalDateTime start = LocalDateTime.now();

        Instant instant = start.atZone(ZoneId.systemDefault()).toInstant();
        empreint.setStartDate(Date.from(instant));


        Date end = calculateDuration(amountBorrow);
        Integer adAmount = calculateAdAmount(amountBorrow);


        empreint.setEndDate(end);

        empreint.setAdAmountLeft(adAmount);
        empreint.setAdAmount(adAmount);
        empreint.setCredits(new ArrayList<Credit>());
        empreint.setStatus(EmpreintStatus.WAITING);


        ArrayList<TransactionHistory> histories = new ArrayList<>();
        histories.add(TransactionHistory.creation());

        empreint.setHistories(histories);

        empreint.setOrder(order);


        if(player.getWallet() == null){
            player.setWallet(new Wallet());
        }

        player.getWallet().addTransaction(empreint);

    }

    private Integer calculateAdAmount(Double amount) {
        return new Double(amount/Empreint.AD_AMOUNT).intValue();
    }

    private Date calculateDuration(Double amount) {
        LocalDate start = LocalDate.now();
        Double nbDaysBorrow = amount / (Empreint.NB_AD_BY_DAY.doubleValue()*Empreint.AD_AMOUNT);
        Double nbDaysBorrowTotal = nbDaysBorrow * Empreint.PERCENT_ADD_TO_END_DATE;

        LocalDate end = start.plusDays(nbDaysBorrowTotal.intValue());

        Instant instant = end.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
        return  Date.from(instant);
    }

}
