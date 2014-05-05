package fr.k2i.adbeback.webapp.controller;

import fr.k2i.adbeback.webapp.facade.MediaFacadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * User: dimitri
 * Date: 05/05/14
 * Time: 13:38
 * Goal:
 */
@Controller
public class SettingMediaController {

    @Autowired
    private MediaFacadeService mediaFacadeService;

    @RequestMapping(value = IMetaDataController.Path.SHOW_PASSPHRASE,method = RequestMethod.GET)
    public String showPassphrase(ModelMap model) throws Exception {
        model.put("passphrase",mediaFacadeService.getMediaPassPhrase());
        return IMetaDataController.View.SHOW_PASSPHRASE;
    }


    @RequestMapping(value = IMetaDataController.Path.SHOW_PASSPHRASE_GENERATE_NEW,method = RequestMethod.GET)
    public String generatePassphrase(ModelMap model) throws Exception {
        model.put("passphrase",mediaFacadeService.generateNewPassPhrase());
        return IMetaDataController.View.SHOW_PASSPHRASE;
    }


    @RequestMapping(value = IMetaDataController.Path.SHOW_EXT_ID,method = RequestMethod.GET)
    public String showExtId(ModelMap model) throws Exception {
        model.put("extId",mediaFacadeService.getExtId());
        return IMetaDataController.View.EXT_ID;
    }


}
