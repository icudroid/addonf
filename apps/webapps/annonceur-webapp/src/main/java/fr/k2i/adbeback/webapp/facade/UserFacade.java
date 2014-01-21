package fr.k2i.adbeback.webapp.facade;

import fr.k2i.adbeback.core.business.ad.Brand;
import fr.k2i.adbeback.core.business.player.Player;
import fr.k2i.adbeback.dao.IPlayerDao;
import fr.k2i.adbeback.dao.jpa.BrandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 * User: dimitri
 * Date: 08/11/13
 * Time: 17:36
 * To change this template use File | Settings | File Templates.
 */
@Component
public class UserFacade {

    @Autowired
    private BrandRepository brandRepository;

    @Value("${addonf.logo.location}")
    private String logoPath;


    @Transactional
    public Brand getCurrentUser() throws Exception{
        Object principal = getAuthenticationPlayer().getPrincipal();
        if (!(principal instanceof Brand)) {
            throw new Exception("Please check configuration. Should be Brand in the principal.");
        }

        return brandRepository.findOne(((Brand) principal).getId());
    }

    protected Authentication getAuthenticationPlayer() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    @Transactional
    public void setLogo(MultipartFile logo) throws Exception {
        Brand currentUser = getCurrentUser();
        currentUser.setLogo(saveFile(logo.getBytes(),logoPath));
    }


    /**
     *
     * @param content
     * @return
     * @throws java.io.IOException
     */
    private String saveFile(byte[] content,String base) throws IOException {
        return saveFile(content, UUID.randomUUID().toString(),base);
    }


    /**
     *
     * @param content
     * @param path
     * @return
     * @throws IOException
     */
    private String saveFile(byte[] content, String path,String base)  throws IOException{
        String completePath = base + File.separator + path;
        FileOutputStream fos = new FileOutputStream(completePath);
        fos.write(content);
        fos.close();
        return path;
    }
}
