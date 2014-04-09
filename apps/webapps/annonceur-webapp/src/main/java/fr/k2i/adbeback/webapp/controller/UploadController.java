package fr.k2i.adbeback.webapp.controller;

import fr.k2i.adbeback.webapp.bean.FileCommand;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJacksonJsonView;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;


@Controller  
public class UploadController implements HandlerExceptionResolver, Ordered {
    protected final Log logger = LogFactory.getLog(this.getClass());

    public static final String UPLOADED_PARAM = "uploaded";

    public static final String TEXT_PLAIN = "text/plain";

    public static final String PDF = "pdf";
    public static final String JPG = "jpg";
    public static final String JPEG = "jpeg";
    public static final String DOC = "doc";
    public static final String DOCX = "docx";
    public static final String PNG = "png";

    @Value("${upload.max.size:1.5}")
    private Double maxBytesUpload;

    @RequestMapping(value= IMetaDataController.Path.UPLOAD,method = RequestMethod.POST)
    public ModelAndView doUpload(@RequestParam String file,@RequestBody MultipartFile multipartFile,HttpServletRequest request, HttpServletResponse response) throws IOException {
        ModelAndView modelAndView = new ModelAndView(new MappingJacksonJsonView());

        response.setContentType(TEXT_PLAIN);
        response.setCharacterEncoding("UTF-8");

        Map<String,FileCommand>  uploaded = (Map)request.getSession().getAttribute(UPLOADED_PARAM);
        if(uploaded==null){
            uploaded =  new HashMap<String,FileCommand>();
            request.getSession().setAttribute(UPLOADED_PARAM,uploaded);
        }


        if(!isFileSizeOk(multipartFile)){
            modelAndView.addObject(JsonResultError.errorSize());
        }else if(!isFileFormatOk(multipartFile)){
            modelAndView.addObject(JsonResultError.errorFormat());
        }else{
            try {
                uploaded.put(file,new FileCommand(multipartFile));
            } catch (IOException e) {
                logger.error("doUpload",e);
            }
            modelAndView.addObject(JsonResultError.noError());
        }

        return modelAndView;
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


    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        ModelAndView modelAndView = new ModelAndView(new MappingJacksonJsonView());
        modelAndView.addObject(JsonResultError.errorSize());
        return modelAndView;
    }

    @Override
    public int getOrder() {
        return 0;
    }
}


class JsonResultError{
    Object error;

    Object getError() {
        return error;
    }

    void setError(Object error) {
        this.error = error;
    }

    public JsonResultError(Object e){
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