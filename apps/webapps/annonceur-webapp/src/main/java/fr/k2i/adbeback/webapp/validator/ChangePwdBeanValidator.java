package fr.k2i.adbeback.webapp.validator;

import edu.vt.middleware.password.*;
import fr.k2i.adbeback.logger.LogHelper;
import fr.k2i.adbeback.webapp.bean.ChangePasswordBean;
import fr.k2i.adbeback.webapp.bean.ChangePwdBean;
import fr.k2i.adbeback.webapp.bean.enroll.InformationCommand;
import fr.k2i.adbeback.webapp.facade.UserFacade;
import fr.k2i.adbeback.webapp.util.ValidatorHelper;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.validation.ConstraintViolation;
import javax.validation.groups.Default;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * User: dimitri
 * Date: 06/05/14
 * Time: 13:59
 * Goal:
 */
@Component
public class ChangePwdBeanValidator  implements Validator {
    private Logger logger = LogHelper.getLogger(this.getClass());
    @Autowired
    private javax.validation.Validator beanValidator;
    @Autowired
    private ValidatorHelper validatorHelper;

    @Autowired
    private UserFacade userFacade;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public boolean supports(Class<?> aClass) {
        return ChangePwdBean.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        ChangePwdBean changePwdBean = (ChangePwdBean) o;


        Set<ConstraintViolation<ChangePwdBean>> validate = beanValidator.validate(changePwdBean, Default.class);
        validatorHelper.importBeanValidationErrors(validate,errors);


        String password = null;
        try {
            password = userFacade.getCurrentUser().getPassword();
        } catch (Exception e) {
            logger.debug("Erreur lors de la récupération de l'utilisateur loggué",e);
        }

        String old = passwordEncoder.encodePassword(changePwdBean.getOldPassword(), null);
        if(!password.equals(old)){
            errors.rejectValue("oldPassword","notMatch");
        }

        if(!changePwdBean.getNewConfirmPassword().equals(changePwdBean.getNewPassword())){
            errors.rejectValue("newConfirmPassword","notMatch");
        }

        String newPwd = passwordEncoder.encodePassword(changePwdBean.getNewPassword(), null);

        if(newPwd.equals(old)){
            errors.rejectValue("newConfirmPassword","not.equals.to.old");
        }


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
        PasswordData passwordData = new PasswordData(new Password(changePwdBean.getNewConfirmPassword()));

        RuleResult result = validator.validate(passwordData);
        if (!result.isValid()) {
            errors.rejectValue("newConfirmPassword", "not.enough.strong");
        }


    }
}