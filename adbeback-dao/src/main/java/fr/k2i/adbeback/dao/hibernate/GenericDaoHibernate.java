package fr.k2i.adbeback.dao.hibernate;

import fr.k2i.adbeback.dao.GenericDao;
import fr.k2i.adbeback.dao.utils.CriteriaBuilderHelper;
import fr.k2i.adbeback.logger.LogHelper;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.hibernate3.HibernateTemplate;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;

public class GenericDaoHibernate<T, PK extends Serializable> implements GenericDao<T, PK> {
    /**
     * Log variable for all child classes. Uses LogFactory.getLog(getClass()) from Commons Logging
     */
    //protected org.slf4j.Logger log;
    private Class<T> persistentClass;
    public static final String PERSISTENCE_UNIT_NAME = "entityManagerFactory";

    public final Logger log = LogHelper.getLogger(this.getClass());

    /**
     * Entity manager, injected by Spring using @PersistenceContext annotation on setEntityManager()
     */
    @PersistenceContext
    private EntityManager entityManager;

    private HibernateTemplate hibernateTemplate;

    public HibernateTemplate getHibernateTemplate() {
        if(hibernateTemplate == null){
            this.hibernateTemplate = new HibernateTemplate(getSessionFactory());
        }
        return hibernateTemplate;
    }

    public SessionFactory getSessionFactory() {
        Session session = (Session) entityManager.getDelegate();
        return session.getSessionFactory();
    }


    public EntityManager getEntityManager() {
        return entityManager;
    }

    /**
     * Constructor that takes in a class to see which type of entity to persist.
     * Use this constructor when subclassing.
     *
     * @param persistentClass the class type you'd like to persist
     */
    public GenericDaoHibernate(final Class<T> persistentClass) {
        this.persistentClass = persistentClass;
    }

    /**
     *
     * @param persistentClass the class type you'd like to persist
     * @param entityManager
     */
    public GenericDaoHibernate(final Class<T> persistentClass, EntityManager entityManager) {
        this.persistentClass = persistentClass;
        this.entityManager = entityManager;
    }


/*    public Session getSession() throws HibernateException {
        return entityManager.unwrap(Session.class);
    }*/


    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public List<T> getAll() {
        return this.entityManager.createQuery(
                "select obj from " + this.persistentClass.getName() + " obj")
                .getResultList();
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public List<T> getAllDistinct() {
        Collection<T> result = new LinkedHashSet<T>(getAll());
        return new ArrayList<T>(result);
    }


    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public T get(PK id) {
        T entity = this.entityManager.find(this.persistentClass, id);

        if (entity == null) {
            String msg = "Uh oh, '" + this.persistentClass + "' object with id '" + id + "' not found...";
            //log.warn(msg);
            throw new EntityNotFoundException(msg);
        }

        return entity;
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public boolean exists(PK id) {
        T entity = this.entityManager.find(this.persistentClass, id);
        return entity != null;
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public T save(T object) {
        return this.entityManager.merge(object);
    }

    /**
     * {@inheritDoc}
     */
    public void remove(T object) {
        this.entityManager.remove(object);
    }

    /**
     * {@inheritDoc}
     */
    public void remove(PK id) {
        this.entityManager.remove(this.get(id));
    }

    @Override
    public List<T> getAllPaginated(int firstResult, int maxResult) {
        CriteriaBuilderHelper<T> helper = new CriteriaBuilderHelper<T>(entityManager,persistentClass);
        return helper.getResultList(firstResult, maxResult);
   }

    @Override
    public Long countAll() {
        CriteriaBuilderHelper<T> helper = new CriteriaBuilderHelper<T>(entityManager,persistentClass);
        return helper.count("id");
    }


}

