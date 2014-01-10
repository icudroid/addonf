package fr.k2i.adbeback.webapp.controller.ajax;

import fr.k2i.adbeback.webapp.bean.CartBean;
import fr.k2i.adbeback.webapp.bean.MediaLineBean;
import fr.k2i.adbeback.webapp.facade.CartFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.Set;
import static fr.k2i.adbeback.webapp.facade.AdGameFacade.*;
/**
 * Controller to signup new users.
 *
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 */
@Controller
public class CartWebservice {
    @Autowired
    private CartFacade cartFacade;

    @Value(value ="${addonf.static.url}" )
    private String staticUrl;

    @Value(value = "${addonf.max.in.cart:3}")
    private Integer maxMusicInCart;

    @Autowired
    private MessageSource messageSource;


    @RequestMapping(value="/partial/cart.html",method = RequestMethod.GET)
    public
    String cart(Map<String, Object> model,HttpServletRequest request, HttpServletResponse response) throws Exception {
        CartBean cart = getCart(request, response);
        model.put("cart",cart);
        return "layout/fragment/cart";
    }


    @RequestMapping(value="/partial/cartTable.html",method = RequestMethod.GET)
    public
    String cartTable(Map<String, Object> model,HttpServletRequest request, HttpServletResponse response) throws Exception {
        CartBean cart = getCart(request, response);
        model.put("cart",cart);
        model.put("staticUrl",staticUrl);
        return "layout/fragment/cartTable";
    }


    @RequestMapping(value="/rest/addToCart/{musicId}",method = RequestMethod.GET)
    public  @ResponseBody
    CartBean addToCart(@PathVariable Long musicId , HttpServletRequest request, HttpServletResponse response) throws Exception {
    	CartBean cart = getCart(request, response);
    	cart.setError("");
    	if(cart.getNbProduct()>=maxMusicInCart){
    		cart.setError(messageSource.getMessage("addonf.cart.max",new Object[]{maxMusicInCart},request.getLocale()));
    	}else{
	    	MediaLineBean line = cartFacade.getMediaLineBean(musicId);
	    	if(cart.getLines().contains(line)){
                cart.setError(messageSource.getMessage("addonf.cart.alreadyAdded",new Object[]{maxMusicInCart},request.getLocale()));
	    	}else{
	    		cart.getLines().add(line);
                cart.setError(messageSource.getMessage("addonf.cart.added",new Object[]{maxMusicInCart},request.getLocale()));
	    	}
	    	recalculateAdNeeeded(cart);
    	}
    	
    	return cart;
    }


    private void recalculateAdNeeeded(CartBean cart){
    	Set<MediaLineBean> lines = cart.getLines();
    	Integer nb = 0;
    	Integer nbMedias = 0;
		for (MediaLineBean mediaLineBean : lines) {
            nb+=mediaLineBean.getAdNeeded();
            nbMedias++;
		}
		cart.setNbProduct(nbMedias);
		cart.setMinScore(nb);
    }

    @RequestMapping(value="/rest/removeFromCart/{musicId}",method = RequestMethod.GET)
    public  @ResponseBody
    CartBean removeFromCart(@PathVariable Long musicId,HttpServletRequest request,
            HttpServletResponse response) throws Exception {
    	CartBean cart = getCart(request, response);
    	cart.setError("");
    	Set<MediaLineBean> lines = cart.getLines();
    	MediaLineBean toRemove = null;
		for (MediaLineBean mediaLineBean : lines) {
			if(mediaLineBean.getIdMedia().equals(musicId)){
				toRemove = mediaLineBean;
			}
		}
		lines.remove(toRemove);
		recalculateAdNeeeded(cart);
    	return cart;
    }

    
    @RequestMapping(value="/rest/cart",method = RequestMethod.GET)
    public  @ResponseBody
    CartBean getCart(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
    	CartBean cart = (CartBean) request.getSession().getAttribute(CART);
    	if(cart==null){
    		cart = new CartBean();
    		request.getSession().setAttribute(CART,cart);
    	}
    	return cart;
    }

    @RequestMapping(value="/rest/cart/empty",method = RequestMethod.GET)
    public  @ResponseBody
    String empty(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
		request.getSession().removeAttribute(CART);
    	return null;
    }

}
