package fr.k2i.adbeback.webapp.validator;

import fr.k2i.adbeback.logger.LogHelper;
import fr.k2i.adbeback.webapp.util.ValidatorHelper;
import fr.k2i.adbeback.webapp.bean.*;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Date;

/**
 * User: dimitri
 * Date: 27/12/13
 * Time: 14:49
 * Goal:
 */
@Component
public class CampaignCommandValidator implements Validator{

    private static Logger logger = LogHelper.getLogger(CampaignCommandValidator.class);

    @Autowired
    private javax.validation.Validator beanValidator;

    @Autowired
    private ValidatorHelper validatorHelper;


    @Override
    public boolean supports(Class<?> aClass) {
        return  InformationCommand.class.isAssignableFrom(aClass)||
                ProductBean.class.isAssignableFrom(aClass)||
                AdRulesCommand.class.isAssignableFrom(aClass)||
                AdService.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        if(o instanceof InformationCommand){
            validateStep((InformationCommand) o, errors);
        }else
        if(o instanceof AdRulesCommand){
            validateStep((AdRulesCommand) o, errors);
        }else
        if(o instanceof AdService){
            validateStep((AdService) o, errors);
        }

    }


    public void validateModify(Object o, Errors errors) {
        if(o instanceof InformationCommand){
            validateStepModify((InformationCommand) o, errors);
        }else
        if(o instanceof AdRulesCommand){
            validateStepModify((AdRulesCommand) o, errors);
        }else
        if(o instanceof AdService){
            validateStepModify((AdService) o, errors);
        }

    }

    private void validateStepModify(InformationCommand command, Errors errors) {
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
    }


    private void validateStepModify(AdRulesCommand o, Errors errors) {
        //To change body of created methods use File | Settings | File Templates.
    }


    private void validateStepModify(AdService o, Errors errors) {
        //To change body of created methods use File | Settings | File Templates.
    }



    public void validateStep(InformationCommand command, Errors errors) {

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
        if(StringUtils.isEmpty(name)){
            errors.rejectValue("name","required");
        }

        if(command.getAdFile().isEmpty()){
            errors.rejectValue("adFile","required");
        }


        if(command.getDisplayAd().equals(AdDisplay.STATIC)){
            if(command.getDisplayDuration()<10 || command.getDisplayDuration()>30){
                errors.rejectValue("displayDuration","more.than.10.seconds");
            }
        }


    }


    public void validateStep(AdRulesCommand adRulesCommand, Errors errors) {

    }

    public void validateStep(AdService adService, Errors errors) {

    }


}
