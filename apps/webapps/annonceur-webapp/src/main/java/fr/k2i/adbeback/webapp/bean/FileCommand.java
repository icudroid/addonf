package fr.k2i.adbeback.webapp.bean;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: dimitri
 * Date: 11/01/13
 * Time: 16:14
 * To change this template use File | Settings | File Templates.
 */
@Data
public class FileCommand implements Serializable{
    private String fileName;
    private Long size;
    private byte[]content;
    public FileCommand(String name){
        this.fileName = name;
    }
    public FileCommand(){}
    public FileCommand(MultipartFile file) throws IOException {
        content = file.getBytes();
        fileName = file.getOriginalFilename();
        size = file.getSize();
    }

}
