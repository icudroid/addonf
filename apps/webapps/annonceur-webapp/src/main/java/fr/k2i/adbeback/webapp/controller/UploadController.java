package fr.k2i.adbeback.webapp.controller;

import fr.k2i.adbeback.webapp.bean.FileCommand;
import lombok.Data;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;


@Controller  
public class UploadController {
    protected final Log logger = LogFactory.getLog(this.getClass());

    public static final String UPLOADED_PARAM = "uploaded";

    public static final String PDF = "pdf";
    public static final String JPG = "jpg";
    public static final String JPEG = "jpeg";
    public static final String DOC = "doc";
    public static final String DOCX = "docx";
    public static final String PNG = "png";

    @Value("${upload.max.size:1.5}")
    private Double maxBytesUpload;

    @RequestMapping(value= IMetaDataController.Path.UPLOAD,method = RequestMethod.POST)
    public @ResponseBody JsonResultError doUpload(@PathVariable String fileId,@RequestParam("file") MultipartFile file,HttpServletRequest request, HttpServletResponse response) throws IOException {

        Map<String,FileCommand>  uploaded = (Map)request.getSession().getAttribute(UPLOADED_PARAM);
        if(uploaded==null){
            uploaded =  new HashMap<String,FileCommand>();
            request.getSession().setAttribute(UPLOADED_PARAM,uploaded);
        }


        if(!isFileSizeOk(file)){
            return JsonResultError.errorSize();
        }else if(!isFileFormatOk(file)){
            return  JsonResultError.errorFormat();
        }else{
            try {
                uploaded.put(fileId,new FileCommand(file));
            } catch (IOException e) {
                logger.error("doUpload",e);
            }
            return  JsonResultError.noError();
        }

    }

    private boolean isFileSizeOk(MultipartFile file) {
        return file.getSize()>0 && file.getSize()<=(maxBytesUpload*1024*1024);
    }

    private boolean isFileFormatOk(MultipartFile file) {
        String originalName = file.getOriginalFilename();
        int dot = originalName.lastIndexOf(".");
        String ext = originalName.substring(dot + 1);

        return (PNG.equalsIgnoreCase(ext) || PDF.equalsIgnoreCase(ext) || JPG.equalsIgnoreCase(ext) || JPEG.equalsIgnoreCase(ext) || DOC.equalsIgnoreCase(ext) || DOCX.equalsIgnoreCase(ext)) ;
    }



    @ExceptionHandler(Exception.class)
    public JsonResultError handleError(HttpServletRequest req, Exception exception) {
        return JsonResultError.errorSize();
    }

}


@Data
class JsonResultError implements Serializable{
    String error;

    public JsonResultError(String e){
        error = e;
    }

    static JsonResultError errorSize(){
        return new JsonResultError("size");
    }

    static JsonResultError errorFormat(){
        return new JsonResultError("format");
    }

    static JsonResultError noError(){
        return new JsonResultError("no");
    }


}