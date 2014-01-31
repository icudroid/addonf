package fr.k2i.adbeback.webapp.controller;

import fr.k2i.adbeback.webapp.bean.DoStatBean;
import fr.k2i.adbeback.webapp.facade.ImportServiceFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * User: dimitri
 * Date: 30/01/14
 * Time: 14:54
 * Goal:
 */
@Controller
public class DoStatistcsController {

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private ImportServiceFacade importServiceFacade;

    @InitBinder
    public void initBinder(WebDataBinder binder,Locale locale) {
        DateFormat dateFormat = new SimpleDateFormat(messageSource.getMessage("date_format",null,locale));
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
    }


    @RequestMapping(value = "/doStat",method = RequestMethod.GET)
    public String doStat(@ModelAttribute("doStatBean")DoStatBean doStatBean){
        return "doStat";
    }

    @RequestMapping(value = "/doStat",method = RequestMethod.POST)
    public String doStatSubmit(@ModelAttribute("doStatBean")DoStatBean doStatBean){
        importServiceFacade.doStat(doStatBean.getStart(),doStatBean.getEnd());
        return "doStat";
    }
}
