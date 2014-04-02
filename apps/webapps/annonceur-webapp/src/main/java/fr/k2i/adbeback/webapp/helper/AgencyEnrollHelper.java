package fr.k2i.adbeback.webapp.helper;

import fr.k2i.adbeback.core.business.country.Country;
import fr.k2i.adbeback.core.business.player.Sex;
import fr.k2i.adbeback.core.business.user.LegalStatus;
import fr.k2i.adbeback.dao.ICountryDao;
import fr.k2i.adbeback.webapp.bean.enroll.AgencyEnrollCommand;
import fr.k2i.adbeback.webapp.bean.enroll.AgencyRole;
import fr.k2i.adbeback.webapp.bean.enroll.AgencyUserBean;
import fr.k2i.adbeback.webapp.bean.enroll.AgencyUsersCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * User: dimitri
 * Date: 01/04/14
 * Time: 16:27
 * Goal:
 */
@Service
public class AgencyEnrollHelper {

    @Autowired
    private ICountryDao countryDao;

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


        List<String>sexes = new ArrayList<String>();
        for (Sex sex : Sex.values()) {
            sexes.add(sex.name());
        }
        model.put("sexes",sexes);

        List<String>legalStatus = new ArrayList<String>();
        for (LegalStatus status : LegalStatus.values()) {
            legalStatus.add(status.name());
        }
        model.put("legalStatus",legalStatus);


        List<String>roles = new ArrayList<String>();
        for (AgencyRole role : AgencyRole.values()) {
            roles.add(role.name());
        }
        model.put("roles",roles);


        return model;
    }


    public void createAdmin(AgencyEnrollCommand agencyEnrollCommand){
        AgencyUsersCommand users = agencyEnrollCommand.getUsers();

        //find if admin exist
        AgencyUserBean currentAdmin = null;
        for (AgencyUserBean agencyUserBean : users.getUsers()) {
            if(AgencyRole.ADMIN.equals(agencyUserBean.getRole())){
                currentAdmin = agencyUserBean;
            }
        }

        if(currentAdmin!=null){
            users.getUsers().remove(currentAdmin);
        }

        AgencyUserBean current = users.getCurrent();
        current.setRole(AgencyRole.ADMIN);
        users.getUsers().add(current);
    }


    public void emptyCurrent(AgencyEnrollCommand agencyEnrollCommand){
        agencyEnrollCommand.getUsers().setCurrent(new AgencyUserBean());
    }
}
