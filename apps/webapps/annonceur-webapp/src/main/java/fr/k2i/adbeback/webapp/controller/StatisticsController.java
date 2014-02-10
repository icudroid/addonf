package fr.k2i.adbeback.webapp.controller;

import fr.k2i.adbeback.webapp.bean.LabelData;
import fr.k2i.adbeback.webapp.bean.StatisticSearchBean;
import fr.k2i.adbeback.webapp.facade.StatisticsFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
        model.put("idAd",idAd);

        return "dashboard/global";
    }



    @RequestMapping(value = "/dashboard/search",method = RequestMethod.POST)
    public @ResponseBody List<LabelData> global(@RequestBody StatisticSearchBean search) throws Exception {
        return statisticsFacade.doSearch(search);
    }


}
