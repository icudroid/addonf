package fr.k2i.adbeback.webapp.bean;

import lombok.Data;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Data
public class CartBean implements Serializable{
	private static final long serialVersionUID = -4552013561893862429L;
	private Set<MediaLineBean> lines = new HashSet<MediaLineBean>();
	private Integer minScore = 0;
	private Integer maxTime;
	private Integer nbProduct = 0;
	private String error;
}
