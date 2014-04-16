package fr.k2i.adbeback.webapp.helper;

import fr.k2i.adbeback.core.business.ad.Sector;
import fr.k2i.adbeback.core.business.country.Country;
import fr.k2i.adbeback.core.business.player.AgeGroup;
import fr.k2i.adbeback.core.business.player.Sex;
import fr.k2i.adbeback.core.business.user.*;
import fr.k2i.adbeback.dao.ICategoryDao;
import fr.k2i.adbeback.dao.ICountryDao;
import fr.k2i.adbeback.dao.ISectorDao;
import fr.k2i.adbeback.webapp.bean.enroll.*;
import fr.k2i.adbeback.webapp.bean.enroll.agency.AgencyRole;
import fr.k2i.adbeback.webapp.controller.UploadController;
import fr.k2i.adbeback.webapp.facade.UserFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.webflow.execution.RequestContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * User: dimitri
 * Date: 01/04/14
 * Time: 16:27
 * Goal:
 */
@Service
public class EnrollHelper {

    @Autowired
    private ICountryDao countryDao;

    @Autowired
    private ISectorDao sectorDao;

    @Autowired
    private ICategoryDao categoryDao;

    @Autowired
    private UserFacade userFacade;

    @Transactional
    public Map<String,Object> defaultModelNeeded(){
        Map<String,Object> model = new HashMap<String, Object>();
        Iterable<Country> all = countryDao.getAll();
        List<String> countries = new ArrayList<String>();
        Iterator<Country> iterator = all.iterator();
        while (iterator.hasNext()) {
            Country next = iterator.next();
            countries.add(next.getCode());
        }
        model.put("countries",countries);


/*        List<String>sexes = new ArrayList<String>();
        for (Sex sex : Sex.values()) {
            sexes.add(sex.name());
        }*/
        model.put("sexes",Sex.values());

/*        List<String>legalStatus = new ArrayList<String>();
        for (LegalStatus status : LegalStatus.values()) {
            legalStatus.add(status.name());
        }*/
        model.put("legalStatus",LegalStatus.values());


        List<String>roles = new ArrayList<String>();
        for (AgencyRole role : AgencyRole.values()) {
            if(!AgencyRole.ADMIN.equals(role))roles.add(role.name());
        }
        model.put("roles",roles);


/*        List<String>mediaTypes = new ArrayList<String>();
        for (MediaType mediaType : MediaType.values()) {
            mediaTypes.add(mediaType.name());
        }*/
        model.put("medias",MediaType.values());


        List<Sector> sectors = sectorDao.getAll();
        model.put("sectors",sectors);

/*        List<String> ages = new ArrayList<String>();
        for (AgeGroup age : AgeGroup.values()) {
            ages.add(age.name());
        }*/
        model.put("ages",AgeGroup.values());


        List<Category> categories = categoryDao.getAll();
        model.put("categories",categories);

        return model;
    }


    public String whichEnroll(EnrollFlowState which){
        return which.getRegistration().name();
    }


    public void emptyUploaded(RequestContext context){
        HttpSession session = ((HttpServletRequest)context.getExternalContext().getNativeRequest()).getSession();
        session.removeAttribute(UploadController.UPLOADED_PARAM);
    }



    @Transactional
    public String isAgencyLoggued(){
        try {
            User currentUser = userFacade.getCurrentUser();
            if (currentUser instanceof AgencyUser) {
                return "agencyUser";
            }else {
                return "user";
            }
        } catch (Exception e) {
            return "notLoggued";
        }
    }


}
