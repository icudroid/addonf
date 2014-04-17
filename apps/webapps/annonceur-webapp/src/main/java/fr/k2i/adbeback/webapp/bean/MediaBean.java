package fr.k2i.adbeback.webapp.bean;

import lombok.Data;

import java.io.Serializable;

/**
 * User: dimitri
 * Date: 17/04/14
 * Time: 15:07
 * Goal:
 */

@Data
public class MediaBean implements Serializable{
    private Long id;
    private String name;

    public MediaBean(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MediaBean)) return false;

        MediaBean mediaBean = (MediaBean) o;

        if (id != null ? !id.equals(mediaBean.id) : mediaBean.id != null) return false;
        if (name != null ? !name.equals(mediaBean.name) : mediaBean.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}
