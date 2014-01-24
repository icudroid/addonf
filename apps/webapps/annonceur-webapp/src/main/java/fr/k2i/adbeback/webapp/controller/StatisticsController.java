package fr.k2i.adbeback.webapp.controller;

import fr.k2i.adbeback.webapp.facade.StatisticsFacade;
import fr.k2i.adbeback.webapp.facade.UserFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

/**
 * User: dimitri
 * Date: 24/01/14
 * Time: 13:39
 * Goal:
 */
@Controller
public class StatisticsController {
    @Autowired
    private StatisticsFacade statisticsFacade;


    @RequestMapping("/dashboard/global/{idAd}")
    public String global(@PathVariable Long idAd,Map<String, Object> model) throws Exception {

        statisticsFacade.global(idAd,model);

        return "dashboard/global";
    }
}
