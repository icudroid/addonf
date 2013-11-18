package fr.k2i.adbeback.webapp.controller;

import fr.k2i.adbeback.core.business.player.Player;
import fr.k2i.adbeback.core.business.player.Sex;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: dimitri
 * Date: 29/10/13
 * Time: 13:25
 * To change this template use File | Settings | File Templates.
 */
@Controller
@RequestMapping("/signup*")
public class SignupController {


    @ModelAttribute(value = "player")
    @RequestMapping(method = RequestMethod.GET)
    public Player showForm(Map<String, Object> model) {
        model.put("civilities", Sex.values());
        return new Player();
    }


    @RequestMapping(method = RequestMethod.POST)
    public String onSubmit(final Player player, final BindingResult errors, final HttpServletRequest request, final HttpServletResponse response)
            throws Exception {



        return "success-registration";
    }



}
