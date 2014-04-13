package fr.k2i.adbeback.dao;

import fr.k2i.adbeback.core.business.ad.Ad;
import fr.k2i.adbeback.core.business.user.User;
import fr.k2i.adbeback.dao.IGenericDao;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Created with IntelliJ IDEA.
 * User: dev
 * Date: 05/04/14
 * Time: 16:36
 * To change this template use File | Settings | File Templates.
 */
public interface IWebUserDao extends IGenericDao<User, Long> {

    UserDetails findByUsername(String username);

    User getUserByEmail(String email);

    void enable(Long idUser);

    void setPassword(Long idUser, String password);
}
