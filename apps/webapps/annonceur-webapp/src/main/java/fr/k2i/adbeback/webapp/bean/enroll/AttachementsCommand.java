package fr.k2i.adbeback.webapp.bean.enroll;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;


@Data
public class AttachementsCommand {
    Map<String,AttachementsCommand> files = new HashMap<String,AttachementsCommand>();
}
