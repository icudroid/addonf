package fr.k2i.adbeback.webapp.controller;

import com.google.common.collect.Lists;
import fr.k2i.adbeback.core.business.ad.Ad;
import fr.k2i.adbeback.core.business.ad.Brand;
import fr.k2i.adbeback.core.business.ad.rule.AdService;
import fr.k2i.adbeback.core.business.ad.rule.BrandRule;
import fr.k2i.adbeback.core.business.ad.rule.OpenRule;
import fr.k2i.adbeback.dao.IAdDao;
import fr.k2i.adbeback.dao.IStatisticsDao;
import fr.k2i.adbeback.dao.jpa.StatisticsDao;
import fr.k2i.adbeback.webapp.facade.BrandServiceFacade;
import fr.k2i.adbeback.webapp.facade.UserFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: dimitri
 * Date: 29/10/13
 * Time: 13:25
 * To change this template use File | Settings | File Templates.
 */
@Controller
public class HomeController {

    @Autowired
    private UserFacade userFacade;

    @Autowired
    private IStatisticsDao statisticsDao;

    @Autowired
    private IAdDao adDao;

    @RequestMapping("/")
    public String home(Map<String, Object> model) {
        try{
            Brand currentUser = userFacade.getCurrentUser();
            List<Ad> ads = adDao.findByBrand(currentUser);
            long l = statisticsDao.nbGlobal(ads.get(0), new Date(), new Date());
            List<StatisticsDao.StatisticsAge> statisticsAges = statisticsDao.nbGlobalGroupAge(ads.get(0), new Date(), new Date());

            List<Class<? extends AdService>> services = Lists.newArrayList(BrandRule.class, OpenRule.class);
            List<StatisticsDao.Statistics> statisticses = statisticsDao.nbGlobalGroupByService(ads.get(0), new Date(), new Date(), services);

            return "home";
        }catch (Exception e){
            return "redirect:/login";
        }
    }

    @RequestMapping("/logout-success")
    public String secure(Map<String, Object> model) {
        return "redirect:/";
    }


    @RequestMapping(value="/setLogo",method = RequestMethod.GET)
    public String setLogo(Map<String, Object> model) throws Exception {
        Brand currentUser = userFacade.getCurrentUser();
        model.put("logo", currentUser.getLogo());
        return "logo";
    }


    @RequestMapping(value="/setLogo",method = RequestMethod.POST)
    public String setLogoSubmit(MultipartFile logo) throws Exception {
        userFacade.setLogo(logo);
        return IMetaDataController.PathUtils.REDIRECT;
    }

}
