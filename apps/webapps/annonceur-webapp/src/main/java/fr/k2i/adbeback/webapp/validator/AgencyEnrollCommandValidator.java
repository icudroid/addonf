package fr.k2i.adbeback.webapp.validator;

import edu.vt.middleware.password.*;
import fr.k2i.adbeback.dao.IAgencyDao;
import fr.k2i.adbeback.dao.IBrandDao;
import fr.k2i.adbeback.dao.IWebUserDao;
import fr.k2i.adbeback.dao.jpa.AnnonceurUserDao;
import fr.k2i.adbeback.logger.LogHelper;
import fr.k2i.adbeback.webapp.bean.AddressBean;
import fr.k2i.adbeback.webapp.bean.ContactBean;
import fr.k2i.adbeback.webapp.bean.EnrollBrandCommand;
import fr.k2i.adbeback.webapp.bean.enroll.AgencyEnrollCommand;
import fr.k2i.adbeback.webapp.bean.enroll.AgencyInformationCommand;
import fr.k2i.adbeback.webapp.bean.enroll.AgencyUserBean;
import fr.k2i.adbeback.webapp.util.PhoneNumberUtils;
import fr.k2i.adbeback.webapp.util.SirenSiretValidator;
import fr.k2i.adbeback.webapp.util.ValidatorHelper;
import org.joda.time.LocalTime;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.annotation.Resource;
import javax.validation.ConstraintViolation;
import javax.validation.groups.Default;
import java.io.FileInputStream;
import java.io.IOException;
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
public class AgencyEnrollCommandValidator{

    private static Logger logger = LogHelper.getLogger(AgencyEnrollCommandValidator.class);

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


    public void validateAgency(Object o, Errors errors) {
        AgencyEnrollCommand command = (AgencyEnrollCommand) o;
        AgencyInformationCommand info = command.getInfo();
        Set<ConstraintViolation<AgencyInformationCommand>> validate = beanValidator.validate(info, Default.class);
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
        AgencyEnrollCommand command = (AgencyEnrollCommand) o;
        AgencyUserBean admin = command.getUsers().getCurrent();

        Set<ConstraintViolation<AgencyUserBean>> validate = beanValidator.validate(admin, Default.class);
        validatorHelper.importBeanValidationErrors(validate,errors,"users.current.");


        if(StringUtils.isEmpty(admin.getEmail())){
            errors.rejectValue("users.current.email","required");
        }else{
            UserDetails user = webUserDao.findByUsername(admin.getEmail());
            if(user!=null){
                errors.rejectValue("users.current.email","exist");
            }
        }

        if(StringUtils.isEmpty(admin.getEmailConfirm())){
            errors.rejectValue("users.current.emailConfirm","required");
        }

        if(!StringUtils.isEmpty(admin.getEmail()) && !StringUtils.isEmpty(admin.getEmailConfirm()) &&
                !admin.getEmail().equals(admin.getEmailConfirm())){
            errors.rejectValue("users.current.emailConfirm","different");
        }


        if(StringUtils.isEmpty(admin.getPassword())){
            errors.rejectValue("users.current.password","required");
        }

        if(StringUtils.isEmpty(admin.getPasswordConfirm())){
            errors.rejectValue("users.current.passwordConfirm","required");
        }else if(!StringUtils.isEmpty(admin.getPassword()) && !StringUtils.isEmpty(admin.getPasswordConfirm()) &&
                !admin.getPassword().equals(admin.getPasswordConfirm())){
            errors.rejectValue("users.current.passwordConfirm","different");
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

            // don't allow numerical sequences of length 3
            //NumericalSequenceRule numSeqRule = new NumericalSequenceRule(3);

            // don't allow qwerty sequences
            QwertySequenceRule qwertySeqRule = new QwertySequenceRule();

            // don't allow 4 repeat characters
            RepeatCharacterRegexRule repeatRule = new RepeatCharacterRegexRule(4);

            // group all rules together in a List
            List<Rule> ruleList = new ArrayList<Rule>();
            ruleList.add(lengthRule);
            ruleList.add(whitespaceRule);
            ruleList.add(charRule);
            ruleList.add(alphaSeqRule);
            //ruleList.add(numSeqRule);
            ruleList.add(qwertySeqRule);
            ruleList.add(repeatRule);


            PasswordValidator validator = new PasswordValidator(ruleList);
            PasswordData passwordData = new PasswordData(new Password(admin.getPassword()));

            RuleResult result = validator.validate(passwordData);
            if (!result.isValid()) {
                errors.rejectValue("users.current.password","not.enough.strong");
            }

        }

        if(StringUtils.isEmpty(admin.getFirstname())){
            errors.rejectValue("users.current.firstname","required");
        }

        if(StringUtils.isEmpty(admin.getLastname())){
            errors.rejectValue("users.current.lastname","required");
        }

        if(StringUtils.isEmpty(admin.getPhone())){
            errors.rejectValue("users.current.phone","required");
        }

        if(!StringUtils.isEmpty(admin.getPhone()) && !PhoneNumberUtils.isValidPhoneNumber(admin.getPhone(), command.getInfo().getAddress().getCountry())){
            errors.rejectValue("users.current.phone","wrong");
        }


    }



    public void validateToto(Object o, Errors errors) {
        AgencyEnrollCommand command = (AgencyEnrollCommand) o;
        Set<ConstraintViolation<AgencyEnrollCommand>> validateEnrollBrandCommand = beanValidator.validate(command, Default.class);


/*
        Set<ConstraintViolation<ContactBean>> validateContact = beanValidator.validate(command.getContact(), Default.class);

        validatorHelper.importBeanValidationErrors(validateEnrollBrandCommand,errors);
        validatorHelper.importBeanValidationErrors(validateContact, errors);




        if(StringUtils.isEmpty(command.getName())){
                    errors.rejectValue("name","required");
        }else{
            if(brandDao.findByName(command.getName())!=null){
                errors.rejectValue("name","exist");
            }
        }

        if(StringUtils.isEmpty(command.getPassword())){
            errors.rejectValue("password","required");
        }

        if(StringUtils.isEmpty(command.getConfirmPassword())){
            errors.rejectValue("confirmPassword","required");
        }

        if(!StringUtils.isEmpty(command.getPassword()) && !StringUtils.isEmpty(command.getConfirmPassword()) &&
                !command.getPassword().equals(command.getConfirmPassword())){
            errors.rejectValue("confirmPassword","different");
        }

        AddressBean address = command.getAddress();
        if(StringUtils.isEmpty(address.getCity())){
            errors.rejectValue("address.city","required");
        }

        if(StringUtils.isEmpty(address.getCountry())){
            errors.rejectValue("address.country","required");
        }

        if(StringUtils.isEmpty(address.getStreet())){
            errors.rejectValue("address.street","required");
        }

        if(StringUtils.isEmpty(address.getZipcode())){
            errors.rejectValue("address.zipcode","required");
        }

        ContactBean contact = command.getContact();


        if(StringUtils.isEmpty(contact.getEmail())){
            errors.rejectValue("contact.email","required");
        }else{
            if(brandDao.findByEmail(contact.getEmail())!=null){
                errors.rejectValue("contact.email","exist");
            }
        }

        if(StringUtils.isEmpty(contact.getEmailConfirm())){
            errors.rejectValue("contact.emailConfirm","required");
        }

        if(!StringUtils.isEmpty(contact.getEmail()) && !StringUtils.isEmpty(contact.getEmailConfirm()) &&
                !contact.getEmail().equals(contact.getEmailConfirm())){
            errors.rejectValue("contact.emailConfirm","different");
        }

        if(StringUtils.isEmpty(contact.getFirstname())){
            errors.rejectValue("contact.firstname","required");
        }

        if(StringUtils.isEmpty(contact.getLastname())){
            errors.rejectValue("contact.lastname","required");
        }

        if(StringUtils.isEmpty(contact.getPhone())){
            errors.rejectValue("contact.phone","required");
        }

        if(!StringUtils.isEmpty(contact.getPhone()) && !PhoneNumberUtils.isValidPhoneNumber(contact.getPhone(), address.getCountry())){
            errors.rejectValue("contact.phone","wrong");
        }

        if(StringUtils.isEmpty(command.getSiret())){
            errors.rejectValue("siret","required");
        }else{
            if(brandDao.findBySiret(command.getSiret())!=null){
                errors.rejectValue("siret","exist");
            }
            sirenSiretValidator.validateSiret(command.getSiret(),errors,"siret");
        }
*/


    }


 }
