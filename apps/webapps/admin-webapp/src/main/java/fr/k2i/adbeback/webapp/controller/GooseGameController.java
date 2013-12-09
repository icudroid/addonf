package fr.k2i.adbeback.webapp.controller;

import fr.k2i.adbeback.core.business.goosegame.GooseLevel;
import fr.k2i.adbeback.dao.jpa.GooseLevelDao;
import fr.k2i.adbeback.webapp.bean.GooseLevelGame;
import fr.k2i.adbeback.webapp.facade.GooseGameFacade;
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
        return gooseGameFacade.search(searchBean.getLevel(), searchBean.getMultiple());
    }


    @RequestMapping(value = "manage/gooseGame/level/{levelId}",method  = RequestMethod.GET)
    public @ResponseBody GooseLevelGame level(@PathVariable Long levelId){
        return gooseGameFacade.getGooseLevel(levelId);
    }

    @RequestMapping(value = "/manage/gooseGame/create",method  = RequestMethod.POST)
    public @ResponseBody GooseLevelGame create(@RequestBody CreateBean createBean){

        if(createBean.getMultiple()==true){
            return gooseGameFacade.generateMultiLevel(createBean.getStrong(),createBean.getLevel(), createBean.getMinAmount());
        }else{
            return gooseGameFacade.generateSingleLevel(createBean.getNbCase(),createBean.getLevel());
        }
    }


    @RequestMapping(value = "manage/gooseGame/modify/{caseId}/{type}",method  = RequestMethod.GET)
    public void modify(@PathVariable Long caseId,@PathVariable Integer type){
        gooseGameFacade.modifyCaseType(caseId,type);
    }


    @RequestMapping(value = "manage/gooseGame/modifyJump/{caseId}/{jumpToNumber}",method  = RequestMethod.GET)
    public @ResponseBody String modifyJump(@PathVariable Long caseId, @PathVariable Integer jumpToNumber){
        gooseGameFacade.modifyCaseTypeToJump(caseId,jumpToNumber);
        return "";
    }



}


class SearchBean implements Serializable{
    private Boolean multiple;
    private Integer level;

    Boolean getMultiple() {
        return multiple;
    }

    void setMultiple(Boolean multiple) {
        this.multiple = multiple;
    }

    Integer getLevel() {
        return level;
    }

    void setLevel(Integer level) {
        this.level = level;
    }
}



class CreateBean implements Serializable{
    private Boolean multiple;
    private Integer level;
    private Integer strong;
    private Integer nbCase;
    private Integer minAmount;

    Integer getMinAmount() {
        return minAmount;
    }

    void setMinAmount(Integer minAmount) {
        this.minAmount = minAmount;
    }

    Integer getStrong() {
        return strong;
    }

    void setStrong(Integer strong) {
        this.strong = strong;
    }

    Integer getNbCase() {
        return nbCase;
    }

    void setNbCase(Integer nbCase) {
        this.nbCase = nbCase;
    }

    Boolean getMultiple() {
        return multiple;
    }

    void setMultiple(Boolean multiple) {
        this.multiple = multiple;
    }

    Integer getLevel() {
        return level;
    }

    void setLevel(Integer level) {
        this.level = level;
    }
}

