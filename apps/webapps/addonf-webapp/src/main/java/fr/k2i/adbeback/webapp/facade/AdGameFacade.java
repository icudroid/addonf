package fr.k2i.adbeback.webapp.facade;

import fr.k2i.adbeback.bean.AdBean;
import fr.k2i.adbeback.bean.AdGameBean;
import fr.k2i.adbeback.bean.PossibilityBean;
import fr.k2i.adbeback.core.business.game.*;
import fr.k2i.adbeback.service.AdGameManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: dimitri
 * Date: 08/11/13
 * Time: 17:16
 * To change this template use File | Settings | File Templates.
 */
@Component
public class AdGameFacade {


    public static final String CORRECT_ANSWER = "correct";
    public static final String USER_ANSWER = "answer";
    public static final String USER_SCORE = "score";
    public static final String ID_ADGAME = "adGameId";
    public static final String NB_ERRORS = "errors";



    @Autowired
    private AdGameManager adGameManager;


    @Transactional
    public AdGameBean createAdGame(Long idPlayer,HttpServletRequest request) throws Exception {
        AdGame generateAdGame = adGameManager.generate(idPlayer);
        Map<Integer, Long> correctResponse = new HashMap<Integer, Long>();
        AdGameBean res = new AdGameBean();
        Map<Integer, AdChoise> choises = generateAdGame.getChoises();
        List<AdBean> game = new ArrayList<AdBean>();

        for (Map.Entry<Integer, AdChoise> entry : choises.entrySet()) {
            Integer num = entry.getKey();
            AdChoise adChoise = entry.getValue();
            AdBean adBean = new AdBean();
            List<PossibilityBean> possibilities = new ArrayList<PossibilityBean>();

            for (Possibility possibility : adChoise.getPossiblities()) {
                PossibilityBean pb = new PossibilityBean();

                if (possibility instanceof BrandPossibility) {
                    BrandPossibility p = (BrandPossibility) possibility;
                    pb.setType(0);
                    pb.setAnswer(p.getBrand().getLogo());
                }
                if (possibility instanceof ProductPossibility) {
                    ProductPossibility p = (ProductPossibility) possibility;
                    if (p.getProduct().getLogo() == null) {
                        pb.setType(2);
                        pb.setAnswer(p.getProduct().getName());
                    } else {
                        pb.setAnswer(p.getProduct().getLogo());
                        pb.setType(1);
                    }
                }
                if (possibility instanceof OpenPossibility) {
                    OpenPossibility p = (OpenPossibility) possibility;
                    pb.setType(3);
                    pb.setAnswer(p.getAnswer());
                }

                pb.setId(possibility.getId());
                possibilities.add(pb);

            }
            adBean.setPossibilities(possibilities);
            adBean.setQuestion(adChoise.getQuestion());
            adBean.setUrl(adChoise.getCorrect().getAd().getVideo());

            correctResponse.put(num, adChoise.getCorrect().getId());
            game.add(adBean);
        }
        res.setGame(game);
        res.setMinScore(generateAdGame.getMinScore());
        res.setTimeLimite((long) (generateAdGame.getMinScore() * 20));
        res.setTotalAds(choises.size());


        Map<Integer, Long> answers = new HashMap<Integer, Long>();
        request.getSession().setAttribute(USER_ANSWER, answers);
        request.getSession().setAttribute(CORRECT_ANSWER, correctResponse);
        request.getSession().setAttribute(USER_SCORE, 0);
        request.getSession().setAttribute(NB_ERRORS, 0);
        request.getSession().setAttribute(ID_ADGAME, generateAdGame.getId());

        return res;
    }

}
