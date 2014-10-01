package fr.k2i.adbeback.webapp.controller.ajax;

import com.google.common.collect.Lists;
import fr.k2i.adbeback.webapp.bean.AdGameBean;
import fr.k2i.adbeback.webapp.bean.LimiteTimeAdGameBean;
import fr.k2i.adbeback.webapp.bean.ResponseAdGameBean;
import fr.k2i.adbeback.webapp.bean.configure.PaymentConfigure;
import fr.k2i.adbeback.webapp.facade.AdGameFacade;
import fr.k2i.adbeback.webapp.util.HttpStreaming;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.List;

import static fr.k2i.adbeback.webapp.facade.AdGameFacade.ADS_VIDEO;

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

    @Value("${addonf.ads.location:/videos/}")
    private String pathAds;

    @Value("${addonf.logo.location:/logos/}")
    private String pathLogo;



    @RequestMapping( "/partials/{html}.html")
    public String partials(@PathVariable String html){
        return "manage/gooseGame/partials/"+html;
    }



    @RequestMapping(value = "/rest/createGame", method = RequestMethod.GET)
    public @ResponseBody
    AdGameBean createGame(@ModelAttribute PaymentConfigure configure, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return adGameFacade.createMicroPurchaseAdGame(configure, request);
    }


    @RequestMapping(value = "/logo/{filename}", method = RequestMethod.GET)
    public @ResponseBody
    ResponseEntity<byte[]> streamLogoAdNoExt(@PathVariable String filename, HttpServletRequest request, HttpServletResponse response) throws Exception {
        File file = new File(pathLogo+filename);
        return  HttpStreaming.streaming(request, file);
    }

    @RequestMapping(value = "/logo/{filename}.{ext}", method = RequestMethod.GET)
    public @ResponseBody
    ResponseEntity<byte[]> streamLogoAd(@PathVariable String filename, @PathVariable String ext, HttpServletRequest request, HttpServletResponse response) throws Exception {
        File file = new File(pathLogo+filename+"."+ext);
        return  HttpStreaming.streaming(request, file);
    }



    @RequestMapping(value = "/video/{index}", method = RequestMethod.GET)
    public @ResponseBody
    ResponseEntity<byte[]> streamVideoAd(@PathVariable int index, HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<String> videos = (List<String>) request.getSession().getAttribute(ADS_VIDEO);
        File file = new File(pathAds+videos.get(index));
        return  HttpStreaming.streaming(request, file);
    }



    @RequestMapping(value = "/video/{index}/{type}", method = RequestMethod.GET)
    public @ResponseBody
    ResponseEntity<byte[]> streamVideoAdByType(@PathVariable int index, @PathVariable String type, HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<String> videos = (List<String>) request.getSession().getAttribute(ADS_VIDEO);
        File file = new File(pathAds+videos.get(index)+"."+type);
        return HttpStreaming.streaming(request,file);
    }


    @RequestMapping(value = "/rest/play/{index}/{responseId}", method = RequestMethod.GET)
    public @ResponseBody
    ResponseAdGameBean play(@PathVariable Integer index, @PathVariable Long responseId,
                            HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        return adGameFacade.userResponse(request,index, Lists.newArrayList(responseId));

    }

    @RequestMapping(value = "/rest/play/{index}", method = RequestMethod.GET)
    public @ResponseBody
    ResponseAdGameBean playMultiReponse(@PathVariable Integer index, @RequestParam List<Long> responseId,
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
