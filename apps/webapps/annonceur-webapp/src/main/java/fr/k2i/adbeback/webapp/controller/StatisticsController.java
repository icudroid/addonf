package fr.k2i.adbeback.webapp.controller;

import fr.k2i.adbeback.webapp.bean.LabelData;
import fr.k2i.adbeback.webapp.bean.StatisticSearchBean;
import fr.k2i.adbeback.webapp.facade.StatisticsFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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


    @RequestMapping(value = "/dashboard/detail/{idAd}",method = RequestMethod.GET)
    public String detail(@ModelAttribute("search") StatisticSearchBean search, @PathVariable Long idAd,Map<String, Object> model) throws Exception {

        statisticsFacade.detail(idAd,model);
        model.put("idAd",idAd);

        return "dashboard/detail";
    }


    @RequestMapping(value = "/dashboard/detail/{idAd}",method = RequestMethod.POST)
    public String detailFiltered(@ModelAttribute("search") StatisticSearchBean search, @PathVariable Long idAd,Pageable pageable,Map<String, Object> model) throws Exception {
        statisticsFacade.detail(idAd,model);
        statisticsFacade.doSearch(search, model,pageable);
        model.put("idAd",idAd);

        return "dashboard/detail";
    }

    @RequestMapping(value = "/dashboard/realTime/{idService}",method = RequestMethod.GET)
    public @ResponseBody
    StatisticsFacade.RealTimeResponse realTime(@PathVariable Long idService) throws Exception {
        return statisticsFacade.realTimeStat(idService);
    }


}
