package fr.k2i.adbeback.dao.bean;

import fr.k2i.adbeback.core.business.ad.rule.AdService;
import lombok.Data;

/**
 * User: dimitri
 * Date: 24/04/14
 * Time: 16:05
 * Goal:
 */
@Data
public class Statistics{
    private AdService service;
    private Long count;

    public Statistics(AdService service, Long count) {
        this.service = service;
        this.count = count;
    }
}
