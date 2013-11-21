package fr.k2i.adbeback.webapp.controller.ajax;

import fr.k2i.adbeback.webapp.bean.AdGameBean;
import fr.k2i.adbeback.webapp.bean.LimiteTimeAdGameBean;
import fr.k2i.adbeback.webapp.bean.PlayerGooseGame;
import fr.k2i.adbeback.webapp.bean.ResponseAdGameBean;
import fr.k2i.adbeback.webapp.facade.AdGameFacade;
import fr.k2i.adbeback.webapp.facade.PlayerFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.util.List;
import java.util.Map;

import static fr.k2i.adbeback.webapp.facade.AdGameFacade.*;

/**
 * Created with IntelliJ IDEA.
 * User: dimitri
 * Date: 08/11/13
 * Time: 17:24
 * To change this template use File | Settings | File Templates.
 */

@Controller
public class AdGameWebservice {

    @Autowired
    private AdGameFacade adGameFacade;

    @Autowired
    private PlayerFacade playerFacade;

    @Value("${addonf.ads.location:/videos/}")
    private String pathAds;

    @Value("${addonf.logo.location:/logos/}")
    private String pathLogo;


    @RequestMapping(value = "/dln", method = RequestMethod.GET)
    public @ResponseBody
    void dnl(HttpServletRequest request, HttpServletResponse response) throws Exception {
        if(true == ((Boolean)request.getSession().getAttribute(CAN_BE_DOWNLOAD))){

        }
    }


    @RequestMapping(value = "/rest/createGame/{level}", method = RequestMethod.GET)
    public @ResponseBody
    AdGameBean createGame(@PathVariable Long level,HttpServletRequest request, HttpServletResponse response) throws Exception {
        return adGameFacade.createAdGame(playerFacade.getCurrentPlayer().getId(),level, request);
    }


    @RequestMapping(value = "/logo/{filename}.{ext}", method = RequestMethod.GET)
    public void streamLogoAd(@PathVariable String filename,@PathVariable String ext,HttpServletRequest request, HttpServletResponse response) throws Exception {
        ServletOutputStream outputStream = response.getOutputStream();

        File file = new File(pathLogo+filename+"."+ext);

        FileInputStream fileInputStream = new FileInputStream(file);
        int read =0;
        byte []b = new byte[1024];
        while((read = fileInputStream.read(b, 0, 1024))>0){
            outputStream.write(b, 0, read);
            b = new byte[1024];
        }
        fileInputStream.close();

    }



    @RequestMapping(value = "/video/{index}", method = RequestMethod.GET)
    public void streamVideoAd(@PathVariable int index,HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<String> videos = (List<String>) request.getSession().getAttribute(ADS_VIDEO);
        ServletOutputStream outputStream = response.getOutputStream();

        File file = new File(pathAds+videos.get(index));
        FileInputStream fileInputStream = new FileInputStream(file);
        int read =0;
        byte []b = new byte[1024];
        while((read = fileInputStream.read(b, 0, 1024))>0){
            outputStream.write(b, 0, read);
            b = new byte[1024];
        }
        fileInputStream.close();

    }


    @RequestMapping(value = "/rest/play/{index}/{responseId}", method = RequestMethod.GET)
    public @ResponseBody
    ResponseAdGameBean play(@PathVariable Integer index, @PathVariable Long responseId,
                            HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        return adGameFacade.userResponse(request,index,responseId);

    }

    @RequestMapping(value = "/rest/noresponse/{index}", method = RequestMethod.GET)
    public @ResponseBody
    ResponseAdGameBean noResponse(@PathVariable Integer index,
                                  HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        return adGameFacade.noUserResponse(request,index);

    }



    @RequestMapping(value = "/rest/getResultAdGame", method = RequestMethod.GET)
    public @ResponseBody
    LimiteTimeAdGameBean getResultAdGame(HttpServletRequest request) throws Exception {
        return adGameFacade.getResultAdGame(request);
    }




}
