package fr.k2i.adbeback.webapp.bean.enroll;

import fr.k2i.adbeback.webapp.bean.FileCommand;
import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;


@Data
public class AttachementsCommand implements Serializable{
    Map<String,FileCommand> files = new HashMap<String,FileCommand>();
}
