package fr.k2i.adbeback.webapp.controller;

import com.google.common.collect.Lists;
import fr.k2i.adbeback.core.business.media.Artist;
import fr.k2i.adbeback.core.business.media.Music;
import fr.k2i.adbeback.core.business.media.Productor;
import fr.k2i.adbeback.dao.IMediaDao;
import fr.k2i.adbeback.dao.jpa.GenreRepository;
import fr.k2i.adbeback.webapp.bean.CartBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * User: dimitri
 * Date: 07/01/14
 * Time: 11:29
 * Goal:
 */
@Controller
public class SearchController{
    @Value(value ="${addonf.static.url}" )
    private String staticUrl;

    @Autowired
    private IMediaDao mediaDao;
    @Autowired
    private GenreRepository genreRepository;


    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public @ResponseBody Page<String> ajaxSearch(@RequestParam(value = "q") String req) throws Exception {
        Pageable pageable = new PageRequest(0,10);

        Page<String> music = mediaDao.findMusicAndAlbumTitleByTitle(req, pageable);
        Page<String> persons = mediaDao.findPersonFullNameByName(req, pageable);

        List<String> content = new ArrayList<String>();
        content.addAll(music.getContent());
        content.addAll(persons.getContent());

        Page<String> res = new PageImpl<String>(content,pageable,music.getTotalElements()+persons.getTotalElements());
        return res;
    }

    @RequestMapping("/search.html")
    public String search(@RequestParam(value = "q",required = false) String req,Map<String, Object> model,HttpServletRequest request) throws Exception {

        CartBean cart = (CartBean) request.getSession().getAttribute("cart");
        if(cart==null){
            cart = new CartBean();
            request.getSession().setAttribute("cart",cart);
        }
        model.put("cart", cart);
        model.put("staticUrl",staticUrl);
        model.put("categories",genreRepository.findAll());


        if(!StringUtils.isEmpty(req)){
            Pageable pageable = new PageRequest(0,10);

            Page<Artist> artist = mediaDao.findArtistByFullName(req, pageable);
            Page<Productor> productors = mediaDao.findProductorByFullName(req, pageable);
            Page<Music> musics = mediaDao.findMusicByTile(req, pageable);

            model.put("artist",artist);
            model.put("productors",productors);
            model.put("musics",musics);
            model.put("nosearch",false);
        }else{
            model.put("nosearch",true);
        }

        return "search";
    }
}


