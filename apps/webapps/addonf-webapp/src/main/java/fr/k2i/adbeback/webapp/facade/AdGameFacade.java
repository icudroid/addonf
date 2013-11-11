package fr.k2i.adbeback.webapp.facade;

import fr.k2i.adbeback.bean.AdBean;
import fr.k2i.adbeback.bean.AdGameBean;
import fr.k2i.adbeback.bean.PossibilityBean;
import fr.k2i.adbeback.bean.ResponseAdGameBean;
import fr.k2i.adbeback.core.business.game.*;
import fr.k2i.adbeback.service.AdGameManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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
    public static final String ADS_VIDEO = "adsVideo";



    @Autowired
    private AdGameManager adGameManager;


    @Transactional
    public AdGameBean createAdGame(Long idPlayer,HttpServletRequest request) throws Exception {
        AdGame generateAdGame = adGameManager.generate(idPlayer);
        List<String> adsVideo = new ArrayList<String>();

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
            //adBean.setUrl(adChoise.getCorrect().getAd().getVideo());
            adsVideo.add(adChoise.getCorrect().getAd().getVideo());

            correctResponse.put(num, adChoise.getCorrect().getId());
            game.add(adBean);
        }
        res.setGame(game);
        res.setMinScore(generateAdGame.getMinScore());
        res.setTimeLimite((long) (generateAdGame.getMinScore() * 20));
        res.setTotalAds(choises.size());


        Map<Integer, Long> answers = new HashMap<Integer, Long>();
        HttpSession session = request.getSession();
        session.setAttribute(USER_ANSWER, answers);
        session.setAttribute(CORRECT_ANSWER, correctResponse);
        session.setAttribute(USER_SCORE, 0);
        session.setAttribute(NB_ERRORS, 0);
        session.setAttribute(ID_ADGAME, generateAdGame.getId());
        session.setAttribute(ADS_VIDEO, adsVideo);


        return res;
    }

    @Transactional
    public ResponseAdGameBean userResponse(HttpServletRequest request, Integer index, Long responseId) throws Exception {
        ResponseAdGameBean res = new ResponseAdGameBean();
        Map<Integer, Long> correctResponse = (Map<Integer, Long>) request
                .getSession().getAttribute(CORRECT_ANSWER);
        Integer score = (Integer) request.getSession().getAttribute(USER_SCORE);
        Map<Integer, Long> answers = (Map<Integer, Long>) request.getSession()
                .getAttribute(USER_ANSWER);
        Long correctId = correctResponse.get(index);

        Integer nbErrs = (Integer) request.getSession().getAttribute(NB_ERRORS);

        if (answers.get(index) == null && correctId.equals(responseId)) {
            res.setCorrect(true);
            score++;
            res.setScore(score);
            request.getSession().setAttribute(USER_SCORE, score);
            answers.put(index, responseId);
        } else {
            res.setCorrect(false);
            res.setScore(score);
            answers.put(index, -1L);
            nbErrs++;
            request.getSession().setAttribute(NB_ERRORS, nbErrs);
            if (nbErrs > 6) {
                res.setStatus(fr.k2i.adbeback.bean.StatusGame.Lost);
                emptyGameSession(request);
                adGameManager.saveResponses((Long) request.getSession().getAttribute(ID_ADGAME), score, answers);
                return res;
            }
        }

            if (index < correctResponse.size()-1) {
                res.setStatus(fr.k2i.adbeback.bean.StatusGame.Playing);
            } else {
                res.setStatus(fr.k2i.adbeback.bean.StatusGame.WinLimitTime);
                adGameManager.saveResponses((Long) request.getSession().getAttribute(ID_ADGAME), score, answers);
            }

        return res;

    }

    private void emptyGameSession(HttpServletRequest request) {
        //To change body of created methods use File | Settings | File Templates.
    }

    @Transactional
    public ResponseAdGameBean noUserResponse(HttpServletRequest request, Integer index) throws Exception {
        ResponseAdGameBean res = new ResponseAdGameBean();
        Integer score = (Integer) request.getSession().getAttribute(USER_SCORE);
        Map<Integer, Long> answers = (Map<Integer, Long>) request.getSession()
                .getAttribute(USER_ANSWER);
        Map<Integer, Long> correctResponse = (Map<Integer, Long>) request
                .getSession().getAttribute(CORRECT_ANSWER);
        Integer nbErrs = (Integer) request.getSession().getAttribute(NB_ERRORS);

        answers.put(index, null);
        res.setCorrect(false);
        res.setScore(score);
        nbErrs++;
        request.getSession().setAttribute(USER_SCORE, score);
        request.getSession().setAttribute(NB_ERRORS, nbErrs);

        if (nbErrs > 6) {
            res.setStatus(fr.k2i.adbeback.bean.StatusGame.Lost);
            emptyGameSession(request);
            adGameManager.saveResponses((Long) request.getSession()
                    .getAttribute(ID_ADGAME), score, answers);
        } else if (index +1 < correctResponse.size()) {
            res.setStatus(fr.k2i.adbeback.bean.StatusGame.Playing);
        } else if(score >0){
            res.setStatus(fr.k2i.adbeback.bean.StatusGame.WinLimitTime);
            adGameManager.saveResponses((Long) request.getSession()
                    .getAttribute(ID_ADGAME), score, answers);
        }else{
            res.setStatus(fr.k2i.adbeback.bean.StatusGame.Lost);
            emptyGameSession(request);
            adGameManager.saveResponses((Long) request.getSession()
                    .getAttribute(ID_ADGAME), score, answers);
        }

        return res;

    }
}
