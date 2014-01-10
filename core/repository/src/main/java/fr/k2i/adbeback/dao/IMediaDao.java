package fr.k2i.adbeback.dao;

import fr.k2i.adbeback.core.business.media.Artist;
import fr.k2i.adbeback.core.business.media.Media;
import fr.k2i.adbeback.core.business.media.Music;
import fr.k2i.adbeback.core.business.media.Productor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * User Data Access Object (GenericDao) interface.
 *
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 */
public interface IMediaDao extends IGenericDao<Media, Long> {


    @Transactional
    List<Music> findMusicBy(String str, Long idGenre) throws Exception;


    @Transactional
    List<Music> searchNewMusic(Long idGenre, String str, int max)
			throws Exception;

    @Transactional
    Page<String> findMusicAndAlbumTitleByTitle(String str, Pageable pageable) throws Exception;

    @Transactional
    Page<String> findPersonFullNameByName(String req, Pageable pageable);

    @Transactional
    Page<Artist> findArtistByFullName(String req, Pageable pageable);

    @Transactional
    Page<Productor> findProductorByFullName(String req, Pageable pageable);

    @Transactional
    Page<Music> findMusicByTile(String req, Pageable pageable);

    @Transactional
    List<Music> searchBestMusicDownload(Long idGenre, int max) throws Exception;

    @Transactional
    List<Media> getByIds(List<Long> mediaIds);
}

