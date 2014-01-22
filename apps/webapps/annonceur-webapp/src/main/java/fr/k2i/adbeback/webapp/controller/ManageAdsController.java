package fr.k2i.adbeback.webapp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.k2i.adbeback.core.business.ad.AdType;
import fr.k2i.adbeback.core.business.ad.rule.AgeRule;
import fr.k2i.adbeback.core.business.ad.rule.CityRule;
import fr.k2i.adbeback.core.business.ad.rule.CountryRule;
import fr.k2i.adbeback.core.business.ad.rule.SexRule;
import fr.k2i.adbeback.webapp.bean.*;
import fr.k2i.adbeback.webapp.bean.adservice.AdResponseBean;
import fr.k2i.adbeback.webapp.bean.adservice.BrandRuleBean;
import fr.k2i.adbeback.webapp.bean.adservice.OpenRuleBean;
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


    public static final String UPLOADED_IMG = "uploadedImg";
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


    @RequestMapping(value = IMetaDataController.Path.CREATE_CAMPAIGN,method = RequestMethod.GET)
    public String createCampaign(Map<String, Object> model,HttpServletRequest request){
        CampaignCommand campaignCommand  = new CampaignCommand();
        request.getSession().setAttribute("campaignCommand", campaignCommand);
        return IMetaDataController.PathUtils.REDIRECT+IMetaDataController.Path.CREATE_CAMPAIGN_STEP_1;
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
    public String step1Submit(@ModelAttribute("informationCommand") InformationCommand informationCommand,BindingResult bindingResults,HttpServletRequest request) throws IOException {
        campaignCommandValidator.validate(informationCommand,bindingResults);
        if(bindingResults.hasErrors()){
            return IMetaDataController.View.CREATE_CAMPAIGN_STEP_1;
        }else{
            CampaignCommand campaignCommand = (CampaignCommand) request.getSession().getAttribute("campaignCommand");
            informationCommand.setAdFileCommand(new FileCommand(informationCommand.getAdFile()));
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


    @RequestMapping(value=IMetaDataController.Path.RULE, params={"addCountryRule"})
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


    @RequestMapping(value=IMetaDataController.Path.RULE, params={"removeCountryRule"})
    public ModelAndView removeCountryRule(@RequestBody CountryRule countryRule, HttpServletRequest request) {
        ModelAndView view = new ModelAndView();
        view.setView(new MappingJackson2JsonView());

        CampaignCommand campaignCommand = (CampaignCommand) request.getSession().getAttribute("campaignCommand");
        campaignCommand.getRules().getCountryRules().remove(countryRule);

        return view;
    }



    @RequestMapping(value=IMetaDataController.Path.RULE, params={"addCityRule"})
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


    @RequestMapping(value=IMetaDataController.Path.RULE, params={"removeCityRule"})
    public ModelAndView removeCountryRule(@RequestBody CityRule cityRule, HttpServletRequest request) {
        ModelAndView view = new ModelAndView();
        view.setView(new MappingJackson2JsonView());

        CampaignCommand campaignCommand = (CampaignCommand) request.getSession().getAttribute("campaignCommand");
        campaignCommand.getRules().getCityRules().remove(cityRule);

        return view;
    }




    @RequestMapping(value=IMetaDataController.Path.RULE, params={"setAgeRule"})
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


    @RequestMapping(value=IMetaDataController.Path.RULE, params={"removeAgeRule"})
    public ModelAndView removeAgeRule( HttpServletRequest request) {
        ModelAndView view = new ModelAndView();
        view.setView(new MappingJackson2JsonView());

        CampaignCommand campaignCommand = (CampaignCommand) request.getSession().getAttribute("campaignCommand");
        campaignCommand.getRules().setAgeRule(null);

        return view;
    }



    @RequestMapping(value=IMetaDataController.Path.RULE, params={"setSexRule"})
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
        return IMetaDataController.View.CREATE_CAMPAIGN_STEP_3;
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
        int index = campaignCommand.getAdServices().getBrandRules().indexOf(brandRuleBean);
        campaignCommand.getAdServices().getBrandRules().set(index,brandRuleBean);
        view.addObject("rule",brandRuleBean);
        return view;
    }


    @RequestMapping(value=IMetaDataController.Path.RULE, params={"removeBrandRule"})
    public ModelAndView removeCountryRule(@RequestBody BrandRuleBean brandRuleBean, HttpServletRequest request) {
        ModelAndView view = new ModelAndView();
        view.setView(new MappingJackson2JsonView());

        CampaignCommand campaignCommand = (CampaignCommand) request.getSession().getAttribute("campaignCommand");
        campaignCommand.getAdServices().getBrandRules().remove(brandRuleBean);

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
        int index = campaignCommand.getAdServices().getOpenRules().indexOf(openRuleBean);
        campaignCommand.getAdServices().getOpenRules().set(index,openRuleBean);

        view.addObject("rule",openRuleBean);
        return view;
    }


    @RequestMapping(value=IMetaDataController.Path.RULE, params={"removeOpenRule"})
    public ModelAndView removeOpenRule(@RequestBody OpenRuleBean openRuleBean, HttpServletRequest request) {
        ModelAndView view = new ModelAndView();
        view.setView(new MappingJackson2JsonView());

        CampaignCommand campaignCommand = (CampaignCommand) request.getSession().getAttribute("campaignCommand");
        campaignCommand.getAdServices().getOpenRules().remove(openRuleBean);

        return view;
    }




    @RequestMapping(value=IMetaDataController.Path.SAVE)
    public String save(HttpServletRequest request) throws Exception {

        CampaignCommand campaignCommand = (CampaignCommand) request.getSession().getAttribute("campaignCommand");

        this.brandServiceFacade.save(campaignCommand);
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
        return brandServiceFacade.getAllForConnectedUser(pageable);
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
