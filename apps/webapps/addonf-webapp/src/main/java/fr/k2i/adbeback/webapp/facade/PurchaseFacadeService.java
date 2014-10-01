package fr.k2i.adbeback.webapp.facade;

import fr.k2i.adbeback.core.business.game.AdGame;
import fr.k2i.adbeback.core.business.player.Player;
import fr.k2i.adbeback.core.business.transaction.*;
import fr.k2i.adbeback.core.business.user.Media;
import fr.k2i.adbeback.dao.IMediaDao;
import fr.k2i.adbeback.dao.ITransactionDao;
import fr.k2i.adbeback.webapp.bean.BillLineBean;
import fr.k2i.adbeback.webapp.bean.HistoryAdGameBean;
import fr.k2i.adbeback.webapp.bean.OrderBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * User: dimitri
 * Date: 24/09/14
 * Time: 11:55
 * Goal:
 */
@Service
public class PurchaseFacadeService {

    @Autowired
    private PlayerFacade playerFacade;

    @Autowired
    private ITransactionDao transactionDao;


    @Transactional
    public Page<HistoryAdGameBean> getPurchases(Pageable pageRequest) {
        Player player = playerFacade.getCurrentPlayer();
        List<HistoryAdGameBean> list = new ArrayList<HistoryAdGameBean>();


        List<Transaction> historiesCreditGame = transactionDao.getHistoriesPurchase(player, pageRequest);

        for (Transaction transaction : historiesCreditGame) {
            if (transaction instanceof IDebit) {
                Debit debit = (Debit) transaction;
                list.add(HistoryAdGameBean.builder().adAmount(debit.getAdAmount()).generated(debit.getOrder().getOrderDate()).id(transaction.getId()).build());

            }else if (transaction instanceof IMicroPurchase) {
                MicroPurchase microPurchase = (MicroPurchase) transaction;
                list.add(HistoryAdGameBean.builder().adAmount(microPurchase.getAdAmount()).generated(microPurchase.getOrder().getOrderDate()).id(transaction.getId()).build());
            }
        }

        return new PageImpl<HistoryAdGameBean>(list, pageRequest,transactionDao.countHistoryPurchase(playerFacade.getCurrentPlayer().getId()));
    }


    @Transactional
    public OrderBean getPurchaseDetail(Long idPurchase) {

        Transaction transaction = transactionDao.get(idPurchase);

        Order order = null;
        Media  media = null;

        if (transaction instanceof IDebit) {
            Debit debit = (Debit) transaction;
            order = debit.getOrder();
        }else if (transaction instanceof IMicroPurchase) {
            MicroPurchase microPurchase = (MicroPurchase) transaction;
            order = microPurchase.getOrder();
        }

        List<BillLineBean> products = new ArrayList<>();
        List<MerchantProduct> orderProducts = order.getProducts();

        for (MerchantProduct orderProduct : orderProducts) {
            products.add(BillLineBean.builder().product(orderProduct.getProduct()).nb(orderProduct.getNb()).build());
        }


        return OrderBean.builder()
                .referenceMedia(order.getReferenceMedia())
                .orderDate(order.getOrderDate())
                .adAmount(transaction.getAdAmount())
                .media(order.getMedia().getName())
                .products(products)
            .build();
    }
}
