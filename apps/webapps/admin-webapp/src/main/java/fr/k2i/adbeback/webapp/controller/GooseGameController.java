package fr.k2i.adbeback.webapp.controller;

import fr.k2i.adbeback.core.business.goosegame.DiceGooseLevel;
import fr.k2i.adbeback.core.business.goosegame.GooseLevel;
import fr.k2i.adbeback.core.business.goosegame.MultiGooseLevel;
import fr.k2i.adbeback.core.business.goosegame.SingleGooseLevel;
import fr.k2i.adbeback.dao.jpa.GooseLevelDao;
import fr.k2i.adbeback.webapp.bean.GooseLevelGame;
import fr.k2i.adbeback.webapp.bean.JsonResultError;
import fr.k2i.adbeback.webapp.facade.GooseGameFacade;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: dev
 * Date: 07/12/13
 * Time: 15:52
 * To change this template use File | Settings | File Templates.
 */
@Controller
public class GooseGameController {

    public enum TypeLevel {
        SINGLE(SingleGooseLevel.class),MULTIPLE(MultiGooseLevel.class),DICE(DiceGooseLevel.class);

        private Class<? extends GooseLevel> levelType;

        public Class<? extends GooseLevel> getLevelType() {
            return levelType;
        }

        TypeLevel(Class<? extends GooseLevel> levelType) {
            this.levelType = levelType;
        }


    }



    @Autowired
    private GooseLevelDao gooseLevelDao;

    @Autowired
    private GooseGameFacade gooseGameFacade;

    
    @RequestMapping("/manage/gooseGame")
    public String show(){
        return "manage/gooseGame/index";
    }


    @RequestMapping("/manage/gooseGame/partials/{html}.html")
    public String partials(@PathVariable String html){
        return "manage/gooseGame/partials/"+html;
    }

    @RequestMapping(value = "/manage/gooseGame/search",method  = RequestMethod.POST)
    public @ResponseBody List<GooseLevelGame> search(@RequestBody SearchBean searchBean,Map<String, Object> model){
        return gooseGameFacade.search(searchBean.getLevel(), searchBean.getType().getLevelType());
    }


    @RequestMapping(value = "manage/gooseGame/level/{levelId}",method  = RequestMethod.GET)
    public @ResponseBody GooseLevelGame level(@PathVariable Long levelId){
        return gooseGameFacade.getGooseLevel(levelId);
    }

    @RequestMapping(value = "/manage/gooseGame/create",method  = RequestMethod.POST)
    public @ResponseBody GooseLevelGame create(@RequestBody CreateBean createBean){

        switch (createBean.getType()){
            case DICE:
                return gooseGameFacade.generateDiceLevel(createBean.getNbCase(), createBean.getLevel());
            case MULTIPLE:
                return gooseGameFacade.generateMultiLevel(createBean.getStrong(),createBean.getLevel(), createBean.getMinAmount());
            case SINGLE:
                return gooseGameFacade.generateSingleLevel(createBean.getNbCase(),createBean.getLevel(),createBean.getNbError());
        }

        return null;
    }


    @RequestMapping(value = "manage/gooseGame/modify/{caseId}/{type}",method  = RequestMethod.GET)
    public @ResponseBody
    JsonResultError modify(@PathVariable Long caseId,@PathVariable Integer type){
        gooseGameFacade.modifyCaseType(caseId,type);
        return JsonResultError.noError();
    }


    @RequestMapping(value = "manage/gooseGame/modifyJump/{caseId}/{jumpToNumber}",method  = RequestMethod.GET)
    public @ResponseBody JsonResultError modifyJump(@PathVariable Long caseId, @PathVariable Integer jumpToNumber){
        gooseGameFacade.modifyCaseTypeToJump(caseId,jumpToNumber);
        return JsonResultError.noError();
    }


    @RequestMapping(value = "manage/gooseGame/delete/{levelId}",method  = RequestMethod.GET)
    public @ResponseBody JsonResultError delete(@PathVariable Long levelId){
        gooseGameFacade.deleteLevel(levelId);
        return JsonResultError.noError();
    }



    @RequestMapping(value = "manage/gooseGame/modifyMinAmount/{levelId}/{amount}",method  = RequestMethod.POST)
    public @ResponseBody JsonResultError modifyMinAmount(@PathVariable Long levelId,@PathVariable Integer amount){
        gooseGameFacade.modifyMinAmount(levelId,amount);
        return JsonResultError.noError();
    }

}


@Data
class SearchBean implements Serializable{
    private GooseGameController.TypeLevel type;
    private Integer level;
}


@Data
class CreateBean implements Serializable{



    private GooseGameController.TypeLevel type;
    private Integer level;
    private Integer strong;
    private Integer nbCase;
    private Integer minAmount;
    private Integer nbError;
}

