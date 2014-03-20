package fr.k2i.adbeback.webapp.facade;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

/**
 * User: dimitri
 * Date: 18/03/14
 * Time: 15:48
 * Goal:
 */
public class FileUtils {
    /**
     *
     * @param content
     * @return
     * @throws java.io.IOException
     */
    public static String saveFile(byte[] content,String base) throws IOException {
        return saveFile(content, UUID.randomUUID().toString(),base);
    }


    /**
     *
     * @param content
     * @param path
     * @return
     * @throws IOException
     */
    public static String saveFile(byte[] content, String path,String base)  throws IOException{
        String completePath = base + File.separator + path;
        FileOutputStream fos = new FileOutputStream(completePath);
        fos.write(content);
        fos.close();
        return path;
    }
}
