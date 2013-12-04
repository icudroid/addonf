package fr.k2i.adbeback.dao.jpa;

import fr.k2i.adbeback.core.business.user.User;
import fr.k2i.adbeback.core.business.user.User_;
import fr.k2i.adbeback.dao.utils.CriteriaBuilderHelper;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import javax.persistence.Table;
import java.util.List;

/**
 * This class interacts with Spring's HibernateTemplate to save/delete and
 * retrieve User objects.
 *
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 *   Modified by <a href="mailto:dan@getrolling.com">Dan Kibler</a>
 *   Extended to implement Acegi UserDetailsService interface by David Carter david@carter.net
 *   Modified by <a href="mailto:bwnoll@gmail.com">Bryan Noll</a> to work with 
 *   the new BaseDaoHibernate implementation that uses generics.
*/
@Repository("adminDao")
public class AdminDao extends GenericDaoJpa<User, Long> implements fr.k2i.adbeback.dao.IAdminDao, UserDetailsService {

    /**
     * Constructor that sets the entity to User.class.
     */
    public AdminDao() {
        super(User.class);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        CriteriaBuilderHelper<User> helper = new CriteriaBuilderHelper(getEntityManager(),User.class);
        helper.criteriaHelper.and(
                helper.criteriaHelper.equal(helper.rootHelper.get(User_.username), username)
        );
        List users = helper.getResultList();

        if (users == null || users.isEmpty()) {
            throw new UsernameNotFoundException("user '" + username + "' not found...");
        } else {
            return (UserDetails) users.get(0);
        }
    }

    @Override
    public String getUserPassword(String username) {
        JdbcTemplate jdbcTemplate =
                new JdbcTemplate(org.springframework.orm.hibernate4.SessionFactoryUtils.getDataSource(getSessionFactory()));
        Table table = AnnotationUtils.findAnnotation(User.class, Table.class);
        return jdbcTemplate.queryForObject(
                "select password from " + table.name() + " where username=?", String.class, username);
    }
}
    
    


