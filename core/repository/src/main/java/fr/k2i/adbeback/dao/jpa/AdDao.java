package fr.k2i.adbeback.dao.jpa;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.querydsl.jpa.impl.JPAQuery;
import fr.k2i.adbeback.core.business.ad.*;
import fr.k2i.adbeback.core.business.ad.rule.*;
import fr.k2i.adbeback.core.business.country.City;
import fr.k2i.adbeback.core.business.player.Player;
import fr.k2i.adbeback.core.business.user.*;
import fr.k2i.adbeback.date.DateUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

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
    public List<Ad> getAllValidFor(Player player) {

        /*
            Les règles sont les suivantes :
                - Si l'annonceur a mit des critères par publicités (Age, Sex, Ville , Pays)
                - Une publicité ne doit pas être vu plus de X fois configurable par l'annonceur


         */

        LocalDate date = LocalDate.now();

        City city = player.getAddress().getCity();
        Long countryId = null;
        if(city==null){
            countryId = player.getAddress().getCountry().getId();
        }else{
            countryId = city.getCountry().getId();
        }


        QAd ad = QAd.ad;
        QAdRule adRule = QAdRule.adRule;
        QCountryRule countryRule = adRule.as(QCountryRule.class);

        JPAQuery query = new JPAQuery(getEntityManager());
        query.from(ad).join(ad.rules,adRule).on(adRule.instanceOf(countryRule.getType()))
                .where(
                        ad.startDate.loe(DateUtils.asDate(date))
                                .and(ad.endDate.goe(DateUtils.asDate(date)))
                                .and(countryRule.country.id.eq(countryId))
                );




        /*Query query = getEntityManager().createQuery("select ad from Ad ad inner join ad.rules as adRule with adRule.class = CountryRule where ad.startDate <= :date and ad.endDate >= :date and adRule.country.id = :countryId")
                .setParameter("countryId", countryId)
                .setParameter("date", date.toDate());*/


        return matchRules(query.select(ad).fetch(),player);
    }



    @Override
    public List<Ad> getAllValidForAndProvidedBy(Player player, Media media) {

        /*
            Les règles sont les suivantes :
                - Si l'annonceur a mit des critères par publicités (Age, Sex, Ville , Pays)
                - Une publicité ne doit pas être vu plus de X fois configurable par l'annonceur


         */

        LocalDate date = LocalDate.now();

        QAd ad = QAd.ad;
        QAdRule adRule = QAdRule.adRule;
        QCountryRule countryRule = adRule.as(QCountryRule.class);

        City city = player.getAddress().getCity();
        Long countryId = null;
        if(city==null){
            countryId = player.getAddress().getCountry().getId();
        }else{
            countryId = city.getCountry().getId();
        }


        JPAQuery query = new JPAQuery(getEntityManager());
        query.from(ad).join(ad.rules,adRule).on(adRule.instanceOf(countryRule.getType()))
                .where(
                        ad.startDate.loe(DateUtils.asDate(date))
                        .and(ad.endDate.goe(DateUtils.asDate(date)))
                        .and(countryRule.country.id.eq(countryId))
                        .and(ad.providedBy.id.eq(media.getId()))
                );


/*        Query query = getEntityManager().createQuery("select ad from Ad ad inner join ad.rules as adRule with adRule.class = CountryRule where ad.startDate <= :date and ad.endDate >= :date and adRule.country.id = :countryId and ad.providedBy.id= :idPartner")
                .setParameter("countryId",player.getAddress().getCity().getCountry().getId())
                .setParameter("date", date.toDate())
                .setParameter("idPartner", media.getId());*/

        return matchRules(query.select(ad).fetch(),player);
    }


    @Transactional
    List<Ad> matchRules(List<Ad> ads,Player player) {
        List<Ad> res = new ArrayList<Ad>();


        res = encoded(ads);
        res = matchAmount(res);
        res = matchSex(res,player);
        res = matchCity(res,player);
        res = matchAge(res,player);
        res = matchService(res, player);

        return res;
    }

    private List<Ad> encoded(List<Ad> ads) {
        List<Ad> res = new ArrayList<Ad>();
        for (Ad ad : ads) {
            if (ad instanceof VideoAd) {
                VideoAd videoAd = (VideoAd) ad;
                if(videoAd.getAdFileEncoded()){
                    res.add(ad);
                }
            }else{
                res.add(ad);
            }
        }
        return res;
    }

    private List<Ad> matchService(List<Ad> ads, Player player) {
        List<Ad> res = new ArrayList<Ad>();

        Date now = new Date();
        for (Ad ad : ads) {
            List<AdService> rules = ad.getRules(AdService.class);
            if(!rules.isEmpty()){
                for (AdService rule : rules) {
                     if(rule.getActivated() && now.after(rule.getStartDate()) && now.before(rule.getEndDate())){
                         res.add(ad);
                         break;
                     }
                }
            }
        }
        return res;
    }

    private List<Ad> matchAge(List<Ad> ads,Player player) {
        List<Ad> res = new ArrayList<Ad>();
        Date birthday = player.getBirthday();
        LocalDate now = LocalDate.now();
        Period period = Period.between(DateUtils.asLocalDate(birthday), now);
        int age = period.getYears();

        for (Ad ad : ads) {
            List<AgeRule> rules = ad.getRules(AgeRule.class);
            if(!rules.isEmpty()){
                for (AgeRule rule : rules) {
                    Integer ageMax = rule.getAgeMax();
                    Integer ageMin = rule.getAgeMin();
                    if(age>=ageMin && age<=ageMax){
                        res.add(ad);
                    }
                }
            }else{
                res.add(ad);
            }
        }

        return res;

    }

    private static double CONVERSION_LATLONG_METRES = 111319.49079327;


    private double get_angle_decalage_geo(double $distance){
        return $distance / CONVERSION_LATLONG_METRES;
    }


    /**
     * Todo : match with around
     * @param ads
     * @param player
     * @return
     */
    private List<Ad> matchCity(List<Ad> ads,Player player) {
        List<Ad> res = new ArrayList<Ad>();

        if(player.getAddress().getCity()==null)return ads;

        Double latPlayer = player.getAddress().getCity().getLat();
        Double lonPlayer = player.getAddress().getCity().getLon();

        for (Ad ad : ads) {
            List<CityRule> rules = ad.getRules(CityRule.class);
            if(!rules.isEmpty()){
                for (CityRule rule : rules) {
                    Integer around = rule.getAround();
                    Double lat = rule.getCity().getLat();
                    Double lon = rule.getCity().getLon();

                    Double latP1 = lat - get_angle_decalage_geo(around*1000);
                    Double lonP1 = lon + get_angle_decalage_geo(around*1000);

                    Double latP3 = lat + get_angle_decalage_geo(around*1000);
                    Double lonP3 = lon - get_angle_decalage_geo(around*1000);

                    if(lonPlayer>lonP1 && lonPlayer<lonP3 && latPlayer>latP1 && latPlayer<latP3){
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

            for (AmountRule rule : rules) {
                if(rule.getAmount() > 1.0){
                    res.add(ad);
                    break;
                }
            }
        }
        return res;

    }


    @Transactional
    @Override
    public void updateAmountForAd(AdService adRule, Double bid){
        QAmountRule qAmountRule = QAmountRule.amountRule;
        List<AmountRule> rules = adRule.getAd().getRules(AmountRule.class);
        for (AmountRule rule : rules) {
            Double amount = rule.getAmount();
            if(amount > 0.0){
                BigDecimal amountBD = new BigDecimal(""+amount);
                BigDecimal bidBD = new BigDecimal(""+bid);
                BigDecimal amountLeftBD = amountBD.subtract(bidBD);
                rule.setAmount(amountLeftBD.doubleValue());
                break;
            }
        }
    }

    @Override
    public List<Ad> findByBrand(Brand brand) {
        QAd qAd = QAd.ad;
        JPAQuery query = new JPAQuery(getEntityManager());
        query.from(qAd).where(qAd.brand.eq(brand));
        return query.select(qAd).fetch();
    }


    @Override
    public List<Ad> findByBrands(List<Brand> brands) {
        QAd qAd = QAd.ad;
        JPAQuery query = new JPAQuery(getEntityManager());
        query.from(qAd).where(qAd.brand.in(brands));
        return query.select(qAd).fetch();
    }

    @Override
    public Page<Ad> findByBrand(Brand brand, Pageable pageable) {
        QAd qAd = QAd.ad;
        JPAQuery query = new JPAQuery(getEntityManager());
        query.from(qAd).where(qAd.brand.eq(brand));

        long count = query.fetchCount();

        query
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize());

        return new PageImpl<Ad>(query.select(qAd).fetch(),pageable,count);
    }


    @Override
    public Page<Ad> findByBrands(List<Brand> brands, Pageable pageable) {
        QAd qAd = QAd.ad;
        JPAQuery query = new JPAQuery(getEntityManager());
        query.from(qAd).where(qAd.brand.in(brands));

        long count = query.fetchCount();

        query
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize());

        return new PageImpl<Ad>(query.select(qAd).fetch(),pageable,count);
    }

    @Override
    public List<Ad> findByMedia(Media media,Date date) {
        QAd qAd = QAd.ad;
        QBidCategoryMedia bidCategoryMedia = QBidCategoryMedia.bidCategoryMedia;
        JPAQuery query = new JPAQuery(getEntityManager());

        query.from(qAd).join(qAd.bidCategoryMedias,bidCategoryMedia).where(bidCategoryMedia.media.eq(media).and(qAd.startDate.lt(date).and(qAd.endDate.gt(date))));

        return query.select(qAd).fetch();
    }


    @Override
    public List<VideoAd> findNoEncodedAd() {
        QVideoAd qAd = QVideoAd.videoAd;
        JPAQuery query = new JPAQuery(getEntityManager());
        query.from(qAd).where(qAd.adFileEncoded.eq(false).or(qAd.adFileEncoded.isNull()));
        return query.select(qAd).fetch();
    }



}

