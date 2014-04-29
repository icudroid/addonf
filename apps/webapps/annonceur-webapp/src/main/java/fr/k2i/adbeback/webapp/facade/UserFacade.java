package fr.k2i.adbeback.webapp.facade;

import fr.k2i.adbeback.application.services.mail.IMailEngine;
import fr.k2i.adbeback.application.services.mail.dto.Email;
import fr.k2i.adbeback.application.services.mail.exception.SendException;
import fr.k2i.adbeback.core.business.Constants;
import fr.k2i.adbeback.core.business.ad.Ad;
import fr.k2i.adbeback.core.business.ad.Brand;
import fr.k2i.adbeback.core.business.ad.StaticAd;
import fr.k2i.adbeback.core.business.ad.VideoAd;
import fr.k2i.adbeback.core.business.ad.rule.*;
import fr.k2i.adbeback.core.business.otp.OTPUserSecurityConfirm;
import fr.k2i.adbeback.core.business.player.WebUser;
import fr.k2i.adbeback.core.business.user.*;
import fr.k2i.adbeback.crypto.DESCryptoService;
import fr.k2i.adbeback.dao.*;
import fr.k2i.adbeback.service.BrandManager;
import fr.k2i.adbeback.webapp.bean.*;
import fr.k2i.adbeback.webapp.bean.AdService;
import fr.k2i.adbeback.webapp.bean.adservice.AdResponseBean;
import fr.k2i.adbeback.webapp.bean.adservice.BrandRuleBean;
import fr.k2i.adbeback.webapp.bean.adservice.OpenMultiRuleBean;
import fr.k2i.adbeback.webapp.bean.adservice.OpenRuleBean;
import fr.k2i.adbeback.webapp.bean.enroll.media.CategoryPriceBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: dimitri
 * Date: 08/11/13
 * Time: 17:36
 * To change this template use File | Settings | File Templates.
 */
@Component
public class UserFacade {

/*
    @Autowired
    private BrandManager brandManager;
*/

    @Autowired
    private IAgencyDao agencyDao;

    @Autowired
    private IRoleDao roleDao;

    @Autowired
    private IWebUserDao webUserDao;

    @Autowired
    private MessageSource messageSource;


    @Autowired
    private DESCryptoService desCryptoService;

    @Autowired
    private IMailEngine mailEngine;



    @Value("${addonf.base.url}")
    private String urlBase;


    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private IOTPSecurityDao securityDao;


    @Value("${addonf.ads.location}")
    private String adsPath;

    @Value("${addonf.ads.tmp.location}")
    private String adsPathTmp;

    @Value("${addonf.logo.location}")
    private String logoPath;



    @Autowired
    private IAdDao adDao;


    @Autowired
    private ICityDao cityDao;

    @Autowired
    private ICountryDao countryDao;

    @Autowired
    private IBrandDao brandDao;

    @Autowired
    private IAdGameDao adGameDao;

    @Autowired
    private IMediaDao mediaDao;

    @Autowired
    private ICategoryDao categoryDao;


    @Transactional
    public User getCurrentUser() throws Exception{
        Object principal = getAuthenticationPlayer().getPrincipal();
        if (!(principal instanceof WebUser)) {
            throw new Exception("Please check configuration. Should be Brand in the principal.");
        }else{
            WebUser webUser = (WebUser) principal;

            User user = webUser.getUser();
            if (user instanceof AgencyUser || user instanceof Admin || user instanceof BrandUser || user instanceof MediaUser) {
                return webUserDao.get(((WebUser) principal).getUser().getId());
            }else{
                throw new Exception("Please check configuration. Should be valid user in the principal.");
            }

        }




    }

    protected Authentication getAuthenticationPlayer() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    @Transactional
    public void setLogo(MultipartFile logo) throws Exception {
        //set logo of brand
        User user = getCurrentUser();
        if (user instanceof BrandUser) {
            BrandUser brandUser = (BrandUser) user;
            brandUser.getBrand().setLogo(FileUtils.saveFile(logo.getBytes(),logoPath));
        }

        if (user instanceof BrandUser) {
            BrandUser brandUser = (BrandUser) user;
            brandUser.getBrand().setLogo(FileUtils.saveFile(logo.getBytes(),logoPath));
        }else if (user instanceof MediaUser) {
            MediaUser mediaUser = (MediaUser) user;
            Media media = mediaDao.findByMediaUser(mediaUser);
            media.setLogo(FileUtils.saveFile(logo.getBytes(),logoPath));
        }else if (user instanceof AgencyUser) {
            AgencyUser agencyUser = (AgencyUser) user;
            agencyUser.getAgency().setLogo(FileUtils.saveFile(logo.getBytes(),logoPath));
        }

    }






    @Transactional
    public void sendForgottenPasswd(String username,HttpServletRequest request) throws SendException {

        User user = webUserDao.getUserByEmail(username);

        if(user != null){

            Map<String, Object> modelEmail = new HashMap<String, Object>();
            String endUrl = desCryptoService.generateOtpConfirm(user.getUsername(), user, 48);
            modelEmail.put("name",user.getUsername());
            modelEmail.put("url",urlBase+"pwdinit/"+endUrl);
            Email forgottenPasswd = Email.builder()
                    .subject("Mot de passe oublié")
                    .model(modelEmail)
                    .content("email/forgottenPasswd")
                    .recipients(user.getEmail())
                    .noAttachements()
                    .build();

            try{
                mailEngine.sendMessage(forgottenPasswd,request.getLocale());
            }catch (Exception e){

            }
        }


    }



    @Transactional
    public boolean isValidateOtp(String username, String key){
        User user = webUserDao.getUserByEmail(username);
        if(user ==null){
            return false;
        }
        OTPUserSecurityConfirm otp = securityDao.findByUser(user);
        if(otp == null){
            return false;
        }
        if(!otp.getKey().equals(key)){
            return false;
        }

        return true;
    }


    @Transactional
    public void changePasswd(String username, String password) {
        User user = webUserDao.getUserByEmail(username);
        webUserDao.setPassword(user.getId(), password);
        OTPUserSecurityConfirm otp = securityDao.findByUser(user);
        securityDao.remove(otp);
    }

    @Transactional
    public void changePasswd(String password) throws Exception {
        webUserDao.setPassword(getCurrentUser().getId(), password);
    }

    public void validateChangePwdBean(ChangePwdBean changePwdBean, Errors errors,Locale locale) throws Exception {

        if(!errors.hasErrors()){
            String password = getCurrentUser().getPassword();
            String old = passwordEncoder.encodePassword(changePwdBean.getOldPassword(), null);
            if(!password.equals(old)){
                errors.rejectValue("oldPassword","notMatch",messageSource.getMessage("addonf.notMatch",null,locale));
            }

            if(!changePwdBean.getNewConfirmPassword().equals(changePwdBean.getNewPassword())){
                errors.rejectValue("newConfirmPassword","notMatch",messageSource.getMessage("addonf.notMatch",null,locale));
            }
        }

    }






    @Transactional
    public List<AdBean> getAdsForConnectedUser() throws Exception {
        List<Brand> brands = getBrandForConnectedUser();
        List<AdBean> res = new ArrayList<AdBean>();

        if(brands.isEmpty()){
            return res;
        }

        List<Ad> ads = adDao.findByBrands(brands);

        for (Ad ad : ads) {
            res.add(new AdBean(ad));
        }
        return res;
    }

    private List<Brand> getBrandForConnectedUser() throws Exception {
        List<Brand> brands = new ArrayList<Brand>();

        User user = getCurrentUser();
        if (user instanceof BrandUser) {
            BrandUser brandUser = (BrandUser) user;
            brands.add(brandUser.getBrand());
        }else if (user instanceof AgencyUser) {
            List<Brand> inChargeOf = ((AgencyUser) user).getInChargeOf();
            brands.addAll(inChargeOf);
        }

        return brands;
    }

    @Transactional
    public Page<AdBean> getAllForConnectedUser(Pageable pageable) throws Exception {
        List<Brand> brands = getBrandForConnectedUser();

        List<AdBean> res = new ArrayList<AdBean>();
        Page<Ad> ads = adDao.findByBrands(brands, pageable);

        for (Ad ad : ads) {
            res.add(new AdBean(ad));
        }
        return new PageImpl<AdBean>(res,pageable,ads.getTotalElements());
    }

    public CampaignCommand createCampaign() {
        return new CampaignCommand();
    }

    @Transactional
    public void save(CampaignCommand campaignCommand) throws Exception {

        Ad ad = null;

        Brand brand = brandDao.get(campaignCommand.getBrand().getId());

        InformationCommand information = campaignCommand.getInformation();

        if(information.getId()!=null){
            ad = adDao.get(information.getId());
        }else {
            if (AdDisplay.VIDEO.equals(information.getDisplayAd())){
                ad = new VideoAd();
                //video
            }else{
                //static
                ad = new StaticAd();
            }
        }

        ad.setDuration(information.getDisplayDuration()*1000);

        if(information.getAdFileCommand()!=null){
            if (AdDisplay.VIDEO.equals(information.getDisplayAd())){
                if (ad instanceof VideoAd) {
                    VideoAd videoAd = (VideoAd) ad;
                    videoAd.setAdFileEncoded(false);
                }
                ad.setAdFile(FileUtils.saveFile(information.getAdFileCommand().getContent(),adsPathTmp));
            }else{
                ad.setAdFile(FileUtils.saveFile(information.getAdFileCommand().getContent(),adsPath));
            }
        }
        ad.setBrand(brand);
        ad.setEndDate(information.getEndDate());
        if(ad.getId()==null){
            ad.setInitialAmount(information.getInitialAmonut());
        }
        ad.setName(information.getName());
        ad.setStartDate(information.getStartDate());

        if(information.getInitialAmonut()>0.0){
            AmountRule amountRule = new AmountRule();
            amountRule.setAmount(information.getInitialAmonut());
            amountRule.setInitialAmount(information.getInitialAmonut());
            amountRule.setInserted(new Date());
            ad.addRule(amountRule);
        }

        AdRulesCommand restrictionRules = campaignCommand.getRules();

        if(ad.getId()!=null){
            deleteNotNeededCountryRule(ad,restrictionRules.getCountryRules());
            deleteNotNeededCityRule(ad, restrictionRules.getCityRules());
            deleteNotNeededAgeRule(ad, restrictionRules.getAgeRule());
            deleteNotNeededSexyRule(ad, restrictionRules.getSexRule());
            adDao.save(ad);
        }

        for (CountryRuleBean countryRule : restrictionRules.getCountryRules()) {
            CountryRule c = new CountryRule();
            c.setCountry(countryDao.findByCode(countryRule.getCountry().getCode()));
            ad.addRule(c);
        }


        for (CityRuleBean cityRule : restrictionRules.getCityRules()) {
            CityRule c = new CityRule();
            c.setAround(cityRule.getAround());
            c.setCity(cityDao.get(cityRule.getCity().getId()));
            ad.addRule(c);
        }

        SexRuleBean sexRule = restrictionRules.getSexRule();
        if(sexRule!=null){
            ad.addRule(sexRule.toSexRule());
        }

        AgeRuleBean ageRule = restrictionRules.getAgeRule();
        if(ageRule!=null){
            ad.addRule(ageRule.toAgeRule());
        }


        AdService adServices = campaignCommand.getAdServices();

        List<BrandRuleBean> brandRules = adServices.getBrandRules();

        deleteNotNeededBrandRule(ad, brandRules);
        //ad.getRules().removeAll(ad.getRules(BrandRule.class));

        for (BrandRuleBean brandRule : brandRules) {
            BrandRule b =null;

            if(brandRule.getId() != null){
                List<BrandRule> rules = ad.getRules(BrandRule.class);
                for (BrandRule rule : rules) {
                    if(rule.getId().equals(brandRule.getId())){
                        b = rule;
                        break;
                    }
                }
            }else{
                b = new BrandRule();
                ad.addRule(b);
            }

            b.setName(brandRule.getName());
            b.setAd(ad);
            b.setStartDate(brandRule.getStartDate());
            b.setEndDate(brandRule.getEndDate());
            //b.setPrice(showBrandPrice);
            b.setQuestion("Quel est le Logo de la publicité ?");
            b.setMaxDisplayByUser(b.getMaxDisplayByUser());

            b.getNoDisplayWith().clear();
            List<BrandBean> noDisplayWith = brandRule.getNoDisplayWith();
            for (BrandBean bNo : noDisplayWith) {
                b.addNoDisplayWith(brandDao.get(bNo.getId()));
            }

        }


        List<OpenRuleBean> openRules = adServices.getOpenRules();
        //ad.getRules().removeAll(ad.getRules(OpenRule.class));
        deleteNotNeededOpenRule(ad,openRules);

        for (OpenRuleBean openRule : openRules) {
            OpenRule o = new OpenRule();

            if(openRule.getId() != null){
                List<OpenRule> rules = ad.getRules(OpenRule.class);
                for (OpenRule rule : rules) {
                    if(rule.getId().equals(openRule.getId())){
                        o = rule;
                        break;
                    }
                }
            }else{
                o = new OpenRule();
                ad.addRule(o);
            }

            o.setName(openRule.getName());
            o.setAd(ad);
            o.setStartDate(openRule.getStartDate());
            o.setEndDate(openRule.getEndDate());
            o.setQuestion(openRule.getQuestion());
            //o.setPrice(showOpenPrice);
            o.setMaxDisplayByUser(openRule.getMaxDisplayByUser());

            if(o.getResponses()!=null)o.getResponses().clear();

            List<AdResponseBean> responses = openRule.getResponses();
            for (AdResponseBean response : responses) {
                AdResponse adResponse = new AdResponse();
                if(response.getImage()!=null){
                    adResponse.setImage(FileUtils.saveFile(response.getImage().getContent(),logoPath));
                }
                adResponse.setResponse(response.getResponse());
                o.addResponse(adResponse);
                if(response.isCorrect()){
                    o.setCorrect(adResponse);
                }
            }


        }



        List<OpenMultiRuleBean> openMultiRules = adServices.getOpenMultiRules();
        //ad.getRules().removeAll(ad.getRules(OpenRule.class));
        deleteNotNeededOpenMultiRule(ad, openMultiRules);

        for (OpenMultiRuleBean openRule : openMultiRules) {
            MultiResponseRule o = new MultiResponseRule();

            if(openRule.getId() != null){
                List<MultiResponseRule> rules = ad.getRules(MultiResponseRule.class);
                for (MultiResponseRule rule : rules) {
                    if(rule.getId().equals(openRule.getId())){
                        o = rule;
                        break;
                    }
                }
            }else{
                o = new MultiResponseRule();
                ad.addRule(o);
            }

            o.setName(openRule.getName());
            o.setAd(ad);
            o.setStartDate(openRule.getStartDate());
            o.setEndDate(openRule.getEndDate());
            o.setQuestion(openRule.getQuestion());
            //o.setPrice(showOpenPrice);
            o.setMaxDisplayByUser(openRule.getMaxDisplayByUser());
            o.setAddonText(openRule.getAddonText());
            o.setBtnValidText(openRule.getBtnValidText());

            if(o.getResponses()!=null)o.getResponses().clear();

            List<AdResponse> corrects = o.getCorrects();
            if(corrects==null){
                corrects = new ArrayList<AdResponse>();
                o.setCorrects(corrects);
            }else{
                o.getCorrects().clear();
            }


            List<AdResponseBean> responses = openRule.getResponses();
            for (AdResponseBean response : responses) {
                AdResponse adResponse = new AdResponse();
                if(response.getImage()!=null){
                    adResponse.setImage(FileUtils.saveFile(response.getImage().getContent(),logoPath));
                }
                adResponse.setResponse(response.getResponse());
                o.addResponse(adResponse);


                if(response.isCorrect()){
                    corrects.add(adResponse);
                }
            }


        }


        List<BidCategoryMedia> bidCategoryMedias = new ArrayList<BidCategoryMedia>();

        if(ad.getBidCategoryMedias()!=null){
            ad.getBidCategoryMedias().clear();
            bidCategoryMedias = ad.getBidCategoryMedias();
        }else {
            ad.setBidCategoryMedias(bidCategoryMedias);
        }


        DisplayOnMediasBean medias = campaignCommand.getMedias();

        for (Map.Entry<MediaBean, Map<MediaType, List<CategoryPriceBean>>> mediaBeanMapEntry : medias.getDisplays().entrySet()) {
            MediaBean key = mediaBeanMapEntry.getKey();
            Media media = mediaDao.get(key.getId());

            Map<MediaType, List<CategoryPriceBean>> value = mediaBeanMapEntry.getValue();
            for (Map.Entry<MediaType, List<CategoryPriceBean>> mediaTypeListEntry : value.entrySet()) {
                MediaType mediaType = mediaTypeListEntry.getKey();

                List<CategoryPriceBean> categoryPriceBeans = mediaTypeListEntry.getValue();
                for (CategoryPriceBean categoryPriceBean : categoryPriceBeans) {

                    BidCategoryMedia bidCategoryMedia = new BidCategoryMedia();
                    bidCategoryMedia.setMedia(media);
                    bidCategoryMedia.setMediaType(mediaType);
                    bidCategoryMedia.setCategory(categoryDao.findByKey(categoryPriceBean.getCategory()));
                    bidCategoryMedia.setBid(categoryPriceBean.getBid());

                    bidCategoryMedias.add(bidCategoryMedia);
                }

            }

        }

        adDao.save(ad);

    }

    private void deleteNotNeededBrandRule(Ad ad, List<BrandRuleBean> brandRules) {
        List<BrandRule> rules = ad.getRules(BrandRule.class);
        //List<BrandRule> toRemove = new ArrayList<BrandRule>();
        for (BrandRule rule : rules) {
            boolean found = false;
            for (BrandRuleBean brandRule : brandRules) {
                if(rule.getId().equals(brandRule.getId())){
                    found = true;
                    break;
                }
            }
            if(!found){
                rule.setActivated(false);
                //toRemove.add(rule);
                //rules.remove(rule);
            }
        }
        //ad.getRules().removeAll(toRemove);

    }


    private void deleteNotNeededOpenRule(Ad ad, List<OpenRuleBean> openRuleBeans) {
        List<OpenRule> rules = ad.getRules(OpenRule.class);
        //List<OpenRule> toRemove = new ArrayList<OpenRule>();
        for (OpenRule rule : rules) {
            boolean found = false;
            for (OpenRuleBean openRuleBean : openRuleBeans) {
                if(rule.getId().equals(openRuleBean.getId())){
                   found = true;
                    break;
                }
            }

            if(!found){
                rule.setActivated(false);
                //toRemove.add(rule);
            }

        }
        //ad.getRules().removeAll(toRemove);
    }

    private void deleteNotNeededOpenMultiRule(Ad ad, List<OpenMultiRuleBean> openRuleBeans) {
        List<MultiResponseRule> rules = ad.getRules(MultiResponseRule.class);
        //List<MultiResponseRule> toRemove = new ArrayList<MultiResponseRule>();
        for (MultiResponseRule rule : rules) {
            boolean found = false;
            for (OpenMultiRuleBean openRuleBean : openRuleBeans) {
                if(rule.getId().equals(openRuleBean.getId())){
                    found = true;
                    break;
                }
            }

            if(!found){
                rule.setActivated(false);
                //toRemove.add(rule);
            }

        }
        //ad.getRules().removeAll(toRemove);
    }



    private void deleteNotNeededSexyRule(Ad ad, SexRuleBean sexRule) {
        List<SexRule> rules = ad.getRules(SexRule.class);
        ad.getRules().removeAll(rules);
    }

    private void deleteNotNeededAgeRule(Ad ad, AgeRuleBean ageRule) {
        List<AgeRule> rules = ad.getRules(AgeRule.class);
        ad.getRules().removeAll(rules);
    }

    private void deleteNotNeededCityRule(Ad ad, List<CityRuleBean> cityRules) {
        List<CityRule> rules = ad.getRules(CityRule.class);
        ad.getRules().removeAll(rules);
    }

    private void deleteNotNeededCountryRule(Ad ad, List<CountryRuleBean> countryRules) {
        List<CountryRule> rules = ad.getRules(CountryRule.class);
        ad.getRules().removeAll(rules);
    }


    @Transactional
    public CampaignCommand loadCampaign(Long idAd) throws Exception {
        CampaignCommand campaignCommand = new CampaignCommand();

        Ad ad = adDao.get(idAd);

/*
        if(!ad.getBrand().equals(brand)){
            throw new Exception("Not authorize to change this ad");
        }
*/

        InformationCommand information = new InformationCommand();

        information.setInitialAmonut(0.0);

        if (ad instanceof VideoAd) {
            VideoAd videoAd = (VideoAd) ad;
            if(videoAd.getAdFileEncoded()){
                information.setAdFileCommand(new FileCommand(new File(adsPath+ad.getAdFile())));
            }else{
                information.setAdFileCommand(new FileCommand(new File(adsPathTmp+ad.getAdFile())));
            }
        }else{
            information.setAdFileCommand(new FileCommand(new File(adsPath+ad.getAdFile())));
        }



        if(ad instanceof VideoAd){
            information.setDisplayAd(AdDisplay.VIDEO);
        }else{
            information.setDisplayAd(AdDisplay.STATIC);
        }

        information.setDisplayDuration(ad.getDuration()/1000);
        information.setStartDate(ad.getStartDate());
        information.setEndDate(ad.getEndDate());
        information.setId(ad.getId());
        information.setName(ad.getName());

        campaignCommand.setInformation(information);

        AdRulesCommand rules = new AdRulesCommand();
        AdService service = new AdService();


        for (AdRule adRule : ad.getRules()) {
            rules.addRule(adRule);
            if(adRule instanceof fr.k2i.adbeback.core.business.ad.rule.AdService && ((fr.k2i.adbeback.core.business.ad.rule.AdService) adRule).getActivated().equals(true)){
                //boolean used = adGameDao.RuleIsUsed((fr.k2i.adbeback.core.business.ad.rule.AdService) adRule);
                service.addService(adRule,logoPath);
            }
        }

        campaignCommand.setRules(rules);
        campaignCommand.setAdServices(service);


        campaignCommand.setBrand(new BrandBean(ad.getBrand()));


        DisplayOnMediasBean medias = new DisplayOnMediasBean();
        HashMap<MediaBean, Map<MediaType, List<CategoryPriceBean>>> displays = new HashMap<MediaBean, Map<MediaType, List<CategoryPriceBean>>>();


        List<BidCategoryMedia> bidCategoryMedias = ad.getBidCategoryMedias();
        for (BidCategoryMedia bidCategoryMedia : bidCategoryMedias) {
            MediaBean mediaBean = new MediaBean(bidCategoryMedia.getMedia().getId(), bidCategoryMedia.getMedia().getName());
            Map<MediaType, List<CategoryPriceBean>> mediaTypeListMap = displays.get(mediaBean);
            if(mediaTypeListMap==null){
                mediaTypeListMap = new HashMap<MediaType, List<CategoryPriceBean>>();
                displays.put(mediaBean,mediaTypeListMap);
            }

            List<CategoryPriceBean> categoryPriceBeans = mediaTypeListMap.get(bidCategoryMedia.getMediaType());
            if(categoryPriceBeans==null){
                categoryPriceBeans = new ArrayList<CategoryPriceBean>();
                mediaTypeListMap.put(bidCategoryMedia.getMediaType(),categoryPriceBeans);
            }

            CategoryPriceBean categoryPriceBean = new CategoryPriceBean();
            CategoryPrice categoryPrice = mediaDao.findCategoryPrice(bidCategoryMedia.getMedia().getId(),bidCategoryMedia.getMediaType(),bidCategoryMedia.getCategory().getKey());
            categoryPriceBean.setPrice(categoryPrice.getMinPrice());
            categoryPriceBean.setMediaType(bidCategoryMedia.getMediaType());
            categoryPriceBean.setCategory(bidCategoryMedia.getCategory().getKey());
            categoryPriceBean.setBid(bidCategoryMedia.getBid());
            categoryPriceBean.setMediaId(bidCategoryMedia.getMedia().getId());

            categoryPriceBeans.add(categoryPriceBean);
        }

        medias.setDisplays(displays);

        campaignCommand.setMedias(medias);


        return campaignCommand;
    }

    @Transactional
    public List<BrandBean> getBrandInChargeOfCurrentUser() {
        try {
            AgencyUser currentUser = (AgencyUser) getCurrentUser();
            List<BrandBean> brands = new ArrayList<BrandBean>();

            if(currentUser.getRoles().contains(roleDao.getRoleByName(Constants.AGENCY_ADMIN_ROLE))){
                //show all agency Brand
                Agency agency = currentUser.getAgency();
                List<Brand> agencyBrands = agencyDao.findAllBrandsForAgency(agency);

                for (Brand brand : agencyBrands) {
                    brands.add(new BrandBean(brand));
                }

            }else{
                for (Brand brand : currentUser.getInChargeOf()) {
                    brands.add(new BrandBean(brand));
                }
            }

            return brands;
        } catch (Exception e) {
            return null;
        }


    }


    public enum ConfirmationRegistration {
        TIME_OUT,OK,KO;
    }















}
