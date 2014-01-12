package fr.k2i.adbeback.dao.jpa;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.jpa.impl.JPAUpdateClause;
import fr.k2i.adbeback.core.business.ad.Ad_;
import fr.k2i.adbeback.core.business.ad.QAd;
import fr.k2i.adbeback.core.business.ad.rule.*;
import fr.k2i.adbeback.core.business.media.QCategory;
import fr.k2i.adbeback.core.business.media.QMusic;
import fr.k2i.adbeback.core.business.player.Address;
import fr.k2i.adbeback.core.business.player.Player;
import fr.k2i.adbeback.core.business.player.Sex;
import fr.k2i.adbeback.dao.utils.CriteriaBuilderHelper;
import org.joda.time.LocalDate;
import org.joda.time.Years;
import org.springframework.stereotype.Repository;

import fr.k2i.adbeback.core.business.ad.Ad;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Query;

/**
 * This class interacts with Spring's HibernateTemplate to save/delete and
 * retrieve User objects.
 *
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 *   Modified by <a href="mailto:dan@getrolling.com">Dan Kibler</a>
 *   Extended to implement Acegi UserDetailsService interface by David Carter david@carter.net
 *   Modified by <a href="mailto:bwnoll@gmail.com">Bryan Noll</a> to work with 
 *   the new BaseDaoHibernate implementation that uses generics.
*/
@Repository("adDao")
public class AdDao extends GenericDaoJpa<Ad, Long> implements fr.k2i.adbeback.dao.IAdDao {

    /**
     * Constructor that sets the entity to User.class.
     */
    public AdDao() {
        super(Ad.class);
    }


    @Transactional
    @Override
    public List<Ad> getAllValideFor(Player player) {

        /*
            Les règles sont les suivantes :
                - Si l'annonceur a mit des critères par publicités (Age, Sex, Ville , Pays)
                - Une publicité ne doit pas être vu plus de X fois configurable par l'annonceur


         */

        LocalDate date = new LocalDate();

        Query query = getEntityManager().createQuery("select ad from Ad ad inner join ad.rules as adRule with adRule.class = CountryRule where ad.startDate <= :date and ad.endDate >= :date and adRule.country.id = :countryId")
                .setParameter("countryId",player.getAddress().getCity().getCountry().getId())
                .setParameter("date", date.toDate());


        return matchRules(query.getResultList(),player);
    }

    @Transactional
    private List<Ad> matchRules(List<Ad> ads,Player player) {
        List<Ad> res = new ArrayList<Ad>();

        res = matchAmount(ads);
        res = matchSex(res,player);
        res = matchCity(res,player);
        res = matchAge(res,player);

        return res;
    }

    private List<Ad> matchAge(List<Ad> ads,Player player) {
        List<Ad> res = new ArrayList<Ad>();
        Date birthday = player.getBirthday();
        LocalDate now = new LocalDate();
        Years age = Years.yearsBetween(new LocalDate(birthday), now);

        for (Ad ad : ads) {
            List<AgeRule> rules = ad.getRules(AgeRule.class);
            if(!rules.isEmpty()){
                for (AgeRule rule : rules) {
                    Integer ageMax = rule.getAgeMax();
                    Integer ageMin = rule.getAgeMin();
                    if(age.getYears()>=ageMin && age.getYears()<=ageMax){
                        res.add(ad);
                    }
                }
            }else{
                res.add(ad);
            }
        }

        return res;

    }


    private double distance(double lat1, double lon1, double lat2, double lon2, char unit) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        if (unit == 'K') {
            dist = dist * 1.609344;
        } else if (unit == 'N') {
            dist = dist * 0.8684;
        }
        return (dist);
    }

    /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
/*::  This function converts decimal degrees to radians             :*/
/*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
/*::  This function converts radians to decimal degrees             :*/
/*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
    private double rad2deg(double rad) {
        return (rad * 180 / Math.PI);
    }




    /**
     * Todo : match with around
     * @param ads
     * @param player
     * @return
     */
    private List<Ad> matchCity(List<Ad> ads,Player player) {
        List<Ad> res = new ArrayList<Ad>();

        for (Ad ad : ads) {
            List<CityRule> rules = ad.getRules(CityRule.class);
            if(!rules.isEmpty()){
                for (CityRule rule : rules) {
                    if(rule.getCity().equals(player.getAddress().getCity())){
                        res.add(ad);
                    }
                }
            }else{
                res.add(ad);
            }
        }

        return res;

    }

    private List<Ad> matchSex(List<Ad> ads,Player player) {
        List<Ad> res = new ArrayList<Ad>();

        for (Ad ad : ads) {
            List<SexRule> rules = ad.getRules(SexRule.class);
            if(!rules.isEmpty()){
                for (SexRule rule : rules) {
                    if(rule.getSex().equals(player.getSex())){
                        res.add(ad);
                    }
                }
            }else{
                res.add(ad);
            }
        }

        return res;

    }

    private List<Ad> matchAmount(List<Ad> ads) {
        List<Ad> res = new ArrayList<Ad>();

        for (Ad ad : ads) {
            List<AmountRule> rules = ad.getRules(AmountRule.class);
            if(!rules.isEmpty()){
                if(rules.get(0).getAmount()>1){
                    res.add(ad);
                }
            }
        }
        return res;

    }


    @Transactional
    @Override
    public void updatetAmountForAd(AdService adRule){
        QAmountRule qAmountRule = QAmountRule.amountRule;

        Double price = adRule.getPrice();
        AmountRule amountRule = adRule.getAd().getRules(AmountRule.class).get(0);

        Query query = getEntityManager().createQuery("update AmountRule set amount=amount-:amount where id = :id")
                .setParameter("amount", price)
                .setParameter("id", amountRule.getId());

        query.executeUpdate();

    }



}

