package fr.k2i.adbeback.dao;

import fr.k2i.adbeback.core.business.media.Media;
import fr.k2i.adbeback.core.business.media.Music;
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
    List<Music> searchBestMusicDownload(Long idGenre) throws Exception;

    @Transactional
    List<Music> searchNewMusic(Long idGenre, String str, int max)
			throws Exception;

    @Transactional
    List<Music> getPushHomeMusic() throws Exception;
}

