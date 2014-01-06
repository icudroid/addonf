package fr.k2i.adbeback.webapp.facade;

import fr.k2i.adbeback.core.business.media.Album;
import fr.k2i.adbeback.core.business.media.Media;
import fr.k2i.adbeback.core.business.media.Music;
import fr.k2i.adbeback.dao.IMediaDao;
import fr.k2i.adbeback.webapp.bean.MediaLineBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: dev
 * Date: 06/01/14
 * Time: 00:33
 * To change this template use File | Settings | File Templates.
 */
@Component
public class CartFacade {


    @Autowired
    private IMediaDao mediaDao;

    @Transactional
    public MediaLineBean getMediaLineBean(Long idMedia) throws Exception {
        Media media = mediaDao.get(idMedia);

        MediaLineBean line = new MediaLineBean();

        line.setIdMedia(idMedia);
        line.setTitle(media.getTitle());

        if (media instanceof Album) {
            Album album = (Album) media;
            line.setType(MediaLineBean.ALBUM_TYPE);
            List<Music> musics = album.getMusics();
            Integer nbAds = 0;
            for (Music music : musics) {
                MediaLineBean lineMusic = new MediaLineBean();
                lineMusic.setAdNeeded(music.getNbAdsNeeded());
                lineMusic.setIdMedia(music.getId());
                lineMusic.setTitle(music.getTitle());
                lineMusic.setType(MediaLineBean.MUSIC_TYPE);
                nbAds+=music.getNbAdsNeeded();
                line.getMedias().add(lineMusic);
            }
            line.setAdNeeded(media.getNbAdsNeeded());
        }else if (media instanceof Music) {
            line.setType(MediaLineBean.MUSIC_TYPE);
            line.setAdNeeded(media.getNbAdsNeeded());
        }


        return line;
    }
}
