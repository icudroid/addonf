package fr.k2i.adbeback.bean;

import fr.k2i.adbeback.core.business.ad.rule.AdService;
import lombok.Data;

import java.util.List;

/**
 * User: dimitri
 * Date: 23/04/14
 * Time: 11:37
 * Goal:
 */
@Data
public  class ResponseUser {
    private List<Long> responses;
    private boolean correct;
    private AdService adService;

    public ResponseUser(boolean correct, List<Long> responses,AdService service) {
        this.correct = correct;
        this.responses = responses;
        this.adService = service;
    }
}