package fr.k2i.adbeback.webapp.validator;

import fr.k2i.adbeback.dao.jpa.BrandRepository;
import fr.k2i.adbeback.logger.LogHelper;
import fr.k2i.adbeback.webapp.util.PhoneNumberUtils;
import fr.k2i.adbeback.webapp.util.SirenSiretValidator;
import fr.k2i.adbeback.webapp.util.ValidatorHelper;
import fr.k2i.adbeback.webapp.bean.AddressBean;
import fr.k2i.adbeback.webapp.bean.ContactBean;
import fr.k2i.adbeback.webapp.bean.EnrollBrandCommand;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.validation.ConstraintViolation;
import javax.validation.groups.Default;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: dev
 * Date: 21/12/13
 * Time: 12:01
 * To change this template use File | Settings | File Templates.
 */

@Component
public class EnrollBrandCommandValidator implements Validator{

    private static Logger logger = LogHelper.getLogger(EnrollBrandCommandValidator.class);

    @Autowired
    private javax.validation.Validator beanValidator;

    @Autowired
    private SirenSiretValidator sirenSiretValidator;


    @Autowired
    private ValidatorHelper validatorHelper;


    @Autowired
    private BrandRepository brandRepository;

    @Override
    public boolean supports(Class<?> aClass) {
        return EnrollBrandCommand.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        EnrollBrandCommand command = (EnrollBrandCommand) o;


        Set<ConstraintViolation<EnrollBrandCommand>> validateEnrollBrandCommand = beanValidator.validate(command, Default.class);
        Set<ConstraintViolation<ContactBean>> validateContact = beanValidator.validate(command.getContact(), Default.class);

        validatorHelper.importBeanValidationErrors(validateEnrollBrandCommand,errors);
        validatorHelper.importBeanValidationErrors(validateContact, errors);




        if(StringUtils.isEmpty(command.getName())){
                    errors.rejectValue("name","required");
        }else{
            if(brandRepository.findByName(command.getName())!=null){
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
            if(brandRepository.findByEmail(contact.getEmail())!=null){
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
            if(brandRepository.findBySiret(command.getSiret())!=null){
                errors.rejectValue("siret","exist");
            }
            sirenSiretValidator.validateSiret(command.getSiret(),errors,"siret");
        }


    }


 }
