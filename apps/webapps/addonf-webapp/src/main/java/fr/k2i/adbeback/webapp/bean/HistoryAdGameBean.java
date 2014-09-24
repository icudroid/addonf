package fr.k2i.adbeback.webapp.bean;

import lombok.Data;
import lombok.experimental.Builder;

import java.io.Serializable;
import java.util.Date;

/**
 * User: dimitri
 * Date: 24/09/14
 * Time: 16:40
 * Goal:
 */
 @Data
 @Builder
public class HistoryAdGameBean implements Serializable {
    private Date generated;
    private Integer adAmount;
}
