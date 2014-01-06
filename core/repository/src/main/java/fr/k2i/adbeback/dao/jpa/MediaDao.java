package fr.k2i.adbeback.dao.jpa;

import fr.k2i.adbeback.core.business.media.*;
import fr.k2i.adbeback.core.business.push.HomePush;
import fr.k2i.adbeback.core.business.push.HomePush_;
import fr.k2i.adbeback.dao.IMediaDao;
import fr.k2i.adbeback.dao.utils.CriteriaBuilderHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * This class interacts with Spring's HibernateTemplate to save/delete and
 * retrieve User objects.
 * 
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a> Modified by
 *         <a href="mailto:dan@getrolling.com">Dan Kibler</a> Extended to
 *         implement Acegi UserDetailsService interface by David Carter
 *         david@carter.net Modified by <a href="mailto:bwnoll@gmail.com">Bryan
 *         Noll</a> to work with the new BaseDaoHibernate implementation that
 *         uses generics.
 */
@Repository("mediaDao")
public class MediaDao extends GenericDaoJpa<Media, Long> implements IMediaDao {

    @Autowired
    private MusicRepository musicRepository;

	/**
	 * Constructor that sets the entity to User.class.
	 */
	public MediaDao() {
		super(Media.class);
	}


    @Transactional
    @Override
	public List<Music> findMusicBy(String str, Long idGenre) throws Exception {
        CriteriaBuilderHelper<Music> helper = new CriteriaBuilderHelper(getEntityManager(),Music.class);


		if(str!=null){
			String[] split = str.split(" ");
			for (String search : split) {

                helper.criteriaHelper.or(
                        helper.criteriaHelper.ilike(helper.rootHelper.join(Music_.artists).get(Artist_.firstName), search, CriteriaBuilderHelper.MatchMode.ANYWHERE),
                        helper.criteriaHelper.ilike(helper.rootHelper.join(Music_.artists).get(Artist_.lastName), search,CriteriaBuilderHelper.MatchMode.ANYWHERE),
                        helper.criteriaHelper.ilike(helper.rootHelper.get(Music_.title), search,CriteriaBuilderHelper.MatchMode.ANYWHERE)
                );

			}


            helper.queryHelper.orderBy(
                    helper.criteriaHelper.desc(helper.rootHelper.get(Music_.title)),
                    helper.criteriaHelper.desc(helper.rootHelper.join(Music_.artists).get(Artist_.firstName)),
                    helper.criteriaHelper.desc(helper.rootHelper.join(Music_.artists).get(Artist_.lastName))
            );

		}

		if (idGenre != null && idGenre != -1) {
            helper.criteriaHelper.and(
                    helper.criteriaHelper.equal(helper.rootHelper.join(Music_.genres).get(Genre_.id), idGenre)
            );
		}
		
		return helper.getResultList();
	}

    @Transactional
    @Override
	public List<Music> searchBestMusicDownload(Long idGenre) throws Exception {

        List<Object[]> results = (List<Object[]>) getEntityManager()
                .createQuery("SELECT m.id AS id, COUNT(m.id) AS total FROM Music AS m GROUP BY m.id ORDER BY COUNT (m.id) DESC")
                .setMaxResults(25)
                .getResultList();


        List<Long> idMedias = new ArrayList<Long>();

        for (Object[] result : results) {
            Long id = (Long) result[0];
            idMedias.add(id);
        }


        CriteriaBuilderHelper<Music> helper = new CriteriaBuilderHelper(getEntityManager(),Music.class);

        if (idGenre != null && idGenre != -1) {
            helper.criteriaHelper.and(
                    helper.criteriaHelper.equal(helper.rootHelper.join(Music_.genres).get(Genre_.id), idGenre)
            );
        }


		if(idMedias.size()>0){
            helper.criteriaHelper.and(
                    helper.rootHelper.get(Music_.id).in(idMedias)
            );
		}

        helper.queryHelper.orderBy(
                helper.criteriaHelper.desc(helper.rootHelper.get(Music_.title))
        );


		return helper.getResultList();
	}


    @Transactional
	@Override
	public List<Music> searchNewMusic(Long idGenre, String str, int max)
			throws Exception {

        CriteriaBuilderHelper<Music> helper = new CriteriaBuilderHelper(getEntityManager(),Music.class);

        Calendar cal = new GregorianCalendar();
        cal.add(Calendar.DATE, -7);

        helper.criteriaHelper.and(
                helper.criteriaHelper.greaterThan(helper.rootHelper.get(Music_.releaseDate), cal.getTime())
        );


        if(str!=null){
            String[] split = str.split(" ");
            for (String search : split) {

                helper.criteriaHelper.or(
                        helper.criteriaHelper.ilike(helper.rootHelper.join(Music_.artists).get(Artist_.firstName), search, CriteriaBuilderHelper.MatchMode.ANYWHERE),
                        helper.criteriaHelper.ilike(helper.rootHelper.join(Music_.artists).get(Artist_.lastName), search,CriteriaBuilderHelper.MatchMode.ANYWHERE),
                        helper.criteriaHelper.ilike(helper.rootHelper.get(Music_.title), search,CriteriaBuilderHelper.MatchMode.ANYWHERE)
                );

            }


            helper.queryHelper.orderBy(
                    helper.criteriaHelper.desc(helper.rootHelper.get(Music_.releaseDate)),
                    helper.criteriaHelper.desc(helper.rootHelper.get(Music_.title)),
                    helper.criteriaHelper.desc(helper.rootHelper.join(Music_.artists).get(Artist_.firstName)),
                    helper.criteriaHelper.desc(helper.rootHelper.join(Music_.artists).get(Artist_.lastName))
            );

        }

        if (idGenre != null && idGenre != -1) {
            helper.criteriaHelper.and(
                    helper.criteriaHelper.equal(helper.rootHelper.join(Music_.genres).get(Genre_.id), idGenre)
            );
        }

        return helper.getResultList(0,max);

	}


}
