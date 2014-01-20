package fr.k2i.adbeback.webapp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.k2i.adbeback.core.business.ad.AdType;
import fr.k2i.adbeback.core.business.ad.rule.AgeRule;
import fr.k2i.adbeback.core.business.ad.rule.CityRule;
import fr.k2i.adbeback.core.business.ad.rule.CountryRule;
import fr.k2i.adbeback.core.business.ad.rule.SexRule;
import fr.k2i.adbeback.webapp.bean.*;
import fr.k2i.adbeback.webapp.facade.BrandServiceFacade;
import fr.k2i.adbeback.webapp.validator.CampaignCommandValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.*;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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


    @Autowired
    private BrandServiceFacade brandServiceFacade;


    @Autowired
    private CampaignCommandValidator campaignCommandValidator;


    @Autowired
    private MessageSource messageSource;


    @InitBinder
    public void initBinder(WebDataBinder binder,Locale locale) {
        DateFormat dateFormat = new SimpleDateFormat(messageSource.getMessage("date_format",null,locale));
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
    }

    @RequestMapping(value = IMetaDataController.Path.LIST_CAMPAIGNS)
    public String showCampaignAds(ModelMap model) throws Exception {
        List<AdBean> ads = brandServiceFacade.getAdsForConnectedUser();
        model.addAttribute("campaigns",ads);
        return IMetaDataController.View.LIST_CAMPAIGNS;
    }


    @RequestMapping(value = IMetaDataController.Path.CREATE_CAMPAIGN_STEP_1,method = RequestMethod.GET)
    public String step1(Map<String, Object> model,HttpServletRequest request){
        CampaignCommand campaignCommand = (CampaignCommand) request.getSession().getAttribute("campaignCommand");
        if(campaignCommand ==null){
            campaignCommand = new CampaignCommand();
            request.getSession().setAttribute("campaignCommand", campaignCommand);
        }

        model.put("informationCommand",campaignCommand.getInformation());

        return IMetaDataController.View.CREATE_CAMPAIGN_STEP_1;
    }

    @RequestMapping(value = IMetaDataController.Path.CREATE_CAMPAIGN_STEP_1,method = RequestMethod.POST)
    public String step1Submit(@ModelAttribute("informationCommand") InformationCommand informationCommand,BindingResult bindingResults,HttpServletRequest request){
        campaignCommandValidator.validate(informationCommand,bindingResults);
        if(bindingResults.hasErrors()){
            return IMetaDataController.View.CREATE_CAMPAIGN_STEP_1;
        }else{
            CampaignCommand campaignCommand = (CampaignCommand) request.getSession().getAttribute("campaignCommand");
            campaignCommand.setInformation(informationCommand);
            return IMetaDataController.PathUtils.REDIRECT+IMetaDataController.Path.CREATE_CAMPAIGN_STEP_2;
        }
    }


    @RequestMapping(value = IMetaDataController.Path.CREATE_CAMPAIGN_STEP_2,method = RequestMethod.GET)
    public String step2(Map<String, Object> model,HttpServletRequest request){
        CampaignCommand campaignCommand = (CampaignCommand) request.getSession().getAttribute("campaignCommand");
        model.put("adRulesCommand",campaignCommand.getRules());
        return IMetaDataController.View.CREATE_CAMPAIGN_STEP_2;
    }

    @RequestMapping(value = IMetaDataController.Path.CREATE_CAMPAIGN_STEP_2,method = RequestMethod.POST)
    public String step2Submit(@ModelAttribute("adRulesCommand") AdRulesCommand adRulesCommand,BindingResult bindingResults,HttpServletRequest request){
        campaignCommandValidator.validate(adRulesCommand,bindingResults);
        if(bindingResults.hasErrors()){
            return IMetaDataController.View.CREATE_CAMPAIGN_STEP_2;
        }else{
            CampaignCommand campaignCommand = (CampaignCommand) request.getSession().getAttribute("campaignCommand");
            campaignCommand.setRules(adRulesCommand);
            return IMetaDataController.PathUtils.REDIRECT+IMetaDataController.Path.CREATE_CAMPAIGN_STEP_3;
        }
    }


    @RequestMapping(value="/createCampaign/rule", params={"addCountryRule"})
    public ModelAndView addCountryRule(@RequestBody CountryRule countryRule, final BindingResult bindingResult, HttpServletRequest request) {
        ModelAndView view = new ModelAndView();
        view.setView(new MappingJackson2JsonView());
        CampaignCommand campaignCommand = (CampaignCommand) request.getSession().getAttribute("campaignCommand");
        if(campaignCommand.getRules().getCountryRules().contains(countryRule)){
            bindingResult.reject("Exists");
        }

        if(bindingResult.hasErrors()){
            Map<String,String> errors = new HashMap<String, String>();
            for (ObjectError objectError : bindingResult.getAllErrors()) {
                errors.put(objectError.getObjectName(),objectError.getCode());
            }
            view.addObject("errors",errors);
        }else{

            campaignCommand.getRules().getCountryRules().add(countryRule);
        }

        return view;
    }


    @RequestMapping(value="/createCampaign/rule", params={"removeCountryRule"})
    public ModelAndView removeCountryRule(@RequestBody CountryRule countryRule, HttpServletRequest request) {
        ModelAndView view = new ModelAndView();
        view.setView(new MappingJackson2JsonView());

        CampaignCommand campaignCommand = (CampaignCommand) request.getSession().getAttribute("campaignCommand");
        campaignCommand.getRules().getCountryRules().remove(countryRule);

        return view;
    }



    @RequestMapping(value="/createCampaign/rule", params={"addCityRule"})
    public ModelAndView addCityRule(@RequestBody CityRule cityRule, final BindingResult bindingResult, HttpServletRequest request) {
        ModelAndView view = new ModelAndView();
        view.setView(new MappingJackson2JsonView());
        CampaignCommand campaignCommand = (CampaignCommand) request.getSession().getAttribute("campaignCommand");
        if(campaignCommand.getRules().getCityRules().contains(cityRule)){
            bindingResult.reject("Exists");
        }

        if(bindingResult.hasErrors()){
            Map<String,String> errors = new HashMap<String, String>();
            for (ObjectError objectError : bindingResult.getAllErrors()) {
                errors.put(objectError.getObjectName(),objectError.getCode());
            }
            view.addObject("errors",errors);
        }else{

            campaignCommand.getRules().getCityRules().add(cityRule);
        }

        return view;
    }


    @RequestMapping(value="/createCampaign/rule", params={"removeCityRule"})
    public ModelAndView removeCountryRule(@RequestBody CityRule cityRule, HttpServletRequest request) {
        ModelAndView view = new ModelAndView();
        view.setView(new MappingJackson2JsonView());

        CampaignCommand campaignCommand = (CampaignCommand) request.getSession().getAttribute("campaignCommand");
        campaignCommand.getRules().getCityRules().remove(cityRule);

        return view;
    }




    @RequestMapping(value="/createCampaign/rule", params={"setAgeRule"})
    public ModelAndView setAgeRule(@RequestBody AgeRule ageRule, final BindingResult bindingResult, HttpServletRequest request) {
        ModelAndView view = new ModelAndView();
        view.setView(new MappingJackson2JsonView());
        CampaignCommand campaignCommand = (CampaignCommand) request.getSession().getAttribute("campaignCommand");
        if(campaignCommand.getRules().getAgeRule() != null){
            bindingResult.reject("Exists");
        }

        if(bindingResult.hasErrors()){
            Map<String,String> errors = new HashMap<String, String>();
            for (ObjectError objectError : bindingResult.getAllErrors()) {
                errors.put(objectError.getObjectName(),objectError.getCode());
            }
            view.addObject("errors",errors);
        }else{

            campaignCommand.getRules().setAgeRule(ageRule);
        }

        return view;
    }


    @RequestMapping(value="/createCampaign/rule", params={"removeAgeRule"})
    public ModelAndView removeAgeRule( HttpServletRequest request) {
        ModelAndView view = new ModelAndView();
        view.setView(new MappingJackson2JsonView());

        CampaignCommand campaignCommand = (CampaignCommand) request.getSession().getAttribute("campaignCommand");
        campaignCommand.getRules().setAgeRule(null);

        return view;
    }



    @RequestMapping(value="/createCampaign/rule", params={"setSexRule"})
    public ModelAndView setSexRule(@RequestBody SexRule sexRule, final BindingResult bindingResult, HttpServletRequest request) {
        ModelAndView view = new ModelAndView();
        view.setView(new MappingJackson2JsonView());
        CampaignCommand campaignCommand = (CampaignCommand) request.getSession().getAttribute("campaignCommand");
        if(campaignCommand.getRules().getSexRule() != null){
            bindingResult.reject("Exists");
        }

        if(bindingResult.hasErrors()){
            Map<String,String> errors = new HashMap<String, String>();
            for (ObjectError objectError : bindingResult.getAllErrors()) {
                errors.put(objectError.getObjectName(),objectError.getCode());
            }
            view.addObject("errors",errors);
        }else{

            campaignCommand.getRules().setSexRule(sexRule);
        }

        return view;
    }


    @RequestMapping(value="/createCampaign/rule", params={"removeSexRule"})
    public ModelAndView removeSexRule( HttpServletRequest request) {
        ModelAndView view = new ModelAndView();
        view.setView(new MappingJackson2JsonView());

        CampaignCommand campaignCommand = (CampaignCommand) request.getSession().getAttribute("campaignCommand");
        campaignCommand.getRules().setSexRule(null);

        return view;
    }


    @RequestMapping("/createCampaign/get")
    public @ResponseBody
    CampaignCommand getCampaign(HttpServletRequest request){
        return (CampaignCommand) request.getSession().getAttribute("campaignCommand");
    }




    @RequestMapping(value = IMetaDataController.Path.CREATE_CAMPAIGN_STEP_3,method = RequestMethod.GET)
    public String step3(Map<String, Object> model,HttpServletRequest request){
        CampaignCommand campaignCommand = (CampaignCommand) request.getSession().getAttribute("campaignCommand");
        model.put("adService",campaignCommand.getAdServices());
        return IMetaDataController.View.CREATE_CAMPAIGN_STEP_3;
    }

    @RequestMapping(value = IMetaDataController.Path.CREATE_CAMPAIGN_STEP_3,method = RequestMethod.POST)
    public String step2Submit(@ModelAttribute("adService") AdService adService,BindingResult bindingResults,HttpServletRequest request){
        campaignCommandValidator.validate(adService,bindingResults);
        if(bindingResults.hasErrors()){
            return IMetaDataController.View.CREATE_CAMPAIGN_STEP_2;
        }else{
            CampaignCommand campaignCommand = (CampaignCommand) request.getSession().getAttribute("campaignCommand");
            campaignCommand.setAdServices(adService);
            return IMetaDataController.PathUtils.REDIRECT+IMetaDataController.Path.CREATE_CAMPAIGN_STEP_3;
        }
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

    @RequestMapping("/manageAds/saveStep/1")
    public @ResponseBody
    ModelAndView step1(@ModelAttribute("command") InformationCommand informationCommand,ModelMap model,HttpServletRequest request, HttpServletResponse response) throws IOException {
        //ObjectMapper mapper = new ObjectMapper();
        //CampaignCommand campaignCommand = mapper.readValue(request.getParameter("command"), CampaignCommand.class);
        //return saveStep( 1, campaignCommand,request);
        ModelAndView view = new ModelAndView();
        view.setView(new MappingJackson2JsonView());
        return view;
    }


    @RequestMapping(IMetaDataController.Path.SAVE_STEP)
    public @ResponseBody
    ModelAndView save(@PathVariable Integer step, @ModelAttribute CampaignCommand campaignCommand,ModelMap model,HttpServletRequest request, HttpServletResponse response) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        //CampaignCommand campaignCommand = mapper.readValue(request.getParameter("command"), CampaignCommand.class);
        return saveStep( step, campaignCommand,request);
    }

    private ModelAndView saveStep(Integer step, CampaignCommand campaignCommand, HttpServletRequest request) throws IOException {
        ModelAndView view = new ModelAndView();
        view.setView(new MappingJackson2JsonView());

            BindingResult bindingResults = new DirectFieldBindingResult(CampaignCommand.class,"campaignCommand");
            Map<String,String> errors = new HashMap<String, String>();
            switch (step){
                case 1:
                    campaignCommandValidator.validate(campaignCommand.getInformation(),bindingResults);
/*                    if(file!=null && file.isEmpty()){
                        errors.put("","required");
                    }else{
                        FileCommand ad = new FileCommand(file);
                        request.getSession().setAttribute("ad",file);
                    }*/
                    //Todo:
                    break;
                case 2:
                    campaignCommandValidator.validate(campaignCommand.getProduct(),bindingResults);
/*                    if(file!=null && file.isEmpty()){
                        errors.put("","required");
                    }else{
                        FileCommand ad = new FileCommand(file);
                        request.getSession().setAttribute("product",file);
                    }*/
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


/*    @RequestMapping(value = IMetaDataController.Path.SAVE_STEP_NO_FILE, method = RequestMethod.POST)
    public @ResponseBody
    ModelAndView saveNoFile( @PathVariable Integer step, ModelMap model,HttpServletRequest request, HttpServletResponse response) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        CampaignCommand campaignCommand = mapper.readValue(request.getInputStream(), CampaignCommand.class);
        return saveStep(null, step, campaignCommand, request);
    }*/




}
