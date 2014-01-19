package fr.k2i.adbeback.webapp.controller;

import fr.k2i.adbeback.dao.IAdGameDao;
import fr.k2i.adbeback.dao.jpa.GenreRepository;
import fr.k2i.adbeback.webapp.bean.myaccount.ChangePwdBean;
import fr.k2i.adbeback.webapp.bean.search.SearchCommand;
import fr.k2i.adbeback.webapp.facade.AdGameFacade;
import fr.k2i.adbeback.webapp.facade.PlayerFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: dev
 * Date: 18/01/14
 * Time: 21:21
 * To change this template use File | Settings | File Templates.
 */
@Controller
public class MyAccountController {

    @Autowired
    private IAdGameDao adGameDao;

    @Autowired
    private PlayerFacade playerFacade;

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private AdGameFacade adGameFacade;


    @Autowired
    private Validator validator;

    @RequestMapping(value = "/redownload")
    public String redownload(@ModelAttribute("search")SearchCommand searchCommand, Pageable pageable, Map<String, Object> model){
        model.put("musics", adGameDao.getDownloadedMusic(playerFacade.getCurrentPlayer(),searchCommand.getGenreId(), searchCommand.getReq(),pageable));
        model.put("categories",genreRepository.findAll());
        return "account/redownload";
    }


    @RequestMapping(value = "/redownloadMusic", method = RequestMethod.GET)
    public @ResponseBody
    void redownloadMusic(@RequestParam Long musicId,HttpServletRequest request, HttpServletResponse response) throws Exception {
        adGameFacade.getMedia(musicId,playerFacade.getCurrentPlayer(),response);
    }


    @RequestMapping(value = "/myaccount/changepwd", method = RequestMethod.GET)
    public String changePwd(@ModelAttribute("changePwdBean")ChangePwdBean changePwdBean,BindingResult bindingResult,Map<String, Object> model) throws Exception {
        model.put("changed",false);
        return "account/changePwd";
    }


    @RequestMapping(value = "/myaccount/changepwd", method = RequestMethod.POST)
    public String changePwdSubmit(@ModelAttribute("changePwdBean")ChangePwdBean changePwdBean,BindingResult bindingResult,Map<String, Object> model,HttpServletRequest request) throws Exception {
        validator.validate(changePwdBean,bindingResult);
        playerFacade.validateChangePwdBean(changePwdBean, bindingResult, request.getLocale());
        if(!bindingResult.hasErrors()){
            playerFacade.changePasswd(changePwdBean.getNewPassword());
            model.put("changed",true);
            return "account/changePwd";
        }else{
            model.put("changed",false);
            return "account/changePwd";
        }
    }


}
