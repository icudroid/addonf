package fr.k2i.adbeback.webapp.helper;

import fr.k2i.adbeback.core.business.country.Country;
import fr.k2i.adbeback.core.business.player.Sex;
import fr.k2i.adbeback.core.business.user.AttachementStatus;
import fr.k2i.adbeback.core.business.user.LegalStatus;
import fr.k2i.adbeback.dao.ICountryDao;
import fr.k2i.adbeback.webapp.bean.FileCommand;
import fr.k2i.adbeback.webapp.bean.enroll.*;
import fr.k2i.adbeback.webapp.state.enroll.AgencyEnrollFlowState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.webflow.context.ExternalContext;
import org.springframework.webflow.execution.RequestContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;

/**
 * User: dimitri
 * Date: 01/04/14
 * Time: 16:27
 * Goal:
 */
@Service
public class AgencyEnrollHelper {


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

    public void addUser(AgencyEnrollCommand agencyEnrollCommand){
        AgencyUsersCommand users = agencyEnrollCommand.getUsers();
        AgencyUserBean current = users.getCurrent();
        users.getUsers().add(current);
    }

    public void deleteUser(AgencyEnrollCommand agencyEnrollCommand,String email){
        AgencyUsersCommand users = agencyEnrollCommand.getUsers();
        AgencyUserBean toDelete = null;
        for (AgencyUserBean agencyUserBean : users.getUsers()) {
            if(agencyUserBean.getEmail().equals(email)){
                toDelete = agencyUserBean;
            }
        }
        users.getUsers().remove(toDelete);
    }


    public void emptyCurrent(AgencyEnrollCommand agencyEnrollCommand){
        agencyEnrollCommand.getUsers().setCurrent(new AgencyUserBean());
    }


    public void fillAdmin(AgencyEnrollCommand agencyEnrollCommand){
        AgencyUsersCommand users = agencyEnrollCommand.getUsers();
        AgencyUserBean currentAdmin = null;
        for (AgencyUserBean agencyUserBean : users.getUsers()) {
            if(AgencyRole.ADMIN.equals(agencyUserBean.getRole())){
                currentAdmin = agencyUserBean;
            }
        }
        if(currentAdmin!=null)users.setCurrent(currentAdmin);
    }


    public List<String> availableFiles(AgencyEnrollFlowState state){
        return state.getRegistration().availableFiles();
    }


    public List<String> requiredFiles(AgencyEnrollFlowState state){
        return state.getRegistration().requiredFiles();
    }

    public Map<String,String> uploadedFileStatus(AgencyEnrollFlowState state,AttachementsCommand command){

        Map<String,String> res = new HashMap<String, String>();
        Map<String,FileCommand> files = command.getFiles();

        List<String> filesNeeded = state.getRegistration().requiredFiles();
        for (String file : filesNeeded) {
            FileCommand fileCommand = files.get(file);
            if(fileCommand!=null){
                res.put(file, AttachementStatus.PRESENT.name());
            }else{
                res.put(file, AttachementStatus.NO_PRESENT.name());
            }
        }

        return res;
    }


    public String saveFiles(AgencyEnrollCommand agencyEnrollCommand, ExternalContext context){

        System.err.println("");
        return "ok";
    }


}
