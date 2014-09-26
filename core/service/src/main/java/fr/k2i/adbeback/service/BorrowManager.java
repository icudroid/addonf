package fr.k2i.adbeback.service;

import fr.k2i.adbeback.bean.ResponseUser;
import fr.k2i.adbeback.core.business.ad.Ad;
import fr.k2i.adbeback.core.business.game.AbstractAdGame;
import fr.k2i.adbeback.core.business.game.StatusGame;
import fr.k2i.adbeback.core.business.goosegame.GooseLevel;
import fr.k2i.adbeback.core.business.player.Player;
import fr.k2i.adbeback.core.business.transaction.Empreint;
import fr.k2i.adbeback.core.business.transaction.Order;
import fr.k2i.adbeback.exception.LimitBorrowException;

import java.util.Map;


/**
 * Business Service Interface to handle communication between web and
 * persistence layer.
 *
 */
public interface BorrowManager extends GenericManager<Empreint, Long> {

    void createBorrow(Player player, Order order, Double amountBorrow) throws LimitBorrowException;
}
