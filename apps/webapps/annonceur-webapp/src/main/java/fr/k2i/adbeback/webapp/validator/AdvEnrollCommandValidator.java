package fr.k2i.adbeback.webapp.validator;

import edu.vt.middleware.password.*;
import fr.k2i.adbeback.core.business.user.MediaType;
import fr.k2i.adbeback.dao.IAgencyDao;
import fr.k2i.adbeback.dao.IBrandDao;
import fr.k2i.adbeback.dao.IWebUserDao;
import fr.k2i.adbeback.logger.LogHelper;
import fr.k2i.adbeback.webapp.bean.AddressBean;
import fr.k2i.adbeback.webapp.bean.enroll.InformationCommand;
import fr.k2i.adbeback.webapp.bean.enroll.adv.AdvEnrollCommand;
import fr.k2i.adbeback.webapp.bean.enroll.adv.AdvUserBean;
import fr.k2i.adbeback.webapp.bean.enroll.adv.CustomerTargetCommand;
import fr.k2i.adbeback.webapp.bean.enroll.adv.CustomizeCommand;
import fr.k2i.adbeback.webapp.bean.enroll.agency.AgencyRole;
import fr.k2i.adbeback.webapp.bean.enroll.agency.AgencyUserBean;
import fr.k2i.adbeback.webapp.util.PhoneNumberUtils;
import fr.k2i.adbeback.webapp.util.SirenSiretValidator;
import fr.k2i.adbeback.webapp.util.ValidatorHelper;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;

import javax.annotation.Resource;
import javax.validation.ConstraintViolation;
import javax.validation.groups.Default;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: dev
 * Date: 21/12/13
 * Time: 12:01
 * To change this template use File | Settings | File Templates.
 */

@Component
public class AdvEnrollCommandValidator {

    private static Logger logger = LogHelper.getLogger(AdvEnrollCommandValidator.class);

    @Autowired
    private javax.validation.Validator beanValidator;

    @Autowired
    private SirenSiretValidator sirenSiretValidator;


    @Autowired
    private ValidatorHelper validatorHelper;


    @Autowired
    private IBrandDao brandDao;

    @Autowired
    private IAgencyDao agencyDao;

    @Resource(name = "annonceurUserDao" )
    private IWebUserDao webUserDao;


    public void validateAdv(Object o, Errors errors) {
        AdvEnrollCommand command = (AdvEnrollCommand) o;
        InformationCommand info = command.getInfo();
        Set<ConstraintViolation<InformationCommand>> validate = beanValidator.validate(info, Default.class);
        validatorHelper.importBeanValidationErrors(validate,errors,"info.");


        if(!StringUtils.isEmpty(info.getName())){
            if(agencyDao.findByName(info.getName())!=null){
                errors.rejectValue("info.name","exist");
            }
        }

        AddressBean address = info.getAddress();
        if(StringUtils.isEmpty(address.getCity())){
            errors.rejectValue("info.address.city","required");
        }

        if(StringUtils.isEmpty(address.getCountry())){
            errors.rejectValue("info.address.country","required");
        }

        if(StringUtils.isEmpty(address.getStreet())){
            errors.rejectValue("info.address.street","required");
        }

        if(StringUtils.isEmpty(address.getZipcode())){
            errors.rejectValue("info.address.zipcode","required");
        }



        if(StringUtils.isEmpty(info.getSiret())){
            errors.rejectValue("info.siret","required");
        }else{
            if(brandDao.findBySiret(info.getSiret())!=null){
                errors.rejectValue("info.siret","exist");
            }
            sirenSiretValidator.validateSiret(info.getSiret(),errors,"info.siret");
        }

        if(StringUtils.isEmpty(info.getPhone())){
            errors.rejectValue("info.phone","required");
        }

        if(!StringUtils.isEmpty(info.getPhone()) && !PhoneNumberUtils.isValidPhoneNumber(info.getPhone(), address.getCountry())){
            errors.rejectValue("info.phone","wrong");
        }

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        if(StringUtils.isEmpty(info.getCreationDate())){
            errors.rejectValue("info.creationDate","required");
        }else{
            Date creationDate = null;
            try {
                creationDate = sdf.parse(info.getCreationDate());
                Date now = new Date();

                if(creationDate.after(now)){
                    errors.rejectValue("info.creationDate","wrong");
                }
            } catch (ParseException e) {
                errors.rejectValue("info.creationDate","bad.format");
            }
        }



    }

    public void validateAdminUser(Object o, Errors errors) {
        AdvEnrollCommand command = (AdvEnrollCommand) o;
        AdvUserBean admin = command.getUser();

        Set<ConstraintViolation<AdvUserBean>> validate = beanValidator.validate(admin, Default.class);
        validatorHelper.importBeanValidationErrors(validate,errors,"user.");


        if(StringUtils.isEmpty(admin.getEmail())){
            errors.rejectValue("user.email","required");
        }else{
            UserDetails user = webUserDao.findByUsername(admin.getEmail());
            if(user!=null){
                errors.rejectValue("user.email","exist");
            }
        }

        if(StringUtils.isEmpty(admin.getEmailConfirm())){
            errors.rejectValue("user.emailConfirm","required");
        }

        if(!StringUtils.isEmpty(admin.getEmail()) && !StringUtils.isEmpty(admin.getEmailConfirm()) &&
                !admin.getEmail().equals(admin.getEmailConfirm())){
            errors.rejectValue("user.emailConfirm","different");
        }


        if(StringUtils.isEmpty(admin.getPassword())){
            errors.rejectValue("user.password","required");
        }

        if(StringUtils.isEmpty(admin.getPasswordConfirm())){
            errors.rejectValue("user.passwordConfirm","required");
        }else if(!StringUtils.isEmpty(admin.getPassword()) && !StringUtils.isEmpty(admin.getPasswordConfirm()) &&
                !admin.getPassword().equals(admin.getPasswordConfirm())){
            errors.rejectValue("user.passwordConfirm","different");
        }else{



            // password must be between 8 and 16 chars long
            LengthRule lengthRule = new LengthRule(8, 16);

            // don't allow whitespace
            WhitespaceRule whitespaceRule = new WhitespaceRule();

            // control allowed characters
            CharacterCharacteristicsRule charRule = new CharacterCharacteristicsRule();
            // require at least 1 digit in passwords
            charRule.getRules().add(new DigitCharacterRule(1));
            // require at least 1 non-alphanumeric char
            charRule.getRules().add(new NonAlphanumericCharacterRule(1));
            // require at least 1 upper case char
            charRule.getRules().add(new UppercaseCharacterRule(1));
            // require at least 1 lower case char
            charRule.getRules().add(new LowercaseCharacterRule(1));
            // require at least 3 of the previous rules be met
            charRule.setNumberOfCharacteristics(3);

            // don't allow alphabetical sequences
            AlphabeticalSequenceRule alphaSeqRule = new AlphabeticalSequenceRule();

            // don't allow 4 repeat characters
            RepeatCharacterRegexRule repeatRule = new RepeatCharacterRegexRule(4);

            // group all rules together in a List
            List<Rule> ruleList = new ArrayList<Rule>();
            ruleList.add(lengthRule);
            ruleList.add(whitespaceRule);
            ruleList.add(charRule);
            ruleList.add(alphaSeqRule);
            ruleList.add(repeatRule);


            PasswordValidator validator = new PasswordValidator(ruleList);
            PasswordData passwordData = new PasswordData(new Password(admin.getPassword()));

            RuleResult result = validator.validate(passwordData);
            if (!result.isValid()) {
                errors.rejectValue("user.password","not.enough.strong");
            }

        }

        if(StringUtils.isEmpty(admin.getFirstname())){
            errors.rejectValue("user.firstname","required");
        }

        if(StringUtils.isEmpty(admin.getLastname())){
            errors.rejectValue("user.lastname","required");
        }

        if(StringUtils.isEmpty(admin.getPhone())){
            errors.rejectValue("user.phone","required");
        }

        if(!StringUtils.isEmpty(admin.getPhone()) && !PhoneNumberUtils.isValidPhoneNumber(admin.getPhone(), command.getInfo().getAddress().getCountry())){
            errors.rejectValue("user.phone","wrong");
        }


    }


    public void validateCustomize(Object o, Errors errors) {
        AdvEnrollCommand command = (AdvEnrollCommand) o;

        CustomizeCommand customize = command.getCustomize();
        Long sectorId = customize.getSectorId();
        if(sectorId==null || sectorId ==-1){
            errors.rejectValue("customize.sectorId","required");
        }

        List<MediaType> targetMedia = customize.getTargetMedia();
        if(targetMedia==null || targetMedia.isEmpty()){
            errors.rejectValue("customize.targetMedia","empty");
        }
    }


    public void validateCustomerTarget(Object o, Errors errors) {
        AdvEnrollCommand command = (AdvEnrollCommand) o;

        CustomerTargetCommand currentCustomerTarget = command.getCustomize().getCurrentCustomerTarget();

         if(currentCustomerTarget.getAgeGroup()==null){
             errors.rejectValue("customize.currentCustomerTarget.ageGroup","empty");
         }
        if(currentCustomerTarget.getSex()==null){
            errors.rejectValue("customize.currentCustomerTarget.sex","empty");
        }


    }
 }
