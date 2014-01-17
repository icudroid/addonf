package fr.k2i.adbeback.webapp.controller;

import fr.k2i.adbeback.webapp.bean.CartBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionAttributeStore;

/**
 * Created with IntelliJ IDEA.
 * User: dev
 * Date: 10/01/14
 * Time: 10:53
 * To change this template use File | Settings | File Templates.
 */
@SessionAttributes("cart")
public class AbstractController {

    @ModelAttribute("cart")
    public CartBean cartBean(){
        return new CartBean();
    }


}
