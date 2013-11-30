package fr.k2i.adbeback.webapp.facade;

import fr.k2i.adbeback.core.business.game.*;
import fr.k2i.adbeback.core.business.goosegame.*;
import fr.k2i.adbeback.core.business.media.Media;
import fr.k2i.adbeback.core.business.media.Music;
import fr.k2i.adbeback.core.business.partener.Reduction;
import fr.k2i.adbeback.core.business.player.Player;
import fr.k2i.adbeback.dao.*;
import fr.k2i.adbeback.service.AdGameManager;
import fr.k2i.adbeback.service.GooseGameManager;
import fr.k2i.adbeback.webapp.bean.*;
import fr.k2i.adbeback.webapp.bean.StatusGame;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

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
    public static final String GOOSE_LEVEL = "gooseLevel";

    public static final String GAME_END_TIME = "endTime";
    public static final String GAME_RESULT = "result";
    public static final String PLAYER_GOOSE_GAME = "gooseGameCases";
    public static final String PLAYER_TOKEN = "token";

    public static final String CAN_BE_DOWNLOAD = "donwload";
    private static final String CART = "cart";

    @Autowired
    private GooseGameDao gooseGameDao;

    @Autowired
    private AdGameManager adGameManager;

    @Autowired
    private PlayerFacade playerFacade;

    @Autowired
    private GooseGameManager gooseGameManager;

    @Autowired
    private PlayerDao playerDao;

    @Autowired
    private GooseLevelDao gooseLevelDao;

    @Autowired
    private GooseTokenDao gooseTokenDao;

    @Value("${addonf.tmp.location:/tmp/}")
    private String tmpPath;


    @Value("${addonf.music.location:/musics/}")
    private String musicPath;

    @Value("${addonf.video.location:/movies/}")
    private String videoPath;



    @Transactional
    public AdGameBean createAdGame(Long idPlayer,Long gooseLevel,HttpServletRequest request) throws Exception {

        CartBean cart = (CartBean) request.getSession().getAttribute(CART);

        AbstractAdGame generateAdGame = adGameManager.generate(idPlayer,gooseLevel);
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
        //res.setTimeLimite((long) (generateAdGame.getMinScore() * 20));
        res.setTimeLimite(Long.valueOf(30));
        res.setTotalAds(choises.size());


        Player player = playerFacade.getCurrentPlayer();

        GooseToken gooseToken =  playerDao.getPlayerGooseToken(idPlayer, gooseLevel);
        GooseLevel level = gooseLevelDao.get(gooseLevel);
        if(gooseToken==null){

            gooseToken = new GooseToken();
            gooseToken.setGooseCase(level.getStartCase());
            player.addGooseToken(gooseToken);
        }else if(!level.isMultiple()){
            gooseToken.setGooseCase(level.getStartCase());
        }

        res.setMultiple(level.isMultiple());

        GooseCase gooseCase = gooseToken.getGooseCase();
        Integer number = gooseCase.getNumber();

        List<PlayerGooseGame> pgg = new ArrayList<PlayerGooseGame>();
        List<GooseCase> cases = gooseGameManager.getCases(level, number,  7);
        for (GooseCase c : cases) {
            Integer type = c.ihmValue();
            pgg.add(new PlayerGooseGame(c.getNumber().equals(number), c.getNumber(), type));
        }

        res.setGooseGames(pgg);
        res.setUserToken(gooseCase.getNumber());

        Map<Integer, Long> answers = new HashMap<Integer, Long>();
        HttpSession session = request.getSession();
        session.setAttribute(PLAYER_GOOSE_GAME, pgg);
        session.setAttribute(USER_ANSWER, answers);
        session.setAttribute(CORRECT_ANSWER, correctResponse);
        session.setAttribute(USER_SCORE, 0);
        session.setAttribute(NB_ERRORS, 0);
        session.setAttribute(ID_ADGAME, generateAdGame.getId());
        session.setAttribute(ADS_VIDEO, adsVideo);
        session.setAttribute(GAME_RESULT, null);
        session.setAttribute(PLAYER_TOKEN, gooseCase);
        session.setAttribute(GOOSE_LEVEL, gooseLevel);
        session.setAttribute(GAME_END_TIME, new Date().getTime()+(res.getTimeLimite())*1000);

        return res;
    }

    @Transactional
    public ResponseAdGameBean userResponse(HttpServletRequest request, Integer index, Long responseId) throws Exception {
        ResponseAdGameBean res = new ResponseAdGameBean();

        Long end = (Long) request.getSession().getAttribute(GAME_END_TIME);
        if(end < new Date().getTime()){
            res.setStatus(StatusGame.WinLimitTime);
            LimiteTimeAdGameBean gameResult = computeResultGame(request);
            request.getSession().setAttribute(GAME_RESULT,gameResult);
        }else{

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

                goHeadToken(request);

            } else {
                res.setCorrect(false);
                res.setScore(score);
                answers.put(index, -1L);
                nbErrs++;
                request.getSession().setAttribute(NB_ERRORS, nbErrs);
/*            if (nbErrs > 6) {
                res.setStatus(StatusGame.Lost);
                emptyGameSession(request);
                adGameManager.saveResponses((Long) request.getSession().getAttribute(ID_ADGAME), score, answers);
                return res;
            }*/
            }

            if (index < correctResponse.size()-1) {
                res.setStatus(StatusGame.Playing);
            } else {
                res.setStatus(StatusGame.WinLimitTime);
                LimiteTimeAdGameBean gameResult = computeResultGame(request);
                request.getSession().setAttribute(GAME_RESULT,gameResult);
                adGameManager.saveResponses((Long) request.getSession().getAttribute(ID_ADGAME), score, answers);
            }
        }


        GooseToken gooseToken =  playerDao.getPlayerGooseToken(playerFacade.getCurrentPlayer().getId(), (Long) request.getSession().getAttribute(GOOSE_LEVEL));
        Integer number = gooseToken.getGooseCase().getNumber();
        res.setUserToken(number);

        return res;

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
            StringBuilder sb = new StringBuilder();
            sb.append("Bravo vous venez de remporté la cagnotte d'une valeur de ");
            sb.append(level.getValue());
            sb.append(" euros");
            gooseGameManager.resetLevelValue(level);
            GooseWin win = new GooseWin();
            win.setGooseLevel(level);
            win.setValue(level.getValue());
            win.setWindate(new Date());
            win.setPlayer(player);
            player.getWins().add(win);
            playerDao.savePlayer(player);
            gameResult.setMessage(sb.toString());
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
                sb.append(reduction.getPartener().getName());
            }else{
                sb.append(reduction.getPercentageValue());
                sb.append(" euros, chez ");
                sb.append(reduction.getPartener().getName());
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
    private void goHeadToken(HttpServletRequest request) throws Exception {

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
               return;
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

            if (byNumber instanceof WinGooseCase) {
                request.getSession().setAttribute(CAN_BE_DOWNLOAD,true);
            }

            token.setGooseCase(byNumber);
            gooseTokenDao.save(token);
            playerDao.save(player);
        }

    }

    private void emptyGameSession(HttpServletRequest request) {
        //To change body of created methods use File | Settings | File Templates.
    }

    @Transactional
    public ResponseAdGameBean noUserResponse(HttpServletRequest request, Integer index) throws Exception {
        ResponseAdGameBean res = new ResponseAdGameBean();

        Long end = (Long) request.getSession().getAttribute(GAME_END_TIME);
        if(end < new Date().getTime()){
            res.setStatus(StatusGame.WinLimitTime);
            LimiteTimeAdGameBean gameResult = computeResultGame(request);
            request.getSession().setAttribute(GAME_RESULT,gameResult);
        }else{
            Integer score = (Integer) request.getSession().getAttribute(USER_SCORE);
            score++;
            res.setCorrect(true);
            goHeadToken(request);
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
                res.setStatus(StatusGame.Lost);
                emptyGameSession(request);
                adGameManager.saveResponses((Long) request.getSession()
                        .getAttribute(ID_ADGAME), score, answers);
            } else if (index +1 < correctResponse.size()) {
                res.setStatus(StatusGame.Playing);
            } else if(score >0){
                res.setStatus(StatusGame.WinLimitTime);
                adGameManager.saveResponses((Long) request.getSession()
                        .getAttribute(ID_ADGAME), score, answers);
            }else{
                res.setStatus(StatusGame.Lost);
                emptyGameSession(request);
                adGameManager.saveResponses((Long) request.getSession()
                        .getAttribute(ID_ADGAME), score, answers);
            }

        }

        GooseToken gooseToken =  playerDao.getPlayerGooseToken(playerFacade.getCurrentPlayer().getId(), (Long) request.getSession().getAttribute(GOOSE_LEVEL));
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
            cagnotte.setValue(gooseLevel.getValue());
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

    public void getMedias(Long idAdGame, HttpServletResponse response) throws IOException {
        AbstractAdGame abstractAdGame = adGameManager.get(idAdGame);
        if (abstractAdGame instanceof AdGameMedia) {
            AdGameMedia adGame = (AdGameMedia) abstractAdGame;
            List<Media> medias = adGame.getMedias();

            if(medias.size()>1){
                // mettre dans un zip
                String zipFileName = tmpPath+idAdGame+".zip";
                ZipFile zipFile;
                try {
                    zipFile = new ZipFile(zipFileName);
                    ArrayList<File> filesToAdd = new ArrayList<File> ();

                    for (Media media : medias) {
                        if (media instanceof Music) {
                            filesToAdd.add( new File(musicPath+media.getFile()));
                        }else{
                            filesToAdd.add( new File(videoPath+media.getFile()));
                        }
                    }

                    ZipParameters parameters = new ZipParameters();
/*
                    parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE); // set compression method to store compression
                    parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);
                    parameters.setEncryptFiles(true);
                    parameters.setEncryptionMethod(Zip4jConstants.ENC_METHOD_AES);
                    parameters.setAesKeyStrength(Zip4jConstants.AES_STRENGTH_256);
                    parameters.setPassword(password);
*/
                    zipFile.createZipFile(filesToAdd, parameters);
                } catch (ZipException e) {
                    e.printStackTrace();
                }

                java.io.File file = new java.io.File(zipFileName);

                response.setContentType("application/zip");
                response.setContentLength((int) file.length());
                ServletOutputStream outputStream = response.getOutputStream();
                FileInputStream fileInputStream = new FileInputStream(file);
                int read =0;
                byte []b = new byte[1024];
                while((read = fileInputStream.read(b, 0, 1024))>0){
                    outputStream.write(b, 0, read);
                    b = new byte[1024];
                }
                fileInputStream.close();

                file.delete();

            }else if(medias.size()==1){

                Media media = medias.get(0);

                java.io.File file = null;

                if (media instanceof Music) {
                    file =  new File(musicPath+media.getFile());
                }else{
                    file =  new File(videoPath+media.getFile());
                }

                response.setContentLength((int) file.length());
                ServletOutputStream outputStream = response.getOutputStream();
                FileInputStream fileInputStream = new FileInputStream(file);
                int read =0;
                byte []b = new byte[1024];
                while((read = fileInputStream.read(b, 0, 1024))>0){
                    outputStream.write(b, 0, read);
                    b = new byte[1024];
                }
                fileInputStream.close();

                file.delete();
            }

        }
    }
}
