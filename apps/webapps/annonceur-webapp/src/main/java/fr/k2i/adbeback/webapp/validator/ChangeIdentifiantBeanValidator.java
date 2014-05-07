package fr.k2i.adbeback.webapp.validator;

import fr.k2i.adbeback.core.business.user.User;
import fr.k2i.adbeback.dao.IWebUserDao;
import fr.k2i.adbeback.logger.LogHelper;
import fr.k2i.adbeback.webapp.bean.ChangeIdentifiantBean;
import fr.k2i.adbeback.webapp.facade.UserFacade;
import fr.k2i.adbeback.webapp.util.ValidatorHelper;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.annotation.Resource;
import javax.validation.ConstraintViolation;
import javax.validation.groups.Default;
import java.util.Set;

/**
 * User: dimitri
 * Date: 06/05/14
 * Time: 13:59
 * Goal:
 */
@Component
public class ChangeIdentifiantBeanValidator implements Validator {
    private Logger logger = LogHelper.getLogger(this.getClass());
    @Autowired
    private javax.validation.Validator beanValidator;
    @Autowired
    private ValidatorHelper validatorHelper;

    @Autowired
    private UserFacade userFacade;

    @Resource(name="webUserDao")
    private IWebUserDao webUserDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public boolean supports(Class<?> aClass) {
        return ChangeIdentifiantBean.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        ChangeIdentifiantBean identifiantBean = (ChangeIdentifiantBean) o;

        Set<ConstraintViolation<ChangeIdentifiantBean>> validate = beanValidator.validate(identifiantBean, Default.class);
        validatorHelper.importBeanValidationErrors(validate,errors);


        try {
            User currentUser = userFacade.getCurrentUser();
            if(currentUser.getEmail().equals(identifiantBean.getEmail())){
                errors.rejectValue("email","same.email");
            }else{
                User userByEmail = webUserDao.getUserByEmail(identifiantBean.getEmail());
                if(userByEmail!=null){
                    errors.rejectValue("email","already.exist");
                }
            }

        } catch (Exception e) {
            logger.debug("",e);

        }

    }
}