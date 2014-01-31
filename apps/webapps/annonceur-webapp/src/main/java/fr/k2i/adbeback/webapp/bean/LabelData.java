package fr.k2i.adbeback.webapp.bean;

import lombok.Data;

import java.io.Serializable;

/**
 * User: dimitri
 * Date: 30/01/14
 * Time: 17:08
 * Goal:
 */
@Data
public class LabelData implements Serializable{
    private String label;
    private Long data;

    public LabelData(String label, Long data) {
        this.label = label;
        this.data = data;
    }
}
