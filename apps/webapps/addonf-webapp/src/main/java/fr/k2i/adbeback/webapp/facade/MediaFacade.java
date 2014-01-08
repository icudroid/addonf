package fr.k2i.adbeback.webapp.facade;

import fr.k2i.adbeback.core.business.media.*;
import fr.k2i.adbeback.core.business.push.HomePush;
import fr.k2i.adbeback.dao.IHomePushDao;
import fr.k2i.adbeback.dao.IMediaDao;
import fr.k2i.adbeback.webapp.bean.PersonBean;
import fr.k2i.adbeback.webapp.bean.media.AlbumBean;
import fr.k2i.adbeback.webapp.bean.media.MediaBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: dev
 * Date: 05/01/14
 * Time: 15:42
 * To change this template use File | Settings | File Templates.
 */
@Component
public class MediaFacade {

    @Autowired
    private IMediaDao mediaDao;

    @Autowired
    private IHomePushDao homePushDao;



    @Transactional
    public List<MediaBean> getBestMusicDownload(Long idGenre) throws Exception {
        return construstBeanList(mediaDao.searchBestMusicDownload(idGenre));
    }


    @Transactional
    public List<MediaBean> getHomePush() throws Exception {
        List<HomePush> pushes = homePushDao.findActualPushHome();

        if(pushes!=null && !pushes.isEmpty()){
            List<MediaBean> medias = new ArrayList<MediaBean>();
            for (HomePush push : pushes) {
                medias.add(contructMediaBean(push.getMedias()));
            }
            return medias;
        }else {
            return getBestMusicDownload(null);
        }
    }


    private List<MediaBean> construstBeanList(List<? extends Media> medias)throws Exception {
        List<MediaBean> res = new ArrayList<MediaBean>();
        for (Media media : medias) {
            MediaBean bean = contructMediaBean(media);
            res.add(bean);
        }

        return res;
    }

    private MediaBean contructMediaBean(Media media) {
        MediaBean bean = new MediaBean();
        bean.setDescription(media.getDescription());
        bean.setDuration(media.getDuration());
        bean.setGenres(media.getGenres());
        bean.setId(media.getId());
        bean.setJacket(media.getJacket());
        bean.setReleaseDate(media.getReleaseDate());
        bean.setThumbJacket(media.getThumbJacket());
        bean.setTitle(media.getTitle());
        bean.setNbAdsNeeded(media.getNbAdsNeeded());
        if(media instanceof Music){
            List<Album> albums = ((Music) media).getAlbums();
            List<AlbumBean> albumBeans = new ArrayList<AlbumBean>();
            for (Album album : albums) {
                albumBeans.add(new AlbumBean(album.getId(),album.getTitle()));
            }
            bean.setAlbums(albumBeans);
        }



        List<PersonBean> productors = new ArrayList<PersonBean>();
        for (Productor p : media.getProductors()) {
            productors.add(new PersonBean(p.getId(),p.getFirstName(), p.getLastName()));
        }
        bean.setProductors(productors);

        if (media instanceof Music) {
            Music music = (Music) media;
            List<PersonBean> artists = new ArrayList<PersonBean>();
            for (Artist a : music.getArtists()) {
                artists.add(new PersonBean(a.getId(), a.getFirstName(), a.getLastName()));
            }

            bean.setArtists(artists);
        }
        return bean;
    }

    public MediaBean getMediaById(Long musicId) {
        Media media = mediaDao.get(musicId);
        return contructMediaBean(media);
    }
}
