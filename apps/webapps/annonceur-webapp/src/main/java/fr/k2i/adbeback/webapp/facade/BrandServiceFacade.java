package fr.k2i.adbeback.webapp.facade;

import fr.k2i.adbeback.application.services.mail.IMailEngine;
import fr.k2i.adbeback.application.services.mail.dto.Email;
import fr.k2i.adbeback.application.services.mail.exception.SendException;
import fr.k2i.adbeback.core.business.Constants;
import fr.k2i.adbeback.core.business.ad.Ad;
import fr.k2i.adbeback.core.business.ad.Brand;
import fr.k2i.adbeback.core.business.ad.Contact;
import fr.k2i.adbeback.core.business.otp.OTPBrandSecurityConfirm;
import fr.k2i.adbeback.core.business.player.Address;
import fr.k2i.adbeback.core.business.player.Role;
import fr.k2i.adbeback.crypto.DESCryptoService;
import fr.k2i.adbeback.dao.IAdDao;
import fr.k2i.adbeback.dao.jpa.*;
import fr.k2i.adbeback.logger.LogHelper;
import fr.k2i.adbeback.util.PhoneNumberUtils;
import fr.k2i.adbeback.webapp.bean.*;
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
