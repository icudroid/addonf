package fr.k2i.adbeback.webapp.facade;

import fr.k2i.adbeback.core.business.ad.*;
import fr.k2i.adbeback.core.business.ad.rule.AdRule;
import fr.k2i.adbeback.core.business.ad.rule.AdService;
import fr.k2i.adbeback.core.business.game.*;
import fr.k2i.adbeback.core.business.goosegame.*;
import fr.k2i.adbeback.core.business.partener.Reduction;
import fr.k2i.adbeback.core.business.player.Address;
import fr.k2i.adbeback.core.business.player.AgeGroup;
import fr.k2i.adbeback.core.business.player.AnonymPlayer;
import fr.k2i.adbeback.core.business.player.Player;
import fr.k2i.adbeback.dao.*;
import fr.k2i.adbeback.service.AdGameManager;
import fr.k2i.adbeback.service.GooseGameManager;
import fr.k2i.adbeback.webapp.bean.*;
import fr.k2i.adbeback.webapp.bean.StatusGame;
import fr.k2i.adbeback.webapp.bean.configure.PaymentConfigure;
import fr.k2i.adbeback.webapp.bean.configure.information.*;
import fr.k2i.adbeback.webapp.bean.configure.url.Url;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: dimitri
 * Date: 08/11/13
 * Time: 17:16
 * To change this template use File | Settings | File Templates.
 */
@Component
public class AdGameFacade {


    public static final String LIMITED_TIME = "limitedTime";
    public static final String CORRECT_ANSWER = "correct";
    public static final String USER_ANSWER = "answer";
    public static final String USER_SCORE = "score";
    public static final String ID_ADGAME = "adGameId";
    public static final String NB_ERRORS = "errors";
    public static final String ADS_VIDEO = "adsVideo";
    public static final String GOOSE_LEVEL = "gooseLevel";
    public static final String MAX_ERRORS = "max_err";

    public static final String GAME_END_TIME = "endTime";
    public static final String GAME_RESULT = "result";
    public static final String PLAYER_GOOSE_GAME = "gooseGameCases";
    public static final String PLAYER_TOKEN = "token";

    public static final String AD_CHOISES = "adChoises";

    public static final String CALL_BACK_URL = "callBack";
    public static final String CALL_SYS_URL = "callSys";

    public static final double AVERAGE_AP_PRICE = 0.24;


    @Autowired
    private IAdDao adDao;

    @Autowired
    private IAdRuleDao adRuleDao;

    @Autowired
    private IGooseGameDao gooseGameDao;

    @Autowired
    private AdGameManager adGameManager;

    @Autowired
    private PlayerFacade playerFacade;

    @Autowired
    private GooseGameManager gooseGameManager;

    @Autowired
    private IPlayerDao playerDao;

    @Autowired
    private IGooseLevelDao gooseLevelDao;

    @Autowired
    private IGooseTokenDao gooseTokenDao;


    @Value("${addonf.tmp.location:/tmp/}")
    private String tmpPath;


    @Value("${addonf.music.location:/musics/}")
    private String musicPath;

    @Value("${addonf.video.location:/movies/}")
    private String videoPath;


    @Autowired
    private IViewedAdDao viewedAdDao;

    @Autowired
    private ICityDao cityDao;

    @Autowired
    private ICountryDao countryDao;



    @Transactional
    public AdGameBean createAdGame(PaymentConfigure configure, HttpServletRequest request) throws Exception {

        HttpSession session = request.getSession();

        //1 : create Anonym User
        Player player = getAnonymousPlayer(configure);

        //moyen 0.20 euro / pub
        //2 : estimate min score
        Double nb = configure.getAmount() / AVERAGE_AP_PRICE;
        Double left = configure.getAmount() % AVERAGE_AP_PRICE;
        Integer minScore =  nb.intValue();

        //one more if exemple 3.75 => 4
        if(left!=0){
            minScore++;
        }

        //3 : find level for NB ads
        SingleGooseLevel gooseLevel = gooseLevelDao.findForNbAds(minScore);

        int maxErr = gooseLevel.getNbMaxAdByPlay() - gooseLevel.getMinScore();


        //4 : generate game
        AbstractAdGame generateAdGame = adGameManager.generate(configure.getSelfAd(),configure.getIdPartner(),configure.getIdTransaction(),player.getId(),gooseLevel);

        //5 : set urlCall in session
        session.setAttribute(CALL_BACK_URL,configure.getCallBackUrl());
        session.setAttribute(CALL_SYS_URL,configure.getCallBackUrl());

        List<String> adsVideo = new ArrayList<String>();
        Map<Integer, Long> correctResponse = new HashMap<Integer, Long>();
        Map<Integer, AdChoise> choises = generateAdGame.getChoises();

        AdGameBean res = createAdBeans(adsVideo, correctResponse, choises);

        //6 : time limit
        if(gooseLevel.getLimitedTime()){
            res.setTimeLimite((long) (minScore * 30));//30 seconds by ad
        }else{
            res.setTimeLimite(-1L);
        }

        res.setTotalAds(choises.size());

        GooseToken gooseToken =  playerDao.getPlayerGooseToken(player.getId(), gooseLevel.getId());

        boolean multiple = (gooseLevel instanceof IMultiGooseLevel);

        if(gooseToken==null){
            gooseToken = new GooseToken();
            gooseToken.setGooseCase(gooseLevel.getStartCase());
            player.addGooseToken(gooseToken);
        }else if(!multiple){
            gooseToken.setGooseCase(gooseLevel.getStartCase());
        }

        res.setMultiple(multiple);

        GooseCase gooseCase = gooseToken.getGooseCase();
        Integer number = gooseCase.getNumber();

        List<PlayerGooseGame> pgg = new ArrayList<PlayerGooseGame>();
        List<GooseCase> cases = gooseGameManager.getCases(gooseLevel, number,  7);
        for (GooseCase c : cases) {
            Integer type = c.ihmValue();
            pgg.add(new PlayerGooseGame(c.getNumber().equals(number), c.getNumber(), type));
        }

        res.setGooseGames(pgg);
        res.setUserToken(gooseCase.getNumber());

        if(gooseLevel instanceof ISingleGooseLevel){
            res.setMinScore(((SingleGooseLevel)gooseLevel).getMinScore());
        }

        Map<Integer, Long> answers = new HashMap<Integer, Long>();

        session.setAttribute(LIMITED_TIME,gooseLevel.getLimitedTime());
        session.setAttribute(PLAYER_GOOSE_GAME, pgg);
        session.setAttribute(USER_ANSWER, answers);
        session.setAttribute(CORRECT_ANSWER, correctResponse);
        session.setAttribute(USER_SCORE, 0);
        session.setAttribute(MAX_ERRORS, maxErr);
        session.setAttribute(NB_ERRORS, 0);
        session.setAttribute(ID_ADGAME, generateAdGame.getId());
        session.setAttribute(ADS_VIDEO, adsVideo);
        session.setAttribute(GAME_RESULT, null);
        session.setAttribute(PLAYER_TOKEN, gooseCase);
        session.setAttribute(GOOSE_LEVEL, gooseLevel.getId());
        session.setAttribute(GAME_END_TIME, new Date().getTime()+(res.getTimeLimite())*1000);
        session.setAttribute(AD_CHOISES,choises);

        return res;
    }

    /**
     *
     * @param adsVideo
     * @param correctResponse
     * @param choises
     * @return
     */
    private AdGameBean createAdBeans(List<String> adsVideo, Map<Integer, Long> correctResponse, Map<Integer, AdChoise> choises) {

        AdGameBean res = new AdGameBean();
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
                    pb.setAnswerImage(p.getBrand().getLogo());
                }
                if (possibility instanceof ProductPossibility) {
                    ProductPossibility p = (ProductPossibility) possibility;
                    if (p.getProduct().getLogo() == null) {
                        pb.setType(2);
                        pb.setAnswerText(p.getProduct().getName());
                    } else {
                        pb.setAnswerImage(p.getProduct().getLogo());
                        pb.setType(1);
                    }
                }
                if (possibility instanceof OpenPossibility) {
                    OpenPossibility p = (OpenPossibility) possibility;
                    pb.setType(3);
                    pb.setAnswerImage(p.getAnswerImage());
                    pb.setAnswerText(p.getAnswerText());
                }

                pb.setId(possibility.getId());
                possibilities.add(pb);

            }
            adBean.setPossibilities(possibilities);
            adBean.setQuestion(adChoise.getQuestion());


            Ad ad = adChoise.getCorrect().getAd();
            if(ad instanceof VideoAd){
                adBean.setType(AdBean.TypeAd.VIDEO);
            }else if(ad instanceof StaticAd){
                adBean.setType(AdBean.TypeAd.STATIC);
                adBean.setDuration(ad.getDuration());
            }else if(ad instanceof AudioAd){
                adBean.setType(AdBean.TypeAd.AUDIO);
            }

            adsVideo.add(ad.getAdFile());
            //adBean.setUrl(adChoise.getCorrect().getAd().getVideo());

            correctResponse.put(num, adChoise.getCorrect().getId());
            game.add(adBean);
        }

        res.setGame(game);
        return res;
    }

    /**
     *
     * @param configure
     * @return
     */
    private Player getAnonymousPlayer(PaymentConfigure configure) {
        Player player = new AnonymPlayer();
        player.setLastName(UUID.randomUUID().toString());

        List<Information> informations = configure.getInformations();
        for (Information information : informations) {
            if (information instanceof AgeInformation) {
                AgeInformation ageInf = (AgeInformation) information;
                player.setAgeGroup(AgeGroup.fromAge(ageInf.getOld()));
            }


            if (information instanceof CityInformation) {
                CityInformation cityInf = (CityInformation) information;
                Address address = new Address();
                address.setCity(cityDao.findByZipcodeAndCityAndCountry_Code(cityInf.getZipCode(),cityInf.getCityName(),cityInf.getCountryCode()));
                player.setAddress(address);
            }

            if (information instanceof CountryInformation) {
                CountryInformation countryInf = (CountryInformation) information;
                Address address = new Address();
                address.setCountry(countryDao.findByCode(countryInf.getCountryCode()));
                player.setAddress(address);
            }


            if (information instanceof SexInformation) {
                SexInformation sexInf = (SexInformation) information;
                player.setSex(sexInf.getSex());
            }
        }
        return playerDao.save(player);
    }

    @Transactional
    public ResponseAdGameBean userResponse(HttpServletRequest request, Integer index, Long responseId) throws Exception {
        ResponseAdGameBean res = new ResponseAdGameBean();
        HttpSession session = request.getSession();
        Long end = (Long) session.getAttribute(GAME_END_TIME);
        Boolean limited = (Boolean) session.getAttribute(LIMITED_TIME);

        Player currentPlayer = playerFacade.getCurrentPlayer();
        if(limited && end < new Date().getTime()){
            res.setStatus(StatusGame.WinLimitTime);
            LimiteTimeAdGameBean gameResult = computeResultGame(request);
            session.setAttribute(GAME_RESULT, gameResult);
        }else{

            Map<Integer, Long> correctResponse = (Map<Integer, Long>) session.getAttribute(CORRECT_ANSWER);
            Integer score = (Integer) session.getAttribute(USER_SCORE);
            Map<Integer, Long> answers = (Map<Integer, Long>) session
                    .getAttribute(USER_ANSWER);
            Long correctId = correctResponse.get(index);

            Integer nbErrs = (Integer) session.getAttribute(NB_ERRORS);

            GooseCase gooseCase = null;

            AdRule adRule = doStat(request, index, currentPlayer);

            if (answers.get(index) == null && correctId!=null && correctId.equals(responseId)) {
                res.setCorrect(true);
                score++;
                res.setScore(score);
                session.setAttribute(USER_SCORE, score);
                answers.put(index, responseId);

                gooseCase = goHeadToken(request);


                adDao.updateAmountForAd((AdService) adRule);


            } else {
                res.setCorrect(false);
                res.setScore(score);
                answers.put(index, -1L);
                nbErrs++;
                session.setAttribute(NB_ERRORS, nbErrs);
/*            if (nbErrs > 6) {
                res.setStatus(StatusGame.Lost);
                emptyGameSession(request);
                adGameManager.saveResponses((Long) request.getSession().getAttribute(ID_ADGAME), score, answers);
                return res;
            }*/
            }

            Integer maxErr = (Integer) session.getAttribute(MAX_ERRORS);

            if (nbErrs < maxErr  && index < correctResponse.size()-1  && !(gooseCase instanceof EndLevelGooseCase)) {
                res.setStatus(StatusGame.Playing);
            } else {
                fr.k2i.adbeback.core.business.game.StatusGame statusGame = null;
                if(gooseCase instanceof EndLevelGooseCase){
                    res.setStatus(StatusGame.WinLimitTime);
                    statusGame = fr.k2i.adbeback.core.business.game.StatusGame.Win;
                    Url callSys = (Url) request.getSession().getAttribute(CALL_SYS_URL);
                    sendCallBack(callSys.getKo());
                }else{
                    res.setStatus(StatusGame.Lost);
                    statusGame = fr.k2i.adbeback.core.business.game.StatusGame.Lost;
                    Url callSys = (Url) request.getSession().getAttribute(CALL_SYS_URL);
                    sendCallBack(callSys.getOk());
                }
                LimiteTimeAdGameBean gameResult = computeResultGame(request);
                session.setAttribute(GAME_RESULT, gameResult);

                adGameManager.saveResponses((Long) session.getAttribute(ID_ADGAME), score, answers, statusGame);
                //emptyGameSession(request);
            }
        }


        GooseToken gooseToken =  playerDao.getPlayerGooseToken(currentPlayer.getId(), (Long) session.getAttribute(GOOSE_LEVEL));
        Integer number = gooseToken.getGooseCase().getNumber();
        res.setUserToken(number);

        return res;

    }

    private void sendCallBack(String url) throws IOException {
        HttpGet httpget = new HttpGet(url);
        HttpClients.createDefault().execute(httpget);
    }

    @Transactional
    private AdRule doStat(HttpServletRequest request, Integer index, Player currentPlayer) {
        Map<Integer, AdChoise> choises = (Map<Integer, AdChoise>) request.getSession().getAttribute(AD_CHOISES);
        AdChoise adChoise = choises.get(index);
        AdRule adRule = adRuleDao.get(adChoise.getGeneratedBy().getId());


        ViewedAd viewedAd = viewedAdDao.findForToday(currentPlayer, (AdService) adRule);
        if(viewedAd==null){
            viewedAd = new ViewedAd(currentPlayer,(AdService) adRule);
            viewedAdDao.save(viewedAd);
        }else{
            viewedAd.view();
        }
        return adRule;
    }


    
    @Transactional
    private LimiteTimeAdGameBean computeResultGame(HttpServletRequest request) throws Exception {
        Player player = playerFacade.getCurrentPlayer();

        GooseToken gooseToken =  playerDao.getPlayerGooseToken(player.getId(), (Long) request.getSession().getAttribute(GOOSE_LEVEL));

        GooseCase gooseCase = gooseToken.getGooseCase();
        GooseLevel level = gooseCase.getLevel();
        LimiteTimeAdGameBean gameResult = new LimiteTimeAdGameBean();
        if (gooseCase instanceof AddPotGooseCase) {
            AddPotGooseCase add = (AddPotGooseCase) gooseCase;
            gooseGameManager.addToLevel(level,add.getValue());
            StringBuilder sb = new StringBuilder();
            sb.append("Vous venez d'ajouter ");
            sb.append(add.getValue());
            sb.append(" d'euros à la cagnotte.");
            gameResult.setMessage(sb.toString());
        }else if (gooseCase instanceof DeadGooseCase) {
            gooseCase = level.getStartCase();
            gooseToken.setGooseCase(level.getStartCase());
            gameResult.setMessage("Vous allez à la case : "+gooseCase.getNumber());
        }else if (gooseCase instanceof EndLevelGooseCase) {
            if(level instanceof IMultiGooseLevel){
                StringBuilder sb = new StringBuilder();
                sb.append("Bravo vous venez de remporté la cagnotte d'une valeur de ");
                sb.append(((MultiGooseLevel)level).getValue());
                sb.append(" euros");
                gooseGameManager.resetLevelValue(level);
                GooseWin win = new GooseWin();
                win.setGooseLevel(level);
                win.setValue(((MultiGooseLevel)level).getValue());
                win.setWindate(new Date());
                win.setPlayer(player);
                player.getWins().add(win);
                playerDao.savePlayer(player);
                gameResult.setMessage(sb.toString());
            }else{
                //download music
            }
        }else if (gooseCase instanceof JumpGooseCase) {
            JumpGooseCase jump = (JumpGooseCase) gooseCase;
            gooseCase = jump.getJumpTo();
            gooseToken.setGooseCase(gooseCase);
            gameResult.setMessage("Vous allez à la case : "+gooseCase.getNumber());
        }else if (gooseCase instanceof ReductionGooseCase) {
            ReductionGooseCase reduc = (ReductionGooseCase) gooseCase;
            StringBuilder sb = new StringBuilder();
            sb.append("Vous venez de gagner un bon d'achat d'une valeur de ");
            Reduction reduction = reduc.getReduction();
            if(reduction.getPercentageValue()!=null){
                sb.append(reduction.getPercentageValue());
                sb.append(" %, chez ");
                sb.append(reduction.getPartner().getName());
            }else{
                sb.append(reduction.getPercentageValue());
                sb.append(" euros, chez ");
                sb.append(reduction.getPartner().getName());
            }
            gameResult.setMessage(sb.toString());
        }else if (gooseCase instanceof JailGooseCase) {
            GooseCase startCase = (GooseCase) request.getSession().getAttribute(PLAYER_TOKEN);
            if(gooseCase.getNumber().equals(startCase.getNumber())){
                Integer score = (Integer) request.getSession().getAttribute(USER_SCORE);
                if(score == 6){
                    //go next case
                    GooseCase caseByNumber = gooseGameManager.getCaseByNumber(gooseCase.getNumber() + 1, gooseCase.getLevel());
                    gooseToken.setGooseCase(caseByNumber);
                    playerDao.savePlayer(player);
                    StringBuilder sb = new StringBuilder();
                    sb.append("Vous venez de sortir de prison");
                    gameResult.setMessage(sb.toString());
                }else{
                    StringBuilder sb = new StringBuilder();
                    sb.append("Vous êtes en prison, pour sortir vous devez faire 6 points supplémentaires");
                    gameResult.setMessage(sb.toString());
                }
            }else{
                StringBuilder sb = new StringBuilder();
                sb.append("Vous êtes en prison, pour sortir vous devez faire 6 points supplémentaires");
                gameResult.setMessage(sb.toString());
            }
        }
        return gameResult;
    }

    @Transactional
    private GooseCase goHeadToken(HttpServletRequest request) throws Exception {

        Player player = playerFacade.getCurrentPlayer();

        AbstractAdGame adGame = adGameManager.get((Long) request.getSession().getAttribute(ID_ADGAME));
        Integer score = (Integer) request.getSession().getAttribute(USER_SCORE);

        //faire avancer le token
        int nbGo = 1;

        GooseToken token =  playerDao.getPlayerGooseToken(player.getId(), (Long) request.getSession().getAttribute(GOOSE_LEVEL));
        GooseCase gooseCase = token.getGooseCase();
        GooseLevel level = gooseCase.getLevel();

        if (gooseCase instanceof EndLevelGooseCase) {
            /*level = gooseGameManager.getNextLevel(level);
            token.setGooseCase(level.getStartCase());*/
        }else if (gooseCase instanceof JailGooseCase && nbGo!=6) {
            //do fait rien car il est en prison
            GooseCase startCase = (GooseCase) request.getSession().getAttribute(PLAYER_TOKEN);
            if(gooseCase.getNumber().equals(startCase.getNumber())){
               return gooseCase;
            }
        }


        if(nbGo>0){

            Integer endCase = level.getEndCase().getNumber();

            GooseCase byNumber = null;
            if(gooseCase.getNumber()+nbGo >endCase){
                byNumber = gooseGameManager.getCaseByNumber((2*endCase)-gooseCase.getNumber()-nbGo,level);
            }else{
                byNumber = gooseGameManager.getCaseByNumber(gooseCase.getNumber()+nbGo,level);
            }

            token.setGooseCase(byNumber);
            gooseTokenDao.save(token);
            playerDao.save(player);
            return byNumber;
        }

        return gooseCase;
    }

    public void emptyGameSession(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.removeAttribute(LIMITED_TIME);
        session.removeAttribute(PLAYER_GOOSE_GAME);
        session.removeAttribute(USER_ANSWER);
        session.removeAttribute(CORRECT_ANSWER);
        session.removeAttribute(USER_SCORE);
        session.removeAttribute(NB_ERRORS);
        session.removeAttribute(ADS_VIDEO);
        session.removeAttribute(GAME_RESULT);
        session.removeAttribute(PLAYER_TOKEN);
        session.removeAttribute(GOOSE_LEVEL);
        session.removeAttribute(GAME_END_TIME);
        session.removeAttribute(ID_ADGAME);
    }

    @Transactional
    public ResponseAdGameBean noUserResponse(HttpServletRequest request, Integer index) throws Exception {
        ResponseAdGameBean res = new ResponseAdGameBean();
        HttpSession session = request.getSession();
        Long end = (Long) session.getAttribute(GAME_END_TIME);
        Boolean limited = (Boolean) session.getAttribute(LIMITED_TIME);

        Player currentPlayer = playerFacade.getCurrentPlayer();
        AdRule adRule = doStat(request, index, currentPlayer);

        if(limited && end < new Date().getTime()){
            res.setStatus(StatusGame.WinLimitTime);
            LimiteTimeAdGameBean gameResult = computeResultGame(request);
            session.setAttribute(GAME_RESULT, gameResult);
        }else{
            Integer score = (Integer) session.getAttribute(USER_SCORE);
            Map<Integer, Long> answers = (Map<Integer, Long>) session
                    .getAttribute(USER_ANSWER);
            Map<Integer, Long> correctResponse = (Map<Integer, Long>) session.getAttribute(CORRECT_ANSWER);
            Integer nbErrs = (Integer) session.getAttribute(NB_ERRORS);

            answers.put(index, null);
            res.setCorrect(false);
            res.setScore(score);
            nbErrs++;
            session.setAttribute(USER_SCORE, score);
            session.setAttribute(NB_ERRORS, nbErrs);

            Integer maxErr = (Integer) session.getAttribute(MAX_ERRORS);

            if (nbErrs > maxErr) {
                res.setStatus(StatusGame.Lost);
                Url callBack = (Url) request.getSession().getAttribute(CALL_BACK_URL);
                res.setWhereToGo(callBack.getKo());
                Url callSys = (Url) request.getSession().getAttribute(CALL_SYS_URL);
                sendCallBack(callSys.getKo());
                adGameManager.saveResponses((Long) session
                        .getAttribute(ID_ADGAME), score, answers, fr.k2i.adbeback.core.business.game.StatusGame.Lost);
            } else if (index +1 < correctResponse.size()) {
                res.setStatus(StatusGame.Playing);
            } else if(score >0){
                Url callBack = (Url) request.getSession().getAttribute(CALL_BACK_URL);
                res.setWhereToGo(callBack.getOk());
                Url callSys = (Url) request.getSession().getAttribute(CALL_SYS_URL);
                sendCallBack(callSys.getOk());
                res.setStatus(StatusGame.WinLimitTime);
                adGameManager.saveResponses((Long) session
                        .getAttribute(ID_ADGAME), score, answers, fr.k2i.adbeback.core.business.game.StatusGame.Win);
            }else{
                Url callBack = (Url) request.getSession().getAttribute(CALL_BACK_URL);
                res.setWhereToGo(callBack.getKo());
                Url callSys = (Url) request.getSession().getAttribute(CALL_SYS_URL);
                sendCallBack(callSys.getKo());
                res.setStatus(StatusGame.Lost);
                adGameManager.saveResponses((Long) session
                        .getAttribute(ID_ADGAME), score, answers, fr.k2i.adbeback.core.business.game.StatusGame.Lost);
            }

        }

        GooseToken gooseToken =  playerDao.getPlayerGooseToken(currentPlayer.getId(), (Long) session.getAttribute(GOOSE_LEVEL));
        Integer number = gooseToken.getGooseCase().getNumber();
        res.setUserToken(number);

        return res;

    }



    private List<PlayerGooseGame> getGooseGame(HttpServletRequest request)
            throws Exception {
        List<PlayerGooseGame> res = new ArrayList<PlayerGooseGame>();

        Player player = playerFacade.getCurrentPlayer();

        GooseToken gooseToken =  playerDao.getPlayerGooseToken(playerFacade.getCurrentPlayer().getId(), (Long) request.getSession().getAttribute(GOOSE_LEVEL));
        GooseCase gooseCase = gooseToken.getGooseCase();
        Integer number = gooseCase.getNumber();
        GooseLevel level = gooseCase.getLevel();

        List<GooseCase> cases = gooseGameManager.getCases(level, number,  7);
        for (GooseCase c : cases) {
            Integer type = c.ihmValue();
            res.add(new PlayerGooseGame(c.getNumber().equals(number),c.getNumber(),type));
        }

        return res;
    }


    @Transactional
    public List<CagnotteBean> getCagnottes() throws Exception {
        List<GooseLevel> all = gooseLevelDao.getAll();
        List<CagnotteBean> res = new ArrayList<CagnotteBean>();
        for (GooseLevel gooseLevel : all) {
            CagnotteBean cagnotte = new CagnotteBean();
            cagnotte.setLevel(gooseLevel.getLevel().intValue());
            cagnotte.setValue(((MultiGooseLevel)gooseLevel).getValue());
            res.add(cagnotte);
        }
        return res;
    }


    @Transactional
    public LimiteTimeAdGameBean getResultAdGame(HttpServletRequest request) throws Exception {

        LimiteTimeAdGameBean res = (LimiteTimeAdGameBean) request.getSession().getAttribute(GAME_RESULT);

        GooseToken gooseToken =  playerDao.getPlayerGooseToken(playerFacade.getCurrentPlayer().getId(), (Long) request.getSession().getAttribute(GOOSE_LEVEL));

        Player player = playerFacade.getCurrentPlayer();
        res.setGooseGames(getGooseGame(request));
        res.setUserToken(gooseToken.getGooseCase().getNumber());

        res.setScore((Integer) request.getSession().getAttribute(USER_SCORE));


        return res;
    }




}


class Filename {
    private String fullPath;
    private char pathSeparator, extensionSeparator;

    public Filename(String str) {
        fullPath = str;
        pathSeparator = File.pathSeparatorChar;
        extensionSeparator = '.';
    }

    public String extension() {
        int dot = fullPath.lastIndexOf(extensionSeparator);
        return fullPath.substring(dot + 1);
    }

    public String filename() { // gets filename without extension
        int dot = fullPath.lastIndexOf(extensionSeparator);
        int sep = fullPath.lastIndexOf(pathSeparator);
        return fullPath.substring(sep + 1, dot);
    }

    public String path() {
        int sep = fullPath.lastIndexOf(pathSeparator);
        return fullPath.substring(0, sep);
    }
}