package fr.k2i.adbeback.webapp.controller;

import fr.k2i.adbeback.core.business.country.Country;
import fr.k2i.adbeback.core.business.player.Sex;
import fr.k2i.adbeback.crypto.DESCryptoService;
import fr.k2i.adbeback.dao.jpa.CountryRepository;
import fr.k2i.adbeback.logger.LogHelper;
import fr.k2i.adbeback.webapp.bean.EnrollBrandCommand;
import fr.k2i.adbeback.webapp.facade.BrandServiceFacade;
import fr.k2i.adbeback.webapp.validator.EnrollBrandCommandValidator;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: dev
 * Date: 15/12/13
 * Time: 13:40
 * To change this template use File | Settings | File Templates.
 */
@Controller
public class EnrollController {

    @Autowired
    private CountryRepository countryRepository;


    @Autowired
    private EnrollBrandCommandValidator enrollBrandCommandValidator;

    @Autowired
    private BrandServiceFacade brandServiceFacade;
    
    @Autowired 
    private DESCryptoService desCryptoService;

    private static final Logger log = LogHelper.getLogger(EnrollController.class);

    @InitBinder("enrollBrandCommand")
    protected void initBinder(WebDataBinder binder) {
        binder.setValidator(enrollBrandCommandValidator);
    }

    @ModelAttribute("enrollBrandCommand")
    protected EnrollBrandCommand enrollBrandCommand(){
        return new EnrollBrandCommand();
    }

    @ModelAttribute("values")
    public Map<String,Object> model(){
        Map<String,Object> model = new HashMap<String, Object>();
        Iterable<Country> all = countryRepository.findAll();
        List<String> countries = new ArrayList<String>();
        Iterator<Country> iterator = all.iterator();
        while (iterator.hasNext()) {
            Country next = iterator.next();
            countries.add(next.getCode());
        }
        model.put("countries",countries);


        List<String>sexes = new ArrayList<String>();
        for (Sex sex : Sex.values()) {
            sexes.add(sex.name());
        }
        model.put("sexes",sexes);

        return model;
    }

    @RequestMapping(value = IMetaDataController.Path.ENROLL_FORM, method = RequestMethod.GET)
    public ModelAndView show(@ModelAttribute("enrollBrandCommand") EnrollBrandCommand enrollBrandCommand,ModelMap model){
        return new ModelAndView(IMetaDataController.View.ENROLL_FORM);
    }


    @RequestMapping(value = IMetaDataController.Path.ENROLL_FORM, method = RequestMethod.POST)
    public ModelAndView submit(@Valid @ModelAttribute("enrollBrandCommand") EnrollBrandCommand enrollBrandCommand, BindingResult bindingResult,ModelMap model,HttpServletRequest request){
        if(bindingResult.hasErrors()){
            return new ModelAndView(IMetaDataController.View.ENROLL_FORM);
        }else{
            ModelAndView view = new ModelAndView(IMetaDataController.View.ENROLLED_FORM);
            brandServiceFacade.enrollBrand(enrollBrandCommand,request.getLocale());
            model.addAttribute("enrolled",true);
            return view;
        }

    }

    @RequestMapping(value = IMetaDataController.Path.REGISTRATION_CONFIRM)
    public String confirmRegistration(@PathVariable String crypt,@PathVariable String code,ModelMap modelMap, HttpServletRequest req) throws Exception {
        String[] strings = desCryptoService.decrypt(crypt).split("\\|");

        String name = strings[0];
        String email = strings[1];

        BrandServiceFacade.ConfirmationRegistration res = brandServiceFacade.confirmRegistration(email,name,code);
        HttpSession session = req.getSession();
        session.setAttribute("result",res.name());
        session.setAttribute("email",email);

        return IMetaDataController.View.REGISTRATION_BRAND_CONFIRM;


    }


}
