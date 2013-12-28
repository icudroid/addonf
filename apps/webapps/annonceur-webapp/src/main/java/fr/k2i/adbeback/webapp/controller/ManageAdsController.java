package fr.k2i.adbeback.webapp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.k2i.adbeback.core.business.ad.AdType;
import fr.k2i.adbeback.core.business.country.Country;
import fr.k2i.adbeback.core.business.player.Sex;
import fr.k2i.adbeback.webapp.bean.AdBean;
import fr.k2i.adbeback.webapp.bean.CampaignCommand;
import fr.k2i.adbeback.webapp.facade.BrandServiceFacade;
import fr.k2i.adbeback.webapp.facade.UserFacade;
import fr.k2i.adbeback.webapp.validator.CampaignCommandValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpRequest;
import org.springframework.http.converter.json.Jackson2ObjectMapperFactoryBean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;
import org.springframework.web.servlet.view.json.MappingJacksonJsonView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: dev
 * Date: 23/12/13
 * Time: 12:01
 * To change this template use File | Settings | File Templates.
 */
@Controller
public class ManageAdsController {

    @ModelAttribute("values")
    public Map<String,Object> model(){
        Map<String,Object> model = new HashMap<String, Object>();

        List<String> adTypes = new ArrayList<String>();
        for (AdType type : AdType.values()) {
            adTypes.add(type.name());
        }
        model.put("adTypes",adTypes);

        return model;
    }

    @Autowired
    private BrandServiceFacade brandServiceFacade;


    @Autowired
    private CampaignCommandValidator campaignCommandValidator;


    @RequestMapping(value = IMetaDataController.Path.DASHBOARD_ADS)
    public String showCurrentAds(){
        return IMetaDataController.View.DASHBOARD_ADS;
    }


    @RequestMapping( IMetaDataController.Path.MANAGE_ADS_PARTIALS)
    public String partials(@PathVariable String html){
        return "manage/ads/partials/"+html;
    }

    @RequestMapping( IMetaDataController.Path.MANAGE_ADS_PARTIALS_ACTION)
    public String partials(@PathVariable String action,@PathVariable String html){
        return "manage/ads/partials/"+action+"/"+html;
    }

    @RequestMapping(IMetaDataController.Path.GET_ALL_ADS)
    public @ResponseBody Page<AdBean> getAll(Pageable pageable){
        return brandServiceFacade.getAllForCurrentUser(pageable);
    }

    @RequestMapping(IMetaDataController.Path.CREATE_CAMPAIGN)
    public @ResponseBody
    CampaignCommand create(){
        return brandServiceFacade.createCampaign();
    }

    @RequestMapping(IMetaDataController.Path.SAVE_STEP)
    public @ResponseBody
    ModelAndView save( @RequestBody MultipartFile file,@PathVariable Integer step,BindingResult bindingResults, ModelMap model,HttpServletRequest request, HttpServletResponse response){
        ObjectMapper mapper = new ObjectMapper();
        ModelAndView view = new ModelAndView();
        view.setView(new MappingJackson2JsonView());
        try {
            CampaignCommand campaignCommand = mapper.readValue(request.getParameter("command"), CampaignCommand.class);

            switch (step){
                case 1:
                    //validate information
                    campaignCommandValidator.validate(campaignCommand.getInformation(),bindingResults);
                    Map<String,String> errors = new HashMap<String, String>();
                    if(bindingResults.hasErrors()){
                        for (ObjectError objectError : bindingResults.getAllErrors()) {
                            errors.put(objectError.getObjectName(),objectError.getCode());
                        }
                        view.addObject("errors",errors);
                    }else{

                    }
                    break;
                case 2:
                    //validate product

                    break;
                case 3:
                    //validate rules

                    break;
                case 4:
                    //validates services

                    break;

            }



        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        return view;
    }




}
