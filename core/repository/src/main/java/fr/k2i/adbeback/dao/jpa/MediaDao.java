package fr.k2i.adbeback.dao.jpa;

import com.mysema.query.QueryModifiers;
import com.mysema.query.Tuple;
import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.types.OrderSpecifier;
import com.mysema.query.types.expr.BooleanExpression;
import fr.k2i.adbeback.core.business.media.*;
import fr.k2i.adbeback.core.business.push.HomePush;
import fr.k2i.adbeback.core.business.push.HomePush_;
import fr.k2i.adbeback.dao.IMediaDao;
import fr.k2i.adbeback.dao.utils.CriteriaBuilderHelper;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

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


	/**
	 * Constructor that sets the entity to User.class.
	 */
	public MediaDao() {
		super(Media.class);
	}

    @Transactional
    @Override
    public Page<String> findMusicAndAlbumTitleByTitle(String str,Pageable pageable) throws Exception {

        QMedia media = QMedia.media;
        JPAQuery query = new JPAQuery(getEntityManager());
        query.from(media)
                .where(
                        media.title.containsIgnoreCase(str)
                );

        query
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(media.title.asc());

        return new PageImpl<String>(query.list(media.title),pageable,query.count());
    }

    @Transactional
    @Override
    public Page<String> findPersonFullNameByName(String req, Pageable pageable) {
        QPerson person = QPerson.person;
        JPAQuery query = new JPAQuery(getEntityManager());
        query.from(person)
                .where(
                        person.lastName.containsIgnoreCase(req).or(person.firstName.containsIgnoreCase(req))
                );

        query
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(person.lastName.asc())
                .orderBy(person.firstName.asc());

        List<Tuple> list = query.list(person.lastName, person.firstName);

        List<String> content = new ArrayList<String>();
        for (Tuple tuple : list) {
            String r = "";
            if(tuple.get(person.firstName).isEmpty()){
                r =tuple.get(person.lastName);
            }else{
                r = tuple.get(person.firstName)+" "+tuple.get(person.lastName);
            }
            content.add(r);
        }

        return new PageImpl<String>(content,pageable,query.count());
    }

    @Transactional
    @Override
    public Page<Artist> findArtistByFullName(String req, Pageable pageable) {
        QArtist artist = QArtist.artist;
        JPAQuery query = new JPAQuery(getEntityManager());
        query.from(artist)
                .where(
                        artist.lastName.containsIgnoreCase(req).or(artist.firstName.containsIgnoreCase(req))
                );

        query
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(artist.lastName.asc())
                .orderBy(artist.firstName.asc());


        return new PageImpl<Artist>(query.list(artist),pageable,query.count());
    }

    @Transactional
    @Override
    public Page<Productor> findProductorByFullName(String req, Pageable pageable) {
        QProductor productor = QProductor.productor;
        JPAQuery query = new JPAQuery(getEntityManager());
        query.from(productor)
                .where(
                        productor.lastName.containsIgnoreCase(req).or(productor.firstName.containsIgnoreCase(req))
                );

        query
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(productor.lastName.asc())
                .orderBy(productor.firstName.asc());


        return new PageImpl<Productor>(query.list(productor),pageable,query.count());
    }


    @Transactional
    @Override
    public Date getLastReleaseForProductor(Productor productor) {
        QMusic music = QMusic.music;
        JPAQuery query = new JPAQuery(getEntityManager());
        query.from(music)
                .where(
                        music.productors.contains(productor)
                   );

        return query.uniqueResult(music.releaseDate.max());
    }


    @Transactional
    @Override
    public Date getLastReleaseForArtist(Artist artist) {
        QMusic music = QMusic.music;
        JPAQuery query = new JPAQuery(getEntityManager());
        query.from(music)
                .where(
                        music.artists.contains(artist)
                );

        return query.uniqueResult(music.releaseDate.max());
    }

    @Override
    public Page<Music> findMusicByTileAndGenre(String req, Long genre, Pageable pageable) {
        QCategory qGenre = QCategory.category;

        QMusic music = QMusic.music;
        JPAQuery query = new JPAQuery(getEntityManager());
        BooleanExpression predicat = music.title.containsIgnoreCase(req);

        query.from(music);

        if(genre!=null && genre>0){
            predicat = predicat.and(music.categories.any().id.eq(genre));
        }


        query   .where(predicat)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(music.title.asc());


        return new PageImpl<Music>(query.list(music),pageable,query.count());
    }

    @Transactional
    @Override
    public Page<Music> findMusicByTileAndGenreAndisNew(String req, Long genre, Pageable pageable) {
        QCategory qGenre = QCategory.category;

        QMusic music = QMusic.music;
        JPAQuery query = new JPAQuery(getEntityManager());

        LocalDate localDate = new LocalDate();

        BooleanExpression predicat = music.title.containsIgnoreCase(req).and(music.releaseDate.gt(localDate.minusMonths(1).toDate()));

        query.from(music);



        if(genre!=null && genre>0){
            predicat = predicat.and(music.categories.any().id.eq(genre));
        }


        query   .where(predicat)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(music.title.asc());


        return new PageImpl<Music>(query.list(music),pageable,query.count());
    }

    @Transactional
    @Override
    public Page<Music> findMusicByTileAndGenreAndTopDl(String req, Long genre, int limit ,Pageable pageable) {

        QCategory qGenre = QCategory.category;
        QMusic music = QMusic.music;

        JPAQuery queryTopDl = new JPAQuery(getEntityManager());
        queryTopDl.from(music).groupBy(music.id).orderBy(music.id.count().desc()).limit(limit);


        JPAQuery query = new JPAQuery(getEntityManager());


        BooleanExpression predicat = music.title.containsIgnoreCase(req).and(music.id.in(queryTopDl.list(music.id)));

        query.from(music);

        if(genre!=null && genre>0){
            predicat = predicat.and(music.categories.any().id.eq(genre));
        }


        query   .where(predicat)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(music.title.asc());


        return new PageImpl<Music>(query.list(music),pageable,query.count());
    }


    @Transactional
    @Override
    public Page<Music> findMusicByTile(String req, Pageable pageable) {
        QMusic music = QMusic.music;
        JPAQuery query = new JPAQuery(getEntityManager());
        query.from(music)
                .where(
                        music.title.containsIgnoreCase(req)
                );

        query
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(music.title.asc());


        return new PageImpl<Music>(query.list(music),pageable,query.count());
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
                    helper.criteriaHelper.equal(helper.rootHelper.join(Music_.categories).get(Category_.id), idGenre)
            );
		}
		
		return helper.getResultList();
	}

    @Transactional
    @Override
	public List<Music> searchBestMusicDownload(Long idGenre,int max) throws Exception {

        List<Object[]> results = (List<Object[]>) getEntityManager()
                .createQuery("SELECT m.id AS id, COUNT(m.id) AS total FROM Music AS m GROUP BY m.id ORDER BY COUNT (m.id) DESC")
                .setMaxResults(max)
                .getResultList();


        List<Long> idMedias = new ArrayList<Long>();

        for (Object[] result : results) {
            Long id = (Long) result[0];
            idMedias.add(id);
        }


        CriteriaBuilderHelper<Music> helper = new CriteriaBuilderHelper(getEntityManager(),Music.class);

        if (idGenre != null && idGenre != -1) {
            helper.criteriaHelper.and(
                    helper.criteriaHelper.equal(helper.rootHelper.join(Music_.categories).get(Category_.id), idGenre)
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

    @Override
    public List<Media> getByIds(List<Long> mediaIds) {
        QMedia media = QMedia.media;
        JPAQuery query = new JPAQuery(getEntityManager());
        query.from(media)
                .where(
                        media.id.in(mediaIds)
                );

        return query.list(media);

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
                    helper.criteriaHelper.equal(helper.rootHelper.join(Music_.categories).get(Category_.id), idGenre)
            );
        }

        return helper.getResultList(0,max);

	}


}
