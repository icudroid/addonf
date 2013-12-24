package fr.k2i.adbeback.webapp.controller;

import fr.k2i.adbeback.webapp.facade.ImportServiceFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: dev
 * Date: 22/12/13
 * Time: 00:02
 * To change this template use File | Settings | File Templates.
 */
@Controller
public class ImportCityController {
    @Autowired
    private ImportServiceFacade importServiceFacade;


    @RequestMapping("/importCities")
    public String importCities() throws IOException {
        importServiceFacade.importCities();
        return "home";
    }

}
