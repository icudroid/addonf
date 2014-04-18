package fr.k2i.adbeback.webapp.controller;

import fr.k2i.adbeback.core.business.Constants;
import fr.k2i.adbeback.core.business.ad.Brand;
import fr.k2i.adbeback.core.business.user.AgencyUser;
import fr.k2i.adbeback.core.business.user.BrandUser;
import fr.k2i.adbeback.core.business.user.MediaType;
import fr.k2i.adbeback.core.business.user.User;
import fr.k2i.adbeback.webapp.bean.*;
import fr.k2i.adbeback.webapp.bean.adservice.AdResponseBean;
import fr.k2i.adbeback.webapp.bean.adservice.BrandRuleBean;
import fr.k2i.adbeback.webapp.bean.adservice.OpenMultiRuleBean;
import fr.k2i.adbeback.webapp.bean.adservice.OpenRuleBean;
import fr.k2i.adbeback.webapp.bean.enroll.media.CategoryPriceBean;
import fr.k2i.adbeback.webapp.bean.enroll.media.CategoryPriceBeans;
import fr.k2i.adbeback.webapp.facade.AdCampaignFacade;
import fr.k2i.adbeback.webapp.facade.UserFacade;
import fr.k2i.adbeback.webapp.validator.CampaignCommandValidator;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.*;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
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

    protected final Log logger = LogFactory.getLog(this.getClass());

    public static final String UPLOADED_IMG = "uploadedImg";
    @Autowired
    private UserFacade userFacade;

    @Autowired
    private AdCampaignFacade adCampaignFacade;


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
        List<AdBean> ads = userFacade.getAdsForConnectedUser();
        model.addAttribute("campaigns",ads);
        return IMetaDataController.View.LIST_CAMPAIGNS;
    }


    @RequestMapping(value = IMetaDataController.Path.CREATE_CAMPAIGN,method = RequestMethod.GET)
    public String createCampaign(Map<String, Object> model,HttpServletRequest request) throws Exception {
        CampaignCommand campaignCommand  = new CampaignCommand();

        String res = null;
        User currentUser = userFacade.getCurrentUser();
        if (currentUser instanceof AgencyUser) {
            res = IMetaDataController.PathUtils.REDIRECT+IMetaDataController.Path.CREATE_CAMPAIGN_STEP_0;
        }else if (currentUser instanceof BrandUser) {
            BrandUser user = (BrandUser) currentUser;
            campaignCommand.setBrand(new BrandBean(user.getBrand()));
            res = IMetaDataController.PathUtils.REDIRECT+IMetaDataController.Path.CREATE_CAMPAIGN_STEP_1;
        }else{
            throw new Exception("");
        }

        request.getSession().setAttribute("campaignCommand", campaignCommand);
        return res;
    }

    @RequestMapping(value = IMetaDataController.Path.MODIFY_CAMPAIGN,method = RequestMethod.GET)
    public String modifyCampaign(@PathVariable Long idAd,Map<String, Object> model,HttpServletRequest request) throws Exception {

        CampaignCommand campaignCommand = userFacade.loadCampaign(idAd);
        request.getSession().setAttribute("campaignCommand", campaignCommand);
        return IMetaDataController.PathUtils.REDIRECT+IMetaDataController.Path.MODIFY_CAMPAIGN_STEP_1;
    }


    @RequestMapping(value = IMetaDataController.Path.CREATE_CAMPAIGN_STEP_1,method = RequestMethod.GET)
    public String step1(Map<String, Object> model,HttpServletRequest request){
        CampaignCommand campaignCommand = (CampaignCommand) request.getSession().getAttribute("campaignCommand");
        if(campaignCommand ==null){
            campaignCommand = new CampaignCommand();
            request.getSession().setAttribute("campaignCommand", campaignCommand);
        }

        model.put("informationCommand",campaignCommand.getInformation());
        model.put("actionCampaign","create");
        return IMetaDataController.View.CREATE_CAMPAIGN_STEP_1;
    }

    @RequestMapping(value = IMetaDataController.Path.MODIFY_CAMPAIGN_STEP_1,method = RequestMethod.GET)
    public String modifyStep1(Map<String, Object> model,HttpServletRequest request){
        CampaignCommand campaignCommand = (CampaignCommand) request.getSession().getAttribute("campaignCommand");
        model.put("informationCommand",campaignCommand.getInformation());
        model.put("actionCampaign","modify");
        return IMetaDataController.View.MODIFY_CAMPAIGN_STEP_1;
    }



    @RequestMapping(value = IMetaDataController.Path.CREATE_CAMPAIGN_STEP_0,method = RequestMethod.GET)
    public String step0(Map<String, Object> model,HttpServletRequest request){
        CampaignCommand campaignCommand = (CampaignCommand) request.getSession().getAttribute("campaignCommand");
        if(campaignCommand ==null){
            campaignCommand = new CampaignCommand();
            request.getSession().setAttribute("campaignCommand", campaignCommand);
        }
        List<BrandBean> brands = userFacade.getBrandInChargeOfCurrentUser();
        model.put("brands",brands);
        model.put("brandBean",campaignCommand.getBrand());
        model.put("actionCampaign","create");
        return IMetaDataController.View.CREATE_CAMPAIGN_STEP_0;
    }


    @RequestMapping(value = IMetaDataController.Path.CREATE_CAMPAIGN_STEP_0,method = RequestMethod.POST)
    public String step0Submit(@ModelAttribute("brandBean") BrandBean brandBean,BindingResult bindingResults,Map<String, Object> model,HttpServletRequest request) throws IOException {

        if(brandBean.getId()==null || brandBean.getId() == -1){
            bindingResults.rejectValue("id","required");
        }

        if(bindingResults.hasErrors()){
            return IMetaDataController.View.CREATE_CAMPAIGN_STEP_0;
        }else{
            CampaignCommand campaignCommand = (CampaignCommand) request.getSession().getAttribute("campaignCommand");
            campaignCommand.setBrand(brandBean);
            return IMetaDataController.PathUtils.REDIRECT+IMetaDataController.Path.CREATE_CAMPAIGN_STEP_1;
        }
    }


    @RequestMapping(value = IMetaDataController.Path.CREATE_CAMPAIGN_STEP_1,method = RequestMethod.POST)
    public String step1Submit(@ModelAttribute("informationCommand") InformationCommand informationCommand,BindingResult bindingResults,Map<String, Object> model,HttpServletRequest request) throws IOException {
        campaignCommandValidator.validate(informationCommand,bindingResults);
        model.put("actionCampaign","create");
        if(bindingResults.hasErrors()){
            return IMetaDataController.View.CREATE_CAMPAIGN_STEP_1;
        }else{
            CampaignCommand campaignCommand = (CampaignCommand) request.getSession().getAttribute("campaignCommand");
            informationCommand.setAdFileCommand(new FileCommand(informationCommand.getAdFile()));
            campaignCommand.setInformation(informationCommand);
            return IMetaDataController.PathUtils.REDIRECT+IMetaDataController.Path.CREATE_CAMPAIGN_STEP_2;
        }
    }


    @RequestMapping(value = IMetaDataController.Path.MODIFY_CAMPAIGN_STEP_1,method = RequestMethod.POST)
    public String modifyStep1Submit(@ModelAttribute("informationCommand") InformationCommand informationCommand,BindingResult bindingResults,Map<String, Object> model,HttpServletRequest request) throws IOException {
        campaignCommandValidator.validateModify(informationCommand,bindingResults);
        model.put("actionCampaign","modify");
        if(bindingResults.hasErrors()){
            return IMetaDataController.View.CREATE_CAMPAIGN_STEP_1;
        }else{
            CampaignCommand campaignCommand = (CampaignCommand) request.getSession().getAttribute("campaignCommand");
            if(!informationCommand.getAdFile().isEmpty()){
                informationCommand.setAdFileCommand(new FileCommand(informationCommand.getAdFile()));
            }
            campaignCommand.setInformation(informationCommand);
            return IMetaDataController.PathUtils.REDIRECT+IMetaDataController.Path.MODIFY_CAMPAIGN_STEP_2;
        }
    }


    @RequestMapping(value = IMetaDataController.Path.CREATE_CAMPAIGN_STEP_2,method = RequestMethod.GET)
    public String step2(Map<String, Object> model,HttpServletRequest request){
        CampaignCommand campaignCommand = (CampaignCommand) request.getSession().getAttribute("campaignCommand");
        model.put("adRulesCommand",campaignCommand.getRules());
        model.put("actionCampaign","create");
        return IMetaDataController.View.CREATE_CAMPAIGN_STEP_2;
    }


    @RequestMapping(value = IMetaDataController.Path.MODIFY_CAMPAIGN_STEP_2,method = RequestMethod.GET)
    public String modifyStep2(Map<String, Object> model,HttpServletRequest request){
        CampaignCommand campaignCommand = (CampaignCommand) request.getSession().getAttribute("campaignCommand");
        model.put("adRulesCommand",campaignCommand.getRules());
        model.put("actionCampaign","modify");
        return IMetaDataController.View.MODIFY_CAMPAIGN_STEP_2;
    }


    @RequestMapping(value=IMetaDataController.Path.RULE, params={"addCountryRule"})
    public ModelAndView addCountryRule(@RequestBody CountryRuleBean countryRule, final BindingResult bindingResult, HttpServletRequest request) {
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

    @RequestMapping(value=IMetaDataController.Path.RULE, params={"removeCountryRule"})
    public ModelAndView removeCountryRule(@RequestBody CountryRuleBean countryRule, HttpServletRequest request) {
        ModelAndView view = new ModelAndView();
        view.setView(new MappingJackson2JsonView());

        CampaignCommand campaignCommand = (CampaignCommand) request.getSession().getAttribute("campaignCommand");


        List<CountryRuleBean> countryRules = campaignCommand.getRules().getCountryRules();
        if(countryRule.getId() != null){
            for (CountryRuleBean rule : countryRules) {
                if(rule.getId().equals(rule.getId())){
                    countryRules.remove(rule);
                    break;
                }
            }
        }else{
            for (CountryRuleBean rule : countryRules) {
                if(rule.getCountry().getId().equals(rule.getCountry().getId())){
                    countryRules.remove(rule);
                    break;
                }
            }

        }


        return view;
    }



    @RequestMapping(value=IMetaDataController.Path.RULE, params={"addCityRule"})
    public ModelAndView addCityRule(@RequestBody CityRuleBean cityRule, final BindingResult bindingResult, HttpServletRequest request) {
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


    @RequestMapping(value=IMetaDataController.Path.RULE, params={"removeCityRule"})
    public ModelAndView removeCountryRule(@RequestBody CityRuleBean cityRule, HttpServletRequest request) {
        ModelAndView view = new ModelAndView();
        view.setView(new MappingJackson2JsonView());

        CampaignCommand campaignCommand = (CampaignCommand) request.getSession().getAttribute("campaignCommand");
        List<CityRuleBean> cityRules = campaignCommand.getRules().getCityRules();
        if(cityRule.getId() != null){
            for (CityRuleBean rule : cityRules) {
                if(rule.getId().equals(cityRule.getId())){
                    cityRules.remove(rule);
                    break;
                }
            }
        }else{
            for (CityRuleBean rule : cityRules) {
                if(rule.getCity().getId().equals(cityRule.getCity().getId())){
                    cityRules.remove(rule);
                    break;
                }
            }

        }

        return view;
    }




    @RequestMapping(value=IMetaDataController.Path.RULE, params={"setAgeRule"})
    public ModelAndView setAgeRule(@RequestBody AgeRuleBean ageRule, final BindingResult bindingResult, HttpServletRequest request) {
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


    @RequestMapping(value=IMetaDataController.Path.RULE, params={"removeAgeRule"})
    public ModelAndView removeAgeRule( HttpServletRequest request) {
        ModelAndView view = new ModelAndView();
        view.setView(new MappingJackson2JsonView());

        CampaignCommand campaignCommand = (CampaignCommand) request.getSession().getAttribute("campaignCommand");
        campaignCommand.getRules().setAgeRule(null);

        return view;
    }



    @RequestMapping(value=IMetaDataController.Path.RULE, params={"setSexRule"})
    public ModelAndView setSexRule(@RequestBody SexRuleBean sexRule, final BindingResult bindingResult, HttpServletRequest request) {
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


    @RequestMapping(value=IMetaDataController.Path.RULE, params={"removeSexRule"})
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
        model.put("actionCampaign","create");
        return IMetaDataController.View.CREATE_CAMPAIGN_STEP_3;
    }



    @RequestMapping(value = IMetaDataController.Path.MODIFY_CAMPAIGN_STEP_3,method = RequestMethod.GET)
    public String modifyStep3(Map<String, Object> model,HttpServletRequest request){
        CampaignCommand campaignCommand = (CampaignCommand) request.getSession().getAttribute("campaignCommand");
        model.put("adService",campaignCommand.getAdServices());
        model.put("actionCampaign","modify");
        return IMetaDataController.View.MODIFY_CAMPAIGN_STEP_3;
    }



    @RequestMapping(value=IMetaDataController.Path.RULE, params={"addBrandRule"})
    public ModelAndView addBrandRule(@RequestBody BrandRuleBean brandRuleBean, final BindingResult bindingResult, HttpServletRequest request) {
        ModelAndView view = new ModelAndView();
        view.setView(new MappingJackson2JsonView());
        brandRuleBean.setUid(UUID.randomUUID().toString());
        CampaignCommand campaignCommand = (CampaignCommand) request.getSession().getAttribute("campaignCommand");
        if(campaignCommand.getAdServices().getBrandRules().contains(brandRuleBean)){
            bindingResult.reject("Exists");
        }

        if(bindingResult.hasErrors()){
            Map<String,String> errors = new HashMap<String, String>();
            for (ObjectError objectError : bindingResult.getAllErrors()) {
                errors.put(objectError.getObjectName(),objectError.getCode());
            }
            view.addObject("errors",errors);
        }else{

            campaignCommand.getAdServices().getBrandRules().add(brandRuleBean);
            view.addObject("rule",brandRuleBean);
        }

        return view;
    }



    @RequestMapping(value=IMetaDataController.Path.RULE, params={"modifyBrandRule"})
    public ModelAndView modifyBrandRule(@RequestBody BrandRuleBean brandRuleBean, final BindingResult bindingResult, HttpServletRequest request) {
        ModelAndView view = new ModelAndView();
        view.setView(new MappingJackson2JsonView());


        CampaignCommand campaignCommand = (CampaignCommand) request.getSession().getAttribute("campaignCommand");
        //int index = campaignCommand.getAdServices().getBrandRules().indexOf(brandRuleBean);
        int index = 0;
        List<BrandRuleBean> brandRules = campaignCommand.getAdServices().getBrandRules();
        if(brandRuleBean.getId() != null){
            for (BrandRuleBean rule : brandRules) {
                if(rule.getId().equals(brandRuleBean.getId())){
                    break;
                }
                index++;
            }
        }else{
            for (BrandRuleBean rule : brandRules) {
                if(rule.getUid().equals(brandRuleBean.getUid())){
                    break;
                }
                index++;
            }

        }

        campaignCommand.getAdServices().getBrandRules().set(index,brandRuleBean);
        view.addObject("rule",brandRuleBean);
        return view;
    }


    @RequestMapping(value=IMetaDataController.Path.RULE, params={"removeBrandRule"})
    public ModelAndView removeCountryRule(@RequestBody BrandRuleBean brandRuleBean, HttpServletRequest request) {
        ModelAndView view = new ModelAndView();
        view.setView(new MappingJackson2JsonView());

        CampaignCommand campaignCommand = (CampaignCommand) request.getSession().getAttribute("campaignCommand");


        List<BrandRuleBean> brandRules = campaignCommand.getAdServices().getBrandRules();
        if(brandRuleBean.getId() != null){
            for (BrandRuleBean rule : brandRules) {
                if(rule.getId().equals(brandRuleBean.getId())){
                    brandRules.remove(rule);
                    break;
                }
            }
        }else{
            for (BrandRuleBean rule : brandRules) {
                if(rule.getUid().equals(brandRuleBean.getUid())){
                    brandRules.remove(rule);
                    break;
                }
            }

        }


        return view;
    }





    @RequestMapping(value=IMetaDataController.Path.RULE, params={"addOpenRule"})
    public ModelAndView addOpenRule(@RequestBody OpenRuleBean openRuleBean, final BindingResult bindingResult, HttpServletRequest request) {
        ModelAndView view = new ModelAndView();
        view.setView(new MappingJackson2JsonView());
        CampaignCommand campaignCommand = (CampaignCommand) request.getSession().getAttribute("campaignCommand");
        openRuleBean.setUid(UUID.randomUUID().toString());
        FileCommand uploadedImg[] = (FileCommand[]) request.getSession().getAttribute(UPLOADED_IMG);
        List<AdResponseBean> responses = openRuleBean.getResponses();
        int i =0;
        if(uploadedImg!=null){
            for (AdResponseBean response : responses) {
                response.setImage(uploadedImg[i]);
                i++;
            }
        }

        if(campaignCommand.getAdServices().getOpenRules().contains(openRuleBean)){
            bindingResult.reject("Exists");
        }

        if(bindingResult.hasErrors()){
            Map<String,String> errors = new HashMap<String, String>();
            for (ObjectError objectError : bindingResult.getAllErrors()) {
                errors.put(objectError.getObjectName(),objectError.getCode());
            }
            view.addObject("errors",errors);
        }else{

            campaignCommand.getAdServices().getOpenRules().add(openRuleBean);
            view.addObject("rule",openRuleBean);
        }

        return view;
    }


    @RequestMapping(value=IMetaDataController.Path.RULE, params={"modifyOpenRule"})
    public ModelAndView modifyOpenRule(@RequestBody OpenRuleBean openRuleBean, final BindingResult bindingResult, HttpServletRequest request) {
        ModelAndView view = new ModelAndView();
        view.setView(new MappingJackson2JsonView());

        FileCommand uploadedImg[] = (FileCommand[]) request.getSession().getAttribute(UPLOADED_IMG);
        List<AdResponseBean> responses = openRuleBean.getResponses();
        int i =0;
        if(uploadedImg!=null){
            for (AdResponseBean response : responses) {
                response.setImage(uploadedImg[i]);
                i++;
            }
        }

        CampaignCommand campaignCommand = (CampaignCommand) request.getSession().getAttribute("campaignCommand");

        //int index = campaignCommand.getAdServices().getOpenRules().indexOf(openRuleBean);

        int index = 0;
        List<OpenRuleBean> openRules = campaignCommand.getAdServices().getOpenRules();
        if(openRuleBean.getId() != null){
            for (OpenRuleBean rule : openRules) {
                if(rule.getId().equals(openRuleBean.getId())){
                    break;
                }
                index++;
            }
        }else{
            for (OpenRuleBean rule : openRules) {
                if(rule.getUid().equals(openRuleBean.getUid())){
                    break;
                }
                index++;
            }

        }


        campaignCommand.getAdServices().getOpenRules().set(index,openRuleBean);

        view.addObject("rule",openRuleBean);
        return view;
    }


    @RequestMapping(value=IMetaDataController.Path.RULE, params={"removeOpenRule"})
    public ModelAndView removeOpenRule(@RequestBody OpenRuleBean openRuleBean, HttpServletRequest request) {
        ModelAndView view = new ModelAndView();
        view.setView(new MappingJackson2JsonView());

        CampaignCommand campaignCommand = (CampaignCommand) request.getSession().getAttribute("campaignCommand");


        List<OpenRuleBean> openRules = campaignCommand.getAdServices().getOpenRules();
        if(openRuleBean.getId() != null){
            for (OpenRuleBean rule : openRules) {
                if(rule.getId().equals(openRuleBean.getId())){
                    openRules.remove(rule);
                    break;
                }
            }
        }else{
            for (OpenRuleBean rule : openRules) {
                if(rule.getUid().equals(openRuleBean.getUid())){
                    openRules.remove(rule);
                    break;
                }
            }

        }


        openRules.remove(openRuleBean);

        return view;
    }


/**********************************************************************************************************************/


@RequestMapping(value=IMetaDataController.Path.RULE, params={"addOpenMultiRule"})
public ModelAndView addOpenMultiRule(@RequestBody OpenMultiRuleBean openRuleBean, final BindingResult bindingResult, HttpServletRequest request) {
    ModelAndView view = new ModelAndView();
    view.setView(new MappingJackson2JsonView());
    CampaignCommand campaignCommand = (CampaignCommand) request.getSession().getAttribute("campaignCommand");
    openRuleBean.setUid(UUID.randomUUID().toString());
    FileCommand uploadedImg[] = (FileCommand[]) request.getSession().getAttribute(UPLOADED_IMG);
    List<AdResponseBean> responses = openRuleBean.getResponses();
    int i =0;
    if(uploadedImg!=null){
        for (AdResponseBean response : responses) {
            response.setImage(uploadedImg[i]);
            i++;
        }
    }

    if(campaignCommand.getAdServices().getOpenRules().contains(openRuleBean)){
        bindingResult.reject("Exists");
    }

    if(bindingResult.hasErrors()){
        Map<String,String> errors = new HashMap<String, String>();
        for (ObjectError objectError : bindingResult.getAllErrors()) {
            errors.put(objectError.getObjectName(),objectError.getCode());
        }
        view.addObject("errors",errors);
    }else{

        campaignCommand.getAdServices().getOpenMultiRules().add(openRuleBean);
        view.addObject("rule",openRuleBean);
    }

    return view;
}


    @RequestMapping(value=IMetaDataController.Path.RULE, params={"modifyOpenMultiRule"})
    public ModelAndView modifyOpenMultiRule(@RequestBody OpenMultiRuleBean openRuleBean, final BindingResult bindingResult, HttpServletRequest request) {
        ModelAndView view = new ModelAndView();
        view.setView(new MappingJackson2JsonView());

        FileCommand uploadedImg[] = (FileCommand[]) request.getSession().getAttribute(UPLOADED_IMG);
        List<AdResponseBean> responses = openRuleBean.getResponses();
        int i =0;
        if(uploadedImg!=null){
            for (AdResponseBean response : responses) {
                response.setImage(uploadedImg[i]);
                i++;
            }
        }

        CampaignCommand campaignCommand = (CampaignCommand) request.getSession().getAttribute("campaignCommand");

        //int index = campaignCommand.getAdServices().getOpenRules().indexOf(openRuleBean);

        int index = 0;
        List<OpenMultiRuleBean> openRules = campaignCommand.getAdServices().getOpenMultiRules();
        if(openRuleBean.getId() != null){
            for (OpenMultiRuleBean rule : openRules) {
                if(rule.getId().equals(openRuleBean.getId())){
                    break;
                }
                index++;
            }
        }else{
            for (OpenMultiRuleBean rule : openRules) {
                if(rule.getUid().equals(openRuleBean.getUid())){
                    break;
                }
                index++;
            }

        }


        campaignCommand.getAdServices().getOpenMultiRules().set(index,openRuleBean);

        view.addObject("rule",openRuleBean);
        return view;
    }


    @RequestMapping(value=IMetaDataController.Path.RULE, params={"removeOpenMultiRule"})
    public ModelAndView removeOpenMultiRule(@RequestBody OpenMultiRuleBean openRuleBean, HttpServletRequest request) {
        ModelAndView view = new ModelAndView();
        view.setView(new MappingJackson2JsonView());

        CampaignCommand campaignCommand = (CampaignCommand) request.getSession().getAttribute("campaignCommand");


        List<OpenMultiRuleBean> openRules = campaignCommand.getAdServices().getOpenMultiRules();
        if(openRuleBean.getId() != null){
            for (OpenMultiRuleBean rule : openRules) {
                if(rule.getId().equals(openRuleBean.getId())){
                    openRules.remove(rule);
                    break;
                }
            }
        }else{
            for (OpenMultiRuleBean rule : openRules) {
                if(rule.getUid().equals(openRuleBean.getUid())){
                    openRules.remove(rule);
                    break;
                }
            }

        }


        openRules.remove(openRuleBean);

        return view;
    }


    /**********************************************************************************************************************/



    @RequestMapping(value = IMetaDataController.Path.CREATE_CAMPAIGN_STEP_4,method = RequestMethod.GET)
    public String step4(Map<String, Object> model,HttpServletRequest request){
        CampaignCommand campaignCommand = (CampaignCommand) request.getSession().getAttribute("campaignCommand");
        DisplayOnMediasBean medias = campaignCommand.getMedias();
        if(medias ==null){
            //load media
            medias = adCampaignFacade.loadMediasBid();
            campaignCommand.setMedias(medias);
        }

        model.put("medias", medias);
        model.put("actionCampaign","create");
        return IMetaDataController.View.CREATE_CAMPAIGN_STEP_4;
    }




    @RequestMapping(value = IMetaDataController.Path.CREATE_CAMPAIGN_STEP_4,method = RequestMethod.POST)
    public String step4Submit(@ModelAttribute("categoryPriceBeans") CategoryPriceBeans categoryPriceBeans,BindingResult bindingResult,Map<String, Object> model,HttpServletRequest request) throws Exception {
        CampaignCommand campaignCommand = (CampaignCommand) request.getSession().getAttribute("campaignCommand");
        DisplayOnMediasBean medias = campaignCommand.getMedias();
        List<CategoryPriceBean> cps = categoryPriceBeans.getCategoryPriceBeans();

        boolean oneCpFilled = false;
        for (CategoryPriceBean cp : cps) {
            if(cp.getBid()!=null){
                oneCpFilled=true;
                setCategoryPriceBean(medias,cp);
            }
        }

        if(!oneCpFilled){
            bindingResult.reject("one.media.required");
            return IMetaDataController.View.CREATE_CAMPAIGN_STEP_4;
        }else{
            this.userFacade.save(campaignCommand);
            request.getSession().removeAttribute("campaignCommand");
            request.getSession().removeAttribute(UPLOADED_IMG);
            return IMetaDataController.PathUtils.REDIRECT+IMetaDataController.Path.LIST_CAMPAIGNS;
        }

    }







    @RequestMapping(value = IMetaDataController.Path.MODIFY_CAMPAIGN_STEP_4,method = RequestMethod.GET)
    public String mofifyStep4(Map<String, Object> model,HttpServletRequest request){
        CampaignCommand campaignCommand = (CampaignCommand) request.getSession().getAttribute("campaignCommand");
        DisplayOnMediasBean medias = campaignCommand.getMedias();
        if(medias ==null){
            //load media
            medias = adCampaignFacade.loadMediasBid();
            campaignCommand.setMedias(medias);
        }

        model.put("medias", medias);
        model.put("actionCampaign","modify");
        return IMetaDataController.View.MODIFY_CAMPAIGN_STEP_4;
    }




    @RequestMapping(value = IMetaDataController.Path.MODIFY_CAMPAIGN_STEP_4,method = RequestMethod.POST)
    public String modifyStep4Submit(@ModelAttribute("categoryPriceBeans") CategoryPriceBeans categoryPriceBeans,BindingResult bindingResult,Map<String, Object> model,HttpServletRequest request) throws Exception {
        CampaignCommand campaignCommand = (CampaignCommand) request.getSession().getAttribute("campaignCommand");
        DisplayOnMediasBean medias = campaignCommand.getMedias();
        List<CategoryPriceBean> cps = categoryPriceBeans.getCategoryPriceBeans();

        boolean oneCpFilled = false;
        for (CategoryPriceBean cp : cps) {
            if(cp.getBid()!=null){
                oneCpFilled=true;
                setCategoryPriceBean(medias,cp);
            }
        }

        if(!oneCpFilled){
            bindingResult.reject("one.media.required");
            model.put("actionCampaign","modify");
            return IMetaDataController.View.MODIFY_CAMPAIGN_STEP_4;
        }else{
            this.userFacade.save(campaignCommand);
            request.getSession().removeAttribute("campaignCommand");
            request.getSession().removeAttribute(UPLOADED_IMG);
            return IMetaDataController.PathUtils.REDIRECT+IMetaDataController.Path.LIST_CAMPAIGNS;
        }

    }


    private void setCategoryPriceBean(DisplayOnMediasBean medias, CategoryPriceBean cp) {

        Map<MediaBean, Map<MediaType, List<CategoryPriceBean>>> displays = medias.getDisplays();

        for (MediaBean mediaBean : displays.keySet()) {
            if(mediaBean.getId().equals(cp.getMediaId())){

                Map<MediaType, List<CategoryPriceBean>> mediaTypeListMap = displays.get(mediaBean);

                for (MediaType mediaType : mediaTypeListMap.keySet()) {

                    if(mediaType.equals(cp.getMediaType())){
                        List<CategoryPriceBean> categoryPriceBeans = mediaTypeListMap.get(cp.getMediaType());

                        int index = 0;
                        for (CategoryPriceBean categoryPriceBean : categoryPriceBeans) {
                            if(categoryPriceBean.getMediaType()==cp.getMediaType() && categoryPriceBean.getMediaId().equals(cp.getMediaId()) &&
                                    categoryPriceBean.getCategory().equals(cp.getCategory())){
                                cp.setPrice(categoryPriceBean.getPrice());
                                categoryPriceBeans.set(index,cp);
                                return;
                            }
                            index++;
                        }

                    }
                }
                break;
            }
        }

    }


    /**********************************************************************************************************************/










    @RequestMapping(value=IMetaDataController.Path.SAVE)
    public String save(HttpServletRequest request) throws Exception {

        CampaignCommand campaignCommand = (CampaignCommand) request.getSession().getAttribute("campaignCommand");

        this.userFacade.save(campaignCommand);
        request.getSession().removeAttribute("campaignCommand");
        request.getSession().removeAttribute(UPLOADED_IMG);
        return IMetaDataController.PathUtils.REDIRECT+IMetaDataController.Path.LIST_CAMPAIGNS;
    }


    @RequestMapping(value=IMetaDataController.Path.MODIFY)
    public String modify(HttpServletRequest request) throws Exception {

        CampaignCommand campaignCommand = (CampaignCommand) request.getSession().getAttribute("campaignCommand");

        this.userFacade.save(campaignCommand);
        request.getSession().removeAttribute("campaignCommand");
        return IMetaDataController.PathUtils.REDIRECT+IMetaDataController.Path.LIST_CAMPAIGNS;
    }



    @RequestMapping(value=IMetaDataController.Path.UPLOAD_IMG)
    public ModelAndView upload(@PathVariable Integer numOpenRule,@PathVariable Integer numResponse,@RequestBody MultipartFile file,HttpServletRequest request) throws Exception {

        FileCommand uploadedImg[] = (FileCommand[]) request.getSession().getAttribute(UPLOADED_IMG);
        if(uploadedImg==null){
            //no updated file
            uploadedImg = new FileCommand[3];
            request.getSession().setAttribute(UPLOADED_IMG, uploadedImg);
        }

        uploadedImg[numResponse] = new FileCommand(file);


        ModelAndView view = new ModelAndView();
        view.setView(new MappingJackson2JsonView());

        return view;
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
        return userFacade.getAllForConnectedUser(pageable);
    }


    @RequestMapping("/createCampaign/tmpImage/{numResponse}")
    public View downloadTmpImage(@PathVariable Integer numResponse,HttpServletRequest request, HttpServletResponse response) {
        FileCommand uploadedImg[] = (FileCommand[]) request.getSession().getAttribute(UPLOADED_IMG);

        if(uploadedImg==null){
            return new MappingJackson2JsonView();
        }

        try {
            FileCommand fileCommand = uploadedImg[numResponse];
            response.setContentType(URLConnection.getFileNameMap().getContentTypeFor(fileCommand.getFileName()));
            response.addHeader("Content-Disposition:","inline; filename=\""+fileCommand.getFileName()+"\"");

            response.setContentLength(fileCommand.getSize().intValue());
            ServletOutputStream outputStream = response.getOutputStream();

            InputStream memoryBuffer = new ByteArrayInputStream(fileCommand.getContent());

            int read = 0;
            byte[] b = new byte[1024];
            while ((read = memoryBuffer.read(b, 0, 1024)) > 0) {
                outputStream.write(b, 0, read);
                b = new byte[1024];
            }
            memoryBuffer.close();

        } catch (IOException e) {

        }

        return new MappingJackson2JsonView();
    }


    @RequestMapping("/createCampaign/tmpImage/empty")
    public View emptyDownloadTmpImage(HttpServletRequest request, HttpServletResponse response) {
        request.getSession().removeAttribute(UPLOADED_IMG);
        return new MappingJackson2JsonView();
    }

    @RequestMapping("/createCampaign/tmpImage/load/{numOpenRule}")
    public View loadDownloadTmpImage(@PathVariable Integer numOpenRule,HttpServletRequest request, HttpServletResponse response) {
        FileCommand uploadedImg[] = (FileCommand[]) request.getSession().getAttribute(UPLOADED_IMG);
        if(uploadedImg==null){
            //no updated file
            uploadedImg = new FileCommand[3];
            request.getSession().setAttribute(UPLOADED_IMG, uploadedImg);
        }
        CampaignCommand campaignCommand = (CampaignCommand) request.getSession().getAttribute("campaignCommand");

        List<OpenRuleBean> openRules = campaignCommand.getAdServices().getOpenRules();

        OpenRuleBean openRuleBean = openRules.get(numOpenRule);

        List<AdResponseBean> responses = openRuleBean.getResponses();

        int i =0;
        for (AdResponseBean adResponseBean : responses) {
            uploadedImg[i] = adResponseBean.getImage();
            i++;
        }

        return new MappingJackson2JsonView();
    }


}
