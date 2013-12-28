package fr.k2i.adbeback.webapp.validator;

import fr.k2i.adbeback.core.business.ad.AdType;
import fr.k2i.adbeback.logger.LogHelper;
import fr.k2i.adbeback.util.ValidatorHelper;
import fr.k2i.adbeback.webapp.bean.CampaignCommand;
import fr.k2i.adbeback.webapp.bean.InformationCommand;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Date;

/**
 * User: dimitri
 * Date: 27/12/13
 * Time: 14:49
 * Goal:
 */
public class CampaignCommandValidator implements Validator{

    private static Logger logger = LogHelper.getLogger(CampaignCommandValidator.class);

    @Autowired
    private javax.validation.Validator beanValidator;

    @Autowired
    private ValidatorHelper validatorHelper;


    @Override
    public boolean supports(Class<?> aClass) {
        return CampaignCommand.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        if(o instanceof InformationCommand){
            validateStep1((InformationCommand) o,errors);
        }
    }

    public void validateStep1(InformationCommand command, Errors errors) {

        Date endDate = command.getEndDate();
        if(endDate==null){
            errors.rejectValue("endDate","required");
        }
        Date startDate = command.getStartDate();
        if(startDate==null){
            errors.rejectValue("startDate","required");
        }

        if(startDate!=null && endDate!=null && startDate.after(endDate)){
            errors.rejectValue("startDate","after.endDate");
        }


        Double initialAmonut = command.getInitialAmonut();
        if(initialAmonut==null){
            errors.rejectValue("initialAmonut","required");
        }
        String name = command.getName();
        if(name==null){
            errors.rejectValue("name","required");
        }

        AdType type = command.getType();
        if(type==null){
            errors.rejectValue("type","required");
        }

    }

}
