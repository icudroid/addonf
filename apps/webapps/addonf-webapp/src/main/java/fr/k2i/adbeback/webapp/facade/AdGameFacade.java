package fr.k2i.adbeback.webapp.facade;

import fr.k2i.adbeback.bean.ResponseUser;
import fr.k2i.adbeback.core.business.ad.*;
import fr.k2i.adbeback.core.business.ad.rule.AdRule;
import fr.k2i.adbeback.core.business.ad.rule.AdService;
import fr.k2i.adbeback.core.business.ad.rule.MultiResponseRule;
import fr.k2i.adbeback.core.business.game.*;
import fr.k2i.adbeback.core.business.goosegame.*;
import fr.k2i.adbeback.core.business.partener.Reduction;
import fr.k2i.adbeback.core.business.player.*;
import fr.k2i.adbeback.core.business.user.BidCategoryMedia;
import fr.k2i.adbeback.core.business.user.Media;
import fr.k2i.adbeback.dao.*;
import fr.k2i.adbeback.service.AdGameManager;
import fr.k2i.adbeback.service.GooseGameManager;
import fr.k2i.adbeback.webapp.bean.*;
import fr.k2i.adbeback.webapp.bean.StatusGame;
import fr.k2i.adbeback.webapp.bean.configure.PaymentConfigure;
import fr.k2i.adbeback.webapp.bean.configure.information.*;
import fr.k2i.adbeback.webapp.bean.configure.url.Url;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
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

    public static final String CONFIGURE = "configurePayment";

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


    @Autowired
    IMediaDao mediaDao;




    @Transactional
    public AdGameBean createAdGame(PaymentConfigure configure, HttpServletRequest request) throws Exception {

        HttpSession session = request.getSession();

        //0 : verifier
        Media media = mediaDao.findByExtId(configure.getIdPartner());

        if(media ==null){
            return null;
        }else{
            if(mediaDao.existTransaction(configure.getIdPartner(),configure.getIdTransaction())){
                return null;
            }
        }


        //1 : create Anonym User
        Player player = getAnonymousPlayer(configure);
        //log user
        WebUser webPlayer = new WebUser(player);
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(webPlayer, webPlayer.getPassword(), webPlayer.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);

        //get all valid ad for player
        List<Ad> ads = null;
        if(configure.getSelfAd()){
            ads = adDao.getAllValidForAndProvidedBy(player,media);
        }else{
            ads= adDao.getAllValidFor(player);
        }

        Map<Double,List<Ad>> adsSortedByBid = constructBidsSortedMap(ads);

        //bid system to calculate min ad neeeded
        Map<Ad,Double> winBidAds  = bidSystem(adsSortedByBid,configure);

        //moyen 0.20 euro / pub
        //2 : estimate min score
        /*Double nb = configure.getAmount() / AVERAGE_AP_PRICE;
        Double left = configure.getAmount() % AVERAGE_AP_PRICE;
        Integer minScore =  nb.intValue();

        //one more if exemple 3.75 => 4
        if(left!=0){
            minScore++;
        }*/

        Integer minScore = winBidAds.size();

        //3 : find level for NB ads
        SingleGooseLevel gooseLevel = gooseLevelDao.findForNbAds(minScore);

        int maxErr = gooseLevel.getNbMaxAdByPlay() - gooseLevel.getMinScore();

        //need more add for max Err
        bidSystemNeedMoreAdForErr(winBidAds,adsSortedByBid, maxErr);

        //4 : generate game
        AbstractAdGame generateAdGame = adGameManager.generate(winBidAds,configure.getIdPartner(),configure.getIdTransaction(),player.getId(),gooseLevel);

        //5 : set urlCall in session
        session.setAttribute(CONFIGURE,configure);

        List<String> adsVideo = new ArrayList<String>();
        Map<Integer, List<Long>> correctResponse = new HashMap<Integer,  List<Long>>();
        Map<Integer, AdChoise> choises = generateAdGame.getChoises();

        AdGameBean res = createAdBeans(adsVideo, correctResponse, choises);

        //6 : time limit
        Boolean limitedTime = (gooseLevel.getLimitedTime() !=null)?gooseLevel.getLimitedTime():false ;
        if(limitedTime !=null && limitedTime ==true) {
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

        Map<Integer, ResponseUser> answers = new HashMap<Integer,ResponseUser>();

        res.setLogoMedia(media.getLogo());

        boolean showSplashScreen =  (configure.getShowSplashScreen()!=null)?configure.getShowSplashScreen():false;
        res.setShowSplashScreen(showSplashScreen);

        session.setAttribute(LIMITED_TIME, limitedTime);
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

    private void bidSystemNeedMoreAdForErr(Map<Ad, Double> winBidAds, Map<Double,List<Ad>> adsSortedByBid, int needMoreAds) {
        for (int i = 0; i < needMoreAds; i++) {
            doBid(winBidAds, adsSortedByBid);
        }
    }



    private Map<Ad, Double> bidSystem(Map<Double,List<Ad>> adsSortedByBid, final PaymentConfigure configure) {
        Map<Ad, Double> res = new HashMap<Ad, Double>();
        double amount = configure.getAmount();
        while (amount>0.0){
            amount-=doBid(res, adsSortedByBid);
        }
        return res;
    }


    private Double doBid(Map<Ad, Double> res, Map<Double, List<Ad>> adsSortedByBid) {
        NavigableSet<Double> bids = (NavigableSet<Double>) adsSortedByBid.keySet();

        Double highBid = bids.first();
        List<Ad> adsBidHigh = adsSortedByBid.get(highBid);
        Double winBid = 0.0;
        if(bids.size()>1){
            adsSortedByBid.remove(highBid);
            bids = ( NavigableSet<Double>) adsSortedByBid.keySet();
            Double justBefore = bids.first();
            winBid = justBefore+0.01;
        }else{
            winBid = highBid;
        }





        Ad adWinbid = null;
        if(adsBidHigh.size()>1){
            //take one random
            Random chooseAd = new Random();
            int index = chooseAd.nextInt(adsBidHigh.size());
            adWinbid = adsBidHigh.get(index);
            adsBidHigh.remove(index);
            adsSortedByBid.put(highBid,adsBidHigh);
        }else{
            adWinbid = adsBidHigh.get(0);
        }

        res.put(adWinbid,winBid);
        return winBid;
    }

    private Map<Double, List<Ad>>  constructBidsSortedMap(List<Ad> ads) {

        // 1 - sort by bid asc
        Map<Double,List<Ad>> adsSortedByBid = new TreeMap<Double,List<Ad>>(new Comparator<Double>() {
            @Override
            public int compare(Double bid1, Double bid2) {
                return bid2.compareTo(bid1);
            }
        });

        for (Ad ad : ads) {
            List<BidCategoryMedia> bidCategoryMedias = ad.getBidCategoryMedias();

            for (BidCategoryMedia bidCategoryMedia : bidCategoryMedias) {
                List<Ad> bidValueForAds = adsSortedByBid.get(bidCategoryMedia.getBid());
                if(bidValueForAds == null){
                    bidValueForAds = new ArrayList<Ad>();
                    adsSortedByBid.put(bidCategoryMedia.getBid(),bidValueForAds);
                }
                bidValueForAds.add(ad);
            }

        }

        return adsSortedByBid;
    }

    /**
     *
     * @param adsVideo
     * @param correctResponse
     * @param choises
     * @return
     */
    private AdGameBean createAdBeans(List<String> adsVideo, Map<Integer, List<Long>> correctResponse, Map<Integer, AdChoise> choises) {

        AdGameBean res = new AdGameBean();
        List<AdBean> game = new ArrayList<AdBean>();

        for (Map.Entry<Integer, AdChoise> entry : choises.entrySet()) {
            Integer num = entry.getKey();
            AdChoise adChoise = entry.getValue();
            AdBean adBean = new AdBean();
            List<PossibilityBean> possibilities = new ArrayList<PossibilityBean>();


            AdService generatedBy = adChoise.getGeneratedBy();

            if (generatedBy instanceof MultiResponseRule) {
                MultiResponseRule rule = (MultiResponseRule) generatedBy;
                adBean.setAddonText(rule.getAddonText());
                adBean.setBtnValidText(rule.getBtnValidText());
                adBean.setMultiResponse(true);
            }else{
                adBean.setMultiResponse(false);
            }


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


            List<Possibility> corrects = adChoise.getCorrects();
            Ad ad = corrects.get(0).getAd();
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

            List<Long> idCorrects = new ArrayList<Long>();

            for (Possibility correct : corrects) {
                idCorrects.add(correct.getId());
            }
            correctResponse.put(num, idCorrects);
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
        player.setEmail(UUID.randomUUID().toString());
        player.setPassword(UUID.randomUUID().toString());
        player.setUsername(UUID.randomUUID().toString());

        Information informations = configure.getInformations();
        AgeInformation ageInf = informations.getAge();
        if(ageInf!=null)player.setAgeGroup(AgeGroup.fromAge(ageInf.getOld()));

        CityInformation cityInf = informations.getCity();
        if(cityInf!=null){
            Address address = new Address();
            address.setCity(cityDao.findByZipcodeAndCityAndCountry_Code(cityInf.getZipCode(),cityInf.getCityName(),cityInf.getCountryCode()));
            address.setCountry(null);
            player.setAddress(address);
        }

        if(cityInf==null){
            CountryInformation countryInf = informations.getCountry();
            if(countryInf!=null){
                Address addressCountry = new Address();
                addressCountry.setCountry(countryDao.findByCode(countryInf.getCountryCode()));
                addressCountry.setCity(null);
                player.setAddress(addressCountry);
            }
        }

        SexInformation sexInf = informations.getSex();
        if(sexInf!=null)player.setSex(sexInf.getSex());

        return playerDao.save(player);
    }

    @Transactional
    public ResponseAdGameBean userResponse(HttpServletRequest request, Integer index, List<Long> responseId) throws Exception {
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

            Map<Integer, List<Long>> correctResponse = (Map<Integer, List<Long>>) session.getAttribute(CORRECT_ANSWER);
            Integer score = (Integer) session.getAttribute(USER_SCORE);
            Map<Integer, ResponseUser> answers = (Map<Integer, ResponseUser>) session.getAttribute(USER_ANSWER);
            List<Long> correctIds = correctResponse.get(index);
            //Long correctId = correctResponse.get(index);

            Integer nbErrs = (Integer) session.getAttribute(NB_ERRORS);

            GooseCase gooseCase = null;

            AdRule adRule = doStat(request, index, currentPlayer);

            //all corrects responses
            boolean isCorrect = true;
            for (Long resp : correctIds) {
                if(!responseId.contains(resp)){
                    isCorrect = false;
                }
            }

            if (answers.get(index) == null && isCorrect) {
                res.setCorrect(true);
                score++;
                res.setScore(score);
                session.setAttribute(USER_SCORE, score);
                answers.put(index, new ResponseUser(true,responseId, (AdService) adRule));

                gooseCase = goHeadToken(request);


                Map<Integer, AdChoise> choises = (Map<Integer, AdChoise>) session.getAttribute(AD_CHOISES);
                AdChoise adChoise = choises.get(index);


                adDao.updateAmountForAd((AdService) adRule,adChoise.getWinBidPrice());


            } else {
                res.setCorrect(false);
                res.setScore(score);
                answers.put(index, new ResponseUser(false,responseId,(AdService) adRule));
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

            if (nbErrs <= maxErr  && index < correctResponse.size()-1  && !(gooseCase instanceof EndLevelGooseCase)) {
                res.setStatus(StatusGame.Playing);
            } else {
                fr.k2i.adbeback.core.business.game.StatusGame statusGame = null;
                PaymentConfigure configure = (PaymentConfigure) session.getAttribute(CONFIGURE);
                res.setWhereToGo(configure.getCallBackUrl());
                res.setIdTransaction(configure.getIdTransaction());
                if(gooseCase instanceof EndLevelGooseCase){
                    res.setStatus(StatusGame.WinLimitTime);
                    statusGame = fr.k2i.adbeback.core.business.game.StatusGame.Win;
                    sendCallBack(configure.getCallSysUrl(), "ok", configure.getIdTransaction());
                }else{
                    res.setStatus(StatusGame.Lost);
                    statusGame = fr.k2i.adbeback.core.business.game.StatusGame.Lost;
                    sendCallBack(configure.getCallSysUrl(), "ko", configure.getIdTransaction());
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

    private void sendCallBack(String url, String status,String idTransaction) throws IOException {
        HttpPost httppost = new HttpPost(url);

        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
        nameValuePairs.add(new BasicNameValuePair("idTransaction", idTransaction));
        nameValuePairs.add(new BasicNameValuePair("status", status));
        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));


        HttpClients.createDefault().execute(httppost);
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
                sb.append(reduction.getMerchant().getName());
            }else{
                sb.append(reduction.getPercentageValue());
                sb.append(" euros, chez ");
                sb.append(reduction.getMerchant().getName());
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
            Map<Integer, ResponseUser> answers = (Map<Integer, ResponseUser>) session
                    .getAttribute(USER_ANSWER);
            Map<Integer, Long> correctResponse = (Map<Integer, Long>) session.getAttribute(CORRECT_ANSWER);
            Integer nbErrs = (Integer) session.getAttribute(NB_ERRORS);

            answers.put(index, new ResponseUser(false,null, (AdService) adRule));
            res.setCorrect(false);
            res.setScore(score);
            nbErrs++;
            session.setAttribute(USER_SCORE, score);
            session.setAttribute(NB_ERRORS, nbErrs);

            Integer maxErr = (Integer) session.getAttribute(MAX_ERRORS);

            PaymentConfigure configure = (PaymentConfigure) session.getAttribute(CONFIGURE);

            if (nbErrs > maxErr) {
                res.setStatus(StatusGame.Lost);
                res.setWhereToGo(configure.getCallBackUrl());
                res.setIdTransaction(configure.getIdTransaction());
                sendCallBack(configure.getCallSysUrl(), "ko",configure.getIdTransaction());
                adGameManager.saveResponses((Long) session
                        .getAttribute(ID_ADGAME), score, answers, fr.k2i.adbeback.core.business.game.StatusGame.Lost);
            } else if (index +1 < correctResponse.size()) {
                res.setStatus(StatusGame.Playing);
            } else if(score >0){
                res.setWhereToGo(configure.getCallBackUrl());
                sendCallBack(configure.getCallSysUrl(), "ok",configure.getIdTransaction());
                res.setIdTransaction(configure.getIdTransaction());
                res.setStatus(StatusGame.WinLimitTime);
                adGameManager.saveResponses((Long) session
                        .getAttribute(ID_ADGAME), score, answers, fr.k2i.adbeback.core.business.game.StatusGame.Win);
            }else{
                res.setWhereToGo(configure.getCallBackUrl());
                sendCallBack(configure.getCallSysUrl(), "ko",configure.getIdTransaction());
                res.setIdTransaction(configure.getIdTransaction());
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