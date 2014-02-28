package fr.k2i.adbeback.webapp;

import fr.k2i.adbeback.core.business.media.*;
import fr.k2i.adbeback.dao.ICategoryDao;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

/**
 * User: dimitri
 * Date: 27/02/14
 * Time: 14:37
 * Goal:
 */
public class Tmp {


/*    @Autowired
    private ICategoryDao categoryDao;*/


/*    public static void main(String[] args) throws IOException, ParseException {
        Tmp tmp = new Tmp();
        tmp.aVoid();
    }*/

    public void aVoid() throws IOException, ParseException {


        System.setProperty("http.proxyHost", "172.16.1.254");
        System.setProperty("http.proxyPort", "8080");

        Document doc = Jsoup.connect("http://musique.fnac.com/a6742803/Jul-Dans-ma-paranoia-CD-album").get();
        Elements scripts = doc.select("script");


        String picture = null,
                albumName= null,
                albumDesc= null,
                artistName= null,
                productorName= null,
                date = null;

        for (Element script : scripts) {
            if(script.html().contains("tc_vars[\"product_name\"]")){
                String[] lines = script.html().split("\r\n");
                for (String line : lines) {
                    if(line.trim().startsWith("tc_vars[\"product_name\"]")){
                        albumName = line.trim().replaceAll("^tc_vars\\[\"product_name\"\\] = \"(.*)\";", "$1");
                    }
                    if(line.trim().startsWith("tc_vars[\"product_picture_url\"]")){
                        picture = line.trim().replaceAll("^tc_vars\\[\"product_picture_url\"\\] = \"(.*)\";", "$1");
                    }

                }
                break;
            }
        }

        albumDesc = doc.select("#FAsummaries .comment p").text();

        Elements elts = doc.select("#propertiesArticle li.clearfix span.value");


        int index = 0;
        for (Element elt : elts) {
            switch (index){
                case 0:
                    artistName = elt.text();
                    break;
                case 1:
                    productorName = elt.text();
                    break;
            }
            index++;
        }

        date = doc.select("div.audio section div.date").text();


        SimpleDateFormat sdf = new SimpleDateFormat("'Paru le 'dd MMMM yyyy", Locale.FRENCH);
        Date releaseDate = sdf.parse(date);



        Artist artist = new Artist();
        artist.setLastName(artistName);

        Productor productor = new Productor();
        productor.setLastName(productorName);

        productor.addArtist(artist);

        URL url = new URL(picture);
        InputStream inputStream = url.openStream();
        String jacket = UUID.randomUUID().toString();
        FileWriter fr = new FileWriter(new File("/tmp/"+ jacket));

        String mp3 = "f1.mp3";

        Album album = createAlbum(albumName,artist, jacket, releaseDate);




        String titles[] = {"Winners","jul ft Kalif Hardcore -...","Dans Mon Del","Grille","J'oublie Tout","Au Quartier","Jul ft Soso Maness -...","Sort Le Cross Vole","N'importe Quoi",
                            "Dans Ma Paranoia","Tu La Love",""};

        album.addMusic(createMusic("Winners",artist, productor, jacket, mp3, releaseDate),1);






    }

    private Album createAlbum(String title,Artist artist, String jacket, Date releaseDate) {
        Album album = new Album();
        album.setTitle(title);
        album.setImgPresentation(jacket);
        album.setJacket(jacket);
        album.setReleaseDate(releaseDate);
        album.addArtists(artist);
        return album;
    }

    private Music createMusic(String title,Artist artist, Productor productor, String jacket, String mp3, Date releaseDate) {
        Music music = new Music();
        //music.addCategory(categoryDao.get(1L));
        music.addArtists(artist);
        music.setMp3Sample(mp3);
        music.setTitle(title);
        music.setFile(mp3);
        music.setImgPresentation(jacket);
        music.setJacket(jacket);
        music.setThumbJacket(jacket);
        music.setNbAdsNeeded(3);
        music.setReleaseDate(releaseDate);
        music.addProductor(productor);
        return music;
    }
}
