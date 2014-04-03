package fr.k2i.adbeback.webapp.validator;

import fr.k2i.adbeback.dao.IBrandDao;
import fr.k2i.adbeback.logger.LogHelper;
import fr.k2i.adbeback.webapp.bean.EnrollBrandCommand;
import fr.k2i.adbeback.webapp.bean.enroll.EnrollFlowState;
import fr.k2i.adbeback.webapp.util.SirenSiretValidator;
import fr.k2i.adbeback.webapp.util.ValidatorHelper;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * User: dimitri
 * Date: 03/04/14
 * Time: 11:00
 * Goal:
 */
@Component
public class WhichValidator{
    private static Logger logger = LogHelper.getLogger(WhichValidator.class);

    @Autowired
    private javax.validation.Validator beanValidator;

    @Autowired
    private ValidatorHelper validatorHelper;

    public void validate(EnrollFlowState state, Errors errors) {
        if(state.getRegistration()==null){
            errors.rejectValue("registration","enroll.type.required");
        }
    }
}