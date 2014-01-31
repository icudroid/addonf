package fr.k2i.adbeback.webapp.controller;

import com.google.common.collect.Lists;
import fr.k2i.adbeback.core.business.media.Artist;
import fr.k2i.adbeback.core.business.media.Music;
import fr.k2i.adbeback.core.business.media.Productor;
import fr.k2i.adbeback.dao.ICategoryDao;
import fr.k2i.adbeback.dao.IMediaDao;
import fr.k2i.adbeback.webapp.bean.CartBean;
import fr.k2i.adbeback.webapp.bean.search.ArtistBean;
import fr.k2i.adbeback.webapp.bean.search.MusicBean;
import fr.k2i.adbeback.webapp.bean.search.ProductorBean;
import fr.k2i.adbeback.webapp.bean.search.SearchCommand;
import fr.k2i.adbeback.webapp.facade.MediaFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.*;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
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
    private ICategoryDao categoryDao;

    @Autowired
    private MediaFacade mediaFacade;


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
        model.put("categories",categoryDao.getAll());
        model.put("query", req);
        model.put("showmore", true);

        if(!StringUtils.isEmpty(req)){
            mediaFacade.search(req,model);
            model.put("nosearch",false);
        }else{
            model.put("nosearch",true);
        }

        return "search";
    }



    @RequestMapping("/search/{what}")
    public String search(@PathVariable String what, @ModelAttribute("search")SearchCommand searchCommand,Pageable pageable, Map<String, Object> model,HttpServletRequest request) throws Exception {

        model.put("staticUrl",staticUrl);
        model.put("categories",categoryDao.getAll());
        model.put("showmore", false);

        model.put("pushes", mediaFacade.getHomePush());


        if("music".equals(what)){
            if(!StringUtils.isEmpty(searchCommand.getReq())){
                model.put("musics",mediaFacade.findMusics(searchCommand.getGenreId(),searchCommand.getReq(),pageable));
            }else{
                model.put("musics", new PageImpl<MusicBean>(Lists.<MusicBean>newArrayList()));
            }
            return "search/music";
        }else if("label".equals(what)){
            if(!StringUtils.isEmpty(searchCommand.getReq())){
                model.put("productors",mediaFacade.findProductors(searchCommand.getReq(),pageable));
            }else{
                model.put("productors", new PageImpl<ArtistBean>(Lists.<ArtistBean>newArrayList()));
            }
            return "search/label";
        }else if("artist".equals(what)){
            if(!StringUtils.isEmpty(searchCommand.getReq())){
                model.put("artists",mediaFacade.findArtists(searchCommand.getReq(),pageable));
            }else{
                model.put("artists", new PageImpl<ArtistBean>(Lists.<ArtistBean>newArrayList()));
            }
            return "search/artist";
        }else if("news".equals(what)){
            if(!StringUtils.isEmpty(searchCommand.getReq())){
                model.put("musics",mediaFacade.findNewMusics(searchCommand.getGenreId(),searchCommand.getReq(),pageable));
            }else{
                model.put("musics", new PageImpl<MusicBean>(Lists.<MusicBean>newArrayList()));
            }
            return "search/news";
        }else if("top50".equals(what)){
            if(!StringUtils.isEmpty(searchCommand.getReq())){
                model.put("musics",mediaFacade.findTopMusics(searchCommand.getGenreId(),searchCommand.getReq(),searchCommand.getTop(),pageable));
            }else{
                model.put("musics", new PageImpl<MusicBean>(Lists.<MusicBean>newArrayList()));
            }
            return "search/top50";
        }else{
            return "search";
        }

    }
}


