package fr.k2i.adbeback.webapp.service;

import fr.k2i.adbeback.core.business.game.AbstractAdGame;
import fr.k2i.adbeback.core.business.game.AdGame;
import fr.k2i.adbeback.core.business.game.AdGameTransaction;
import fr.k2i.adbeback.core.business.game.StatusGame;
import fr.k2i.adbeback.core.business.transaction.Transaction;
import fr.k2i.adbeback.dao.IAdGameDao;
import fr.k2i.adbeback.dao.ITransactionDao;
import fr.k2i.adbeback.service.LuckyWinLotteryManager;
import fr.k2i.adbeback.webapp.bean.AdGameBean;
import fr.k2i.adbeback.webapp.bean.OrderDTO;
import fr.k2i.adbeback.webapp.bean.configure.PaymentConfigure;
import fr.k2i.adbeback.webapp.facade.AdGameFacade;
import fr.k2i.adbeback.webapp.state.AdPayChoice;
import fr.k2i.adbeback.webapp.state.AdPayCommand;
import fr.k2i.adbeback.webapp.state.AdPayFlowState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.webflow.core.collection.LocalParameterMap;
import org.springframework.webflow.execution.RequestContext;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * User: dimitri
 * Date: 06/10/14
 * Time: 16:00
 * Goal:
 */
@Service
public class PayAdService {

    @Autowired
    private IAdGameDao adGameDao;

    @Autowired
    private AdGameFacade adGameFacade;

    @Autowired
    private ITransactionDao transactionDao;

    @Autowired
    private LuckyWinLotteryManager luckyWinLotteryManager;

    public OrderDTO prepareRequestParameters(LocalParameterMap map) {
        //Map<String, Object> parameters = map.asAttributeMap().asMap();
        return new OrderDTO();
    }


    public String validate(OrderDTO order) {
        return "userUnknwow";
    }


    public List<AdPayChoice> payOptions(){
        return Arrays.asList(AdPayChoice.values());
    }


    public String whichOption(AdPayCommand command) throws Exception {
        switch (command.getChoice()) {
            case WIN:
                return "win";
            case PAY_AND_WIN:
                return "payAndWin";
            case BORROW:
                return "borrow";
        }
        throw new Exception("option " + command.getChoice() + "not found");
    }


    @Transactional
    public AdGameBean createLotteryAdGame(org.springframework.webflow.execution.RequestContext requestContext,AdPayFlowState state) throws Exception {
        HttpServletRequest request = ((HttpServletRequest) requestContext.getExternalContext().getNativeRequest());
        PaymentConfigure configure = PaymentConfigure.adbebackConfig();
        state.setLastTransactionId(configure.getIdTransaction());
        return adGameFacade.createLotteryAdGame(configure,request);
    }




    public String isLotteryWin(org.springframework.webflow.execution.RequestContext requestContext,AdPayFlowState state){
        if(transactionDao.isLotteryWinWithTransactionId(state.getLastTransactionId())) {
            HttpServletRequest request = ((HttpServletRequest) requestContext.getExternalContext().getNativeRequest());
            PaymentConfigure configure = (PaymentConfigure) request.getSession().getAttribute(AdGameFacade.CONFIGURE);
            Long idGame = (Long) request.getSession().getAttribute(AdGameFacade.ID_ADGAME);
            AbstractAdGame transaction = adGameDao.get(idGame);
            return luckyWinLotteryManager.doLottery(configure.getAmount(), (AdGameTransaction) transaction) ? "win" : "lost";
        }else {
            return "lost";
        }
    }
}


