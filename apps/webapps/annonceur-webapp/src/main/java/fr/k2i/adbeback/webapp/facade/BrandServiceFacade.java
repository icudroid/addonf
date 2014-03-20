package fr.k2i.adbeback.webapp.facade;

import fr.k2i.adbeback.application.services.mail.IMailEngine;
import fr.k2i.adbeback.application.services.mail.dto.Email;
import fr.k2i.adbeback.application.services.mail.exception.SendException;
import fr.k2i.adbeback.core.business.Constants;
import fr.k2i.adbeback.core.business.ad.*;
import fr.k2i.adbeback.core.business.ad.rule.*;
import fr.k2i.adbeback.core.business.otp.OTPBrandSecurityConfirm;
import fr.k2i.adbeback.core.business.player.Address;
import fr.k2i.adbeback.crypto.DESCryptoService;
import fr.k2i.adbeback.dao.*;
import fr.k2i.adbeback.dao.jpa.*;
import fr.k2i.adbeback.logger.LogHelper;
import fr.k2i.adbeback.webapp.util.PhoneNumberUtils;
import fr.k2i.adbeback.webapp.bean.*;
import fr.k2i.adbeback.webapp.bean.AdService;
import fr.k2i.adbeback.webapp.bean.adservice.AdResponseBean;
import fr.k2i.adbeback.webapp.bean.adservice.BrandRuleBean;
import fr.k2i.adbeback.webapp.bean.adservice.OpenRuleBean;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: dev
 * Date: 22/12/13
 * Time: 15:45
 * To change this template use File | Settings | File Templates.
 */
@Component
public class BrandServiceFacade {
    private static Logger logger = LogHelper.getLogger(BrandServiceFacade.class);

    @Autowired
    private IBrandDao brandDao;

    @Autowired
    private ICityDao cityDao;

    @Autowired
    private ICountryDao countryDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private IMailEngine mailEngine;


    @Autowired
    private MessageSource messageSource;

    @Autowired
    private DESCryptoService desCryptoService;

    @Autowired
    private IOTPSecurityDao securityDao;


    @Value("${brand.base.confirm.url}")
    private String baseUrl;
    
    
    @Autowired
    private RoleDao roleDao;


    @Autowired
    private IAdDao adDao;

    @Autowired
    private UserFacade userFacade;

    @Value("${addonf.price.byBrand:0.30}")
    private Double showBrandPrice;
    @Value("${addonf.price.byOpen:0.30}")
    private Double showOpenPrice;

    @Value("${addonf.ads.location}")
    private String adsPath;

    @Value("${addonf.ads.tmp.location}")
    private String adsPathTmp;

    @Value("${addonf.logo.location}")
    private String logoPath;


    @Autowired
    private IAdGameDao adGameDao;

    @Transactional
    public void enrollBrand(EnrollBrandCommand command,Locale locale){
        Brand brand = new Brand();
        brand.setPassword(passwordEncoder.encodePassword(command.getPassword(), null));
        brand.setName(command.getName());
        brand.setSiret(command.getSiret());

        AddressBean addressBean = command.getAddress();
        Address address =new Address();
        address.setAddress(addressBean.getStreet());
        address.setCity(cityDao.findByZipcodeAndCityAndCountry_Code(addressBean.getZipcode(), addressBean.getCity(), addressBean.getCountry()));
        brand.setAddress(address);

        Contact contact = new Contact();
        ContactBean contactBean = command.getContact();
        contact.setEmail(contactBean.getEmail());
        contact.setFirstname(contactBean.getFirstname());
        contact.setLastname(contactBean.getLastname());
        contact.setSalutation(contactBean.getSex());

        if(PhoneNumberUtils.isValidFixedPhoneNumber(contactBean.getPhone(),addressBean.getCountry())){
            contact.setPhone(contactBean.getPhone());
        }else{
            contact.setMobilePhone(contactBean.getPhone());
        }

        brand.setEmail(contact.getEmail());
        brand.setMain(contact);

        brand.setEnabled(false);
        brand.setAccountExpired(false);
        brand.setAccountLocked(false);
        brand.setCredentialsExpired(false);
        brand.setVersion(0);

        brand.addRole(roleDao.getRoleByName(Constants.ANNONCEUR_ROLE));

        brandDao.save(brand);

        String url = baseUrl + desCryptoService.generateOtpConfirm(command.getName() + "|" + contactBean.getEmail(), brand, 48);

        Map<String, Object> model = new HashMap<String, Object>();

        model.put("url", url);
        model.put("name", contactBean.getFirstname() + " "+contactBean.getLastname());
        Email email = Email.builder()
                                        .subject(messageSource.getMessage("mail.enrolled.annonceur", new Object[]{}, locale))
                                        .model(model)
                                        .content("email/annonceur_enrolled")
                                        .recipients(contactBean.getEmail())
                                        .noAttachements()
                                        .build();
        try {
            mailEngine.sendMessage(email,locale);
        } catch (SendException e) {
            logger.error("error sending email", e);
        }

    }

    @Transactional
    public List<AdBean> getAdsForConnectedUser() throws Exception {
        Brand brand = userFacade.getCurrentUser();

        List<AdBean> res = new ArrayList<AdBean>();
        List<Ad> ads = adDao.findByBrand(brand);

        for (Ad ad : ads) {
            res.add(new AdBean(ad));
        }
        return res;
    }

    @Transactional
    public Page<AdBean> getAllForConnectedUser(Pageable pageable) throws Exception {
        Brand brand = userFacade.getCurrentUser();

        List<AdBean> res = new ArrayList<AdBean>();
        Page<Ad> ads = adDao.findByBrand(brand,pageable);

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
        Brand brand = userFacade.getCurrentUser();
        Ad ad = null;


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
            ad.setAdFile(FileUtils.saveFile(information.getAdFileCommand().getContent(),adsPathTmp));
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
            }

            b.setAd(ad);
            b.setStartDate(brandRule.getStartDate());
            b.setEndDate(brandRule.getEndDate());
            b.setPrice(showBrandPrice);
            b.setQuestion("Quel est le Logo de la publicité ?");
            b.setMaxDisplayByUser(b.getMaxDisplayByUser());

            b.getNoDisplayWith().clear();
            List<BrandBean> noDisplayWith = brandRule.getNoDisplayWith();
            for (BrandBean bNo : noDisplayWith) {
                b.addNoDisplayWith(brandDao.get(bNo.getId()));
            }
            ad.addRule(b);
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
            }


            o.setAd(ad);
            o.setStartDate(openRule.getStartDate());
            o.setEndDate(openRule.getEndDate());
            o.setQuestion(openRule.getQuestion());
            o.setPrice(showOpenPrice);
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

            ad.addRule(o);
        }

        adDao.save(ad);

    }

    private void deleteNotNeededBrandRule(Ad ad, List<BrandRuleBean> brandRules) {
        List<BrandRule> rules = ad.getRules(BrandRule.class);
        for (BrandRule rule : rules) {
            boolean found = false;
            for (BrandRuleBean brandRule : brandRules) {
                if(rule.getId().equals(brandRule.getId())){
                    found = true;
                    break;
                }
            }
            if(!found){
                rules.remove(rule);
            }
        }
    }


    private void deleteNotNeededOpenRule(Ad ad, List<OpenRuleBean> openRuleBeans) {
        List<OpenRule> rules = ad.getRules(OpenRule.class);
        for (OpenRule rule : rules) {
            boolean found = false;
            for (OpenRuleBean openRuleBean : openRuleBeans) {
                if(rule.getId().equals(openRuleBean.getId())){
                    found = true;
                    break;
                }
            }
            if(!found){
                rules.remove(rule);
            }
        }
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

        Brand brand = userFacade.getCurrentUser();
        Ad ad = adDao.get(idAd);

        if(!ad.getBrand().equals(brand)){
            throw new Exception("Not authorize to change this ad");
        }

        InformationCommand information = new InformationCommand();

        information.setInitialAmonut(0.0);


        information.setAdFileCommand(new FileCommand(new File(adsPath+ad.getAdFile())));

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
            if(adRule instanceof fr.k2i.adbeback.core.business.ad.rule.AdService){
                boolean used = adGameDao.isGeneratedWithRule((fr.k2i.adbeback.core.business.ad.rule.AdService) adRule);
                service.addService(adRule,logoPath,used);
            }
        }

        campaignCommand.setRules(rules);
        campaignCommand.setAdServices(service);



        return campaignCommand;
    }


    public enum ConfirmationRegistration {
        TIME_OUT,OK,KO;
    }

    @Transactional
    public ConfirmationRegistration confirmRegistration(String email, String name, String code) {
        Brand brand = brandDao.findByEmail(email);
        OTPBrandSecurityConfirm otp = securityDao.findByBrand(brand);
        if(otp == null){
            return ConfirmationRegistration.KO;
        }else if(otp.getKey().equals(code)){
            Date now = new Date();
            if(otp.getExpirationDate().before(now)){
                return ConfirmationRegistration.TIME_OUT;
            }else{
                brand.setEnabled(true);
                return ConfirmationRegistration.OK;
            }
        }else{
            return ConfirmationRegistration.KO;
        }
    }
}
