package fr.k2i.adbeback.webapp.facade;

import fr.k2i.adbeback.application.services.mail.IMailEngine;
import fr.k2i.adbeback.application.services.mail.dto.Email;
import fr.k2i.adbeback.application.services.mail.exception.SendException;
import fr.k2i.adbeback.core.business.Constants;
import fr.k2i.adbeback.core.business.ad.*;
import fr.k2i.adbeback.core.business.ad.rule.*;
import fr.k2i.adbeback.core.business.otp.OTPBrandSecurityConfirm;
import fr.k2i.adbeback.core.business.player.Address;
import fr.k2i.adbeback.core.business.player.Role;
import fr.k2i.adbeback.crypto.DESCryptoService;
import fr.k2i.adbeback.dao.IAdDao;
import fr.k2i.adbeback.dao.jpa.*;
import fr.k2i.adbeback.logger.LogHelper;
import fr.k2i.adbeback.util.PhoneNumberUtils;
import fr.k2i.adbeback.webapp.bean.*;
import fr.k2i.adbeback.webapp.bean.AdService;
import fr.k2i.adbeback.webapp.bean.adservice.AdResponseBean;
import fr.k2i.adbeback.webapp.bean.adservice.BrandRuleBean;
import fr.k2i.adbeback.webapp.bean.adservice.OpenRuleBean;
import fr.k2i.adbeback.webapp.bean.adservice.ProductRuleBean;
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
import org.springframework.web.multipart.MultipartFile;

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
    private BrandRepository brandRepository;

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private IMailEngine mailEngine;


    @Autowired
    private MessageSource messageSource;

    @Autowired
    private DESCryptoService desCryptoService;

    @Autowired
    private OTPSecurityRepository otpSecurityRepository;


    @Value("${brand.base.confirm.url}")
    private String baseUrl;
    
    
    @Autowired
    private RoleDao roleDao;


    @Autowired
    private AdRepository adRepository;

    @Autowired
    private UserFacade userFacade;

    @Value("${addonf.price.byBrand:0.30}")
    private Double showBrandPrice;
    @Value("${addonf.price.byOpen:0.30}")
    private Double showOpenPrice;

    @Value("${addonf.ads.location}")
    private String adsPath;

    @Value("${addonf.logo.location}")
    private String logoPath;


    @Transactional
    public void enrollBrand(EnrollBrandCommand command,Locale locale){
        Brand brand = new Brand();
        brand.setPassword(passwordEncoder.encodePassword(command.getPassword(), null));
        brand.setName(command.getName());
        brand.setSiret(command.getSiret());

        AddressBean addressBean = command.getAddress();
        Address address =new Address();
        address.setAddress(addressBean.getStreet());
        address.setCity(cityRepository.findByZipcodeAndCityAndCountry_Code(addressBean.getZipcode(), addressBean.getCity(), addressBean.getCountry()));
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

        brandRepository.save(brand);

        String url = baseUrl + desCryptoService.generateOtpConfirm(command.getName() + "|" + contactBean.getEmail(), brand, 48);

        Map<String, Object> model = new HashMap<String, Object>();

        model.put("url", url);
        model.put("name", contactBean.getFirstname() + " "+contactBean.getLastname());
        Email email = Email.builder()
                                        .subject(messageSource.getMessage("mail.enrolled.annonceur", new Object[]{}, locale))
                                        .model(model)
                                        .content("annonceur_enrolled")
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
        List<Ad> ads = adRepository.findByBrand(brand);

        for (Ad ad : ads) {
            res.add(new AdBean(ad));
        }
        return res;
    }

    @Transactional
    public Page<AdBean> getAllForConnectedUser(Pageable pageable) throws Exception {
        Brand brand = userFacade.getCurrentUser();

        List<AdBean> res = new ArrayList<AdBean>();
        Page<Ad> ads = adRepository.findByBrand(brand,pageable);

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
        if (AdDisplay.VIDEO.equals(information.getDisplayAd())){
            ad = new VideoAd();
            //video
        }else{
            //static
            ad = new StaticAd();
            ad.setDuration(information.getDisplayDuration()*1000);
        }


        ad.setAdFile(saveFile(information.getAdFileCommand().getContent(),adsPath));
        ad.setBrand(brand);
        ad.setEndDate(information.getEndDate());
        ad.setInitialAmount(information.getInitialAmonut());
        ad.setName(information.getName());
        ad.setStartDate(information.getStartDate());

        AmountRule amountRule = new AmountRule();
        amountRule.setAmount(information.getInitialAmonut());
        amountRule.setInitialAmount(information.getInitialAmonut());
        amountRule.setInserted(new Date());
        ad.addRule(amountRule);

        AdRulesCommand restrictionRules = campaignCommand.getRules();

        for (CountryRule countryRule : restrictionRules.getCountryRules()) {
            CountryRule c = new CountryRule();
            c.setCountry(countryRepository.findByCode(countryRule.getCountry().getCode()));
            ad.addRule(c);
        }


        for (CityRule cityRule : restrictionRules.getCityRules()) {
            CityRule c = new CityRule();
            c.setAround(cityRule.getAround());
            c.setCity(cityRepository.findOne(cityRule.getCity().getId()));
            ad.addRule(c);
        }

        SexRule sexRule = restrictionRules.getSexRule();
        if(sexRule!=null){
            ad.addRule(sexRule);
        }

        AgeRule ageRule = restrictionRules.getAgeRule();
        if(ageRule!=null){
            ad.addRule(ageRule);
        }


        AdService adServices = campaignCommand.getAdServices();

        List<BrandRuleBean> brandRules = adServices.getBrandRules();

        for (BrandRuleBean brandRule : brandRules) {
            BrandRule b = new BrandRule();
            b.setAd(ad);
            b.setStartDate(brandRule.getStartDate());
            b.setEndDate(brandRule.getEndDate());
            b.setPrice(showBrandPrice);
            b.setQuestion("Quel est le Logo de la publicité ?");
            b.setMaxDisplayByUser(b.getMaxDisplayByUser());

            List<Brand> noDisplayWith = brandRule.getNoDisplayWith();
            for (Brand bNo : noDisplayWith) {
                b.addNoDisplayWith(brandRepository.findOne(bNo.getId()));
            }
            ad.addRule(b);
        }


        List<OpenRuleBean> openRules = adServices.getOpenRules();

        for (OpenRuleBean openRule : openRules) {
            OpenRule o = new OpenRule();
            o.setAd(ad);
            o.setStartDate(openRule.getStartDate());
            o.setEndDate(openRule.getEndDate());
            o.setQuestion(openRule.getQuestion());
            o.setPrice(showOpenPrice);
            o.setMaxDisplayByUser(openRule.getMaxDisplayByUser());

            List<AdResponseBean> responses = openRule.getResponses();
            for (AdResponseBean response : responses) {
                AdResponse adResponse = new AdResponse();
                adResponse.setImage(saveFile(response.getImage().getContent(),logoPath));
                adResponse.setResponse(response.getResponse());
                o.addResponse(adResponse);
                if(response.isCorrect()){
                    o.setCorrect(adResponse);
                }
            }

            ad.addRule(o);
        }

        adRepository.save(ad);

    }


    /**
     *
     * @param content
     * @return
     * @throws java.io.IOException
     */
    private String saveFile(byte[] content,String base) throws IOException {
        return saveFile(content, UUID.randomUUID().toString(),base);
    }


    /**
     *
     * @param content
     * @param path
     * @return
     * @throws IOException
     */
    private String saveFile(byte[] content, String path,String base)  throws IOException{
        String completePath = base + File.separator + path;
        FileOutputStream fos = new FileOutputStream(completePath);
        fos.write(content);
        fos.close();
        return path;
    }



    public enum ConfirmationRegistration {
        TIME_OUT,OK,KO;
    }

    @Transactional
    public ConfirmationRegistration confirmRegistration(String email, String name, String code) {
        Brand brand = brandRepository.findByEmail(email);
        OTPBrandSecurityConfirm otp = otpSecurityRepository.findByBrand(brand);
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
