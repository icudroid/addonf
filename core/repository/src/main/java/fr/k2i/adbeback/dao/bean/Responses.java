package fr.k2i.adbeback.dao.bean;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: dev
 * Date: 27/04/14
 * Time: 10:33
 * To change this template use File | Settings | File Templates.
 */
@Data
public class Responses implements Serializable {
    private List<String> responses = new ArrayList<String>();
    List statistics = new ArrayList();
}
