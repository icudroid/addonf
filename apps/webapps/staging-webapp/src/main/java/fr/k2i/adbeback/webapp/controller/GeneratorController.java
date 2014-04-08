package fr.k2i.adbeback.webapp.controller;

import fr.k2i.adbeback.generator.SirenSiretUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/generate/**")
public class GeneratorController {

    @Autowired
    private SirenSiretUtils sirenSiretUtils;

    @RequestMapping(value = "/generate/siret.html")
    public String generateSiret(ModelMap model){
        model.put("siret",sirenSiretUtils.siretGenerator());
        return "siret";
    }

    @RequestMapping(value = "/generate/siren.html")
    public String generateSiren(ModelMap model){
        model.put("siren",sirenSiretUtils.sirenGenerator());
        return "siren";
    }

}