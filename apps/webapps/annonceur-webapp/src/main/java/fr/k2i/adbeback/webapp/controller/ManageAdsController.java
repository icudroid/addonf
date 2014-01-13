package fr.k2i.adbeback.webapp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.k2i.adbeback.core.business.ad.AdType;
import fr.k2i.adbeback.webapp.bean.AdBean;
import fr.k2i.adbeback.webapp.bean.CampaignCommand;
import fr.k2i.adbeback.webapp.bean.FileCommand;
import fr.k2i.adbeback.webapp.facade.BrandServiceFacade;
import fr.k2i.adbeback.webapp.validator.CampaignCommandValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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


    @RequestMapping(value = IMetaDataController.Path.LIST_CAMPAIGNS)
    public String showCampaignAds(ModelMap model) throws Exception {
        List<AdBean> ads = brandServiceFacade.getAdsForConnectedUser();
        model.addAttribute("campaigns",ads);
        return IMetaDataController.View.LIST_CAMPAIGNS;
    }


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
    public @ResponseBody Page<AdBean> getAll(Pageable pageable) throws Exception {
        return brandServiceFacade.getAllForConnectedUser(pageable);
    }

    @RequestMapping(IMetaDataController.Path.CREATE_CAMPAIGN)
    public @ResponseBody
    CampaignCommand create(){
        return brandServiceFacade.createCampaign();
    }

    @RequestMapping(IMetaDataController.Path.SAVE_STEP)
    public @ResponseBody
    ModelAndView save( @RequestBody(required = false) MultipartFile file,@PathVariable Integer step, ModelMap model,HttpServletRequest request, HttpServletResponse response) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        CampaignCommand campaignCommand = mapper.readValue(request.getParameter("command"), CampaignCommand.class);
        return saveStep(file, step, campaignCommand,request);
    }

    private ModelAndView saveStep(MultipartFile file, Integer step, CampaignCommand campaignCommand, HttpServletRequest request) throws IOException {
        ModelAndView view = new ModelAndView();
        view.setView(new MappingJackson2JsonView());

            BindingResult bindingResults = new DirectFieldBindingResult(CampaignCommand.class,"campaignCommand");
            Map<String,String> errors = new HashMap<String, String>();
            switch (step){
                case 1:
                    campaignCommandValidator.validate(campaignCommand.getInformation(),bindingResults);
                    if(file!=null && file.isEmpty()){
                        errors.put("","required");
                    }else{
                        FileCommand ad = new FileCommand(file);
                        request.getSession().setAttribute("ad",file);
                    }
                    //Todo:
                    break;
                case 2:
                    campaignCommandValidator.validate(campaignCommand.getProduct(),bindingResults);
                    if(file!=null && file.isEmpty()){
                        errors.put("","required");
                    }else{
                        FileCommand ad = new FileCommand(file);
                        request.getSession().setAttribute("product",file);
                    }
                    //Todo:
                    break;
                case 3:
                    campaignCommandValidator.validate(campaignCommand.getRules(),bindingResults);
                    break;
                case 4:
                    campaignCommandValidator.validate(campaignCommand.getAdServices(),bindingResults);
                    break;
            }

            if(bindingResults.hasErrors()){
                for (ObjectError objectError : bindingResults.getAllErrors()) {
                    errors.put(objectError.getObjectName(),objectError.getCode());
                }
                view.addObject("errors",errors);
            }else if(step.equals(4)){
                //SAVE
                //Todo:
            }

        return view;
    }


    @RequestMapping(value = IMetaDataController.Path.SAVE_STEP_NO_FILE, method = RequestMethod.POST)
    public @ResponseBody
    ModelAndView saveNoFile( @PathVariable Integer step, ModelMap model,HttpServletRequest request, HttpServletResponse response) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        CampaignCommand campaignCommand = mapper.readValue(request.getInputStream(), CampaignCommand.class);
        return saveStep(null, step, campaignCommand, request);
    }




}
