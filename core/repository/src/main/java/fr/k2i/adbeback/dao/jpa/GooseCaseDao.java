package fr.k2i.adbeback.dao.jpa;

import com.mysema.query.jpa.impl.JPAQuery;
import fr.k2i.adbeback.core.business.goosegame.*;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Table;
import java.util.ArrayList;
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
@Repository("gooseCaseDao")
public class GooseCaseDao extends GenericDaoJpa<GooseCase, Long> implements fr.k2i.adbeback.dao.IGooseCaseDao {

    /**
     * Constructor that sets the entity to User.class.
     */
    public GooseCaseDao() {
        super(GooseCase.class);
    }

	public GooseCase getByNumber(Integer number,GooseLevel level) throws Exception {
        JPAQuery query = new JPAQuery(getEntityManager());

        QGooseCase gooseCase = QGooseCase.gooseCase;

        query.from(gooseCase).where(gooseCase.number.eq(number),gooseCase.level.eq(level));

        return query.uniqueResult(gooseCase);
	}

	@SuppressWarnings("unchecked")
	public List<GooseCase> get(GooseLevel level, Integer start, Integer nb)
			throws Exception {
        List<Integer> nums = new ArrayList<Integer>();

        if(level.getEndCase().getNumber()>=nb){
            nb = level.getEndCase().getNumber();
        }

        for (int i = 0; i < nb; i++) {
            nums.add(start+i);
        }

        JPAQuery query = new JPAQuery(getEntityManager());

        QGooseCase gooseCase = QGooseCase.gooseCase;

        query.from(gooseCase).where(gooseCase.level.eq(level),gooseCase.number.in(nums)).orderBy(gooseCase.number.asc());

        return query.list(gooseCase);
	}

	@SuppressWarnings("unchecked")
	public List<GooseCase> getCases(GooseLevel level) throws Exception {

        JPAQuery query = new JPAQuery(getEntityManager());

        QGooseCase gooseCase = QGooseCase.gooseCase;

        query.from(gooseCase).where(gooseCase.level.eq(level)).orderBy(gooseCase.number.asc());

        return query.list(gooseCase);

	}


    @Override
    public void updateType(Long caseId, Integer type) {
        JdbcTemplate jdbcTemplate =
                new JdbcTemplate(org.springframework.orm.hibernate4.SessionFactoryUtils.getDataSource(getSessionFactory()));
        Table table = AnnotationUtils.findAnnotation(GooseCase.class, Table.class);
        GooseCaseType gooseCaseType = GooseCaseType.findByType(type);

        Class<?> aClass = null;
        try {
            aClass = Class.forName("fr.k2i.adbeback.core.business.goosegame." + gooseCaseType.name());
        } catch (ClassNotFoundException e) {
            log.error(e);
        }
        DiscriminatorValue discriminatorValue = AnnotationUtils.findAnnotation(aClass, DiscriminatorValue.class);
        jdbcTemplate.update("update " + table.name() + " set classe=? where id=?", discriminatorValue.value(), caseId);
    }


    public void updateTypeJumpTo(Long caseId, Long toCaseId) {
        JdbcTemplate jdbcTemplate =
                new JdbcTemplate(org.springframework.orm.hibernate4.SessionFactoryUtils.getDataSource(getSessionFactory()));
        Table table = AnnotationUtils.findAnnotation(GooseCase.class, Table.class);
        DiscriminatorValue discriminatorValue = AnnotationUtils.findAnnotation(JumpGooseCase.class, DiscriminatorValue.class);
        jdbcTemplate.update("update " + table.name() + " set classe=?, GOOSEJUMP_ID=? where id=?", discriminatorValue.value(), toCaseId,caseId);
    }
}

