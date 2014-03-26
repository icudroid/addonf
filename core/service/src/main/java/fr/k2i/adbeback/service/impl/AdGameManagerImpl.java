package fr.k2i.adbeback.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;


import fr.k2i.adbeback.core.business.ad.ViewedAd;
import fr.k2i.adbeback.core.business.ad.rule.*;
import fr.k2i.adbeback.core.business.game.*;
import fr.k2i.adbeback.core.business.goosegame.GooseLevel;
import fr.k2i.adbeback.core.business.goosegame.IMultiGooseLevel;
import fr.k2i.adbeback.core.business.player.Address;
import fr.k2i.adbeback.dao.*;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.k2i.adbeback.core.business.ad.Ad;
import fr.k2i.adbeback.core.business.ad.Brand;
import fr.k2i.adbeback.core.business.ad.Product;
import fr.k2i.adbeback.core.business.player.Player;
import fr.k2i.adbeback.service.AdGameManager;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation of UserManager interface.
 * 
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 */
@Service("adGameManager")
public class AdGameManagerImpl extends GenericManagerImpl<AbstractAdGame, Long>
		implements AdGameManager {


    @Autowired
	private fr.k2i.adbeback.dao.IAdDao adDao;

    @Autowired
	private fr.k2i.adbeback.dao.IBrandDao brandDao;
	
	private fr.k2i.adbeback.dao.IAdGameDao adGameDao;

    @Autowired
	private IPossibilityDao possibilityDao;

    @Autowired
	private IPlayerDao playerDao;

    @Autowired
    private IGooseLevelDao gooseLevelDao;


    @Autowired
    private IViewedAdDao viewedAdDao;

    @Autowired IPartnerDao partnerDao;

	@Autowired
	public void setAdGameDao(fr.k2i.adbeback.dao.IAdGameDao adGameDao) {
		this.dao = adGameDao;
		this.adGameDao = adGameDao;
	}


    @Transactional
	public AbstractAdGame generate(Boolean selfAd, String idPartner, String idTransaction, Long idPlayer, GooseLevel level) throws Exception {

        AbstractAdGame game = null;

        if(level instanceof IMultiGooseLevel){
            game = new AdGame();
        }else{
            game = new AdGameTransaction();
            ((AdGameTransaction)game).setIdTransaction(idTransaction);
            ((AdGameTransaction)game).setPartner(partnerDao.findbyExtId(idPartner));
        }


		//game.setMinScore(level.getMinScore());

		game.setGenerated(new Date());

		AdScore score = new AdScore();
		score.setScore(0);
		game.setScore(score);

        Player player = playerDao.get(idPlayer);
        game.setChoises(generateChoises(selfAd,level.getNbMaxAdByPlay(), game,player));

		game.setPlayer(player);
		return adGameDao.save(game);
	}

	private Map<Integer, AdChoise> generateChoises(Boolean selfAd, Integer nbAds, AbstractAdGame game, Player player) throws Exception {
		Map<Integer, AdChoise> res = new HashMap<Integer, AdChoise>();

        Address address = player.getAddress();


        Map<Integer, Ad> mapTest = new HashMap<Integer, Ad>();

        List<Ad> allAds = null;
        if(selfAd){
            allAds = adDao.getAllValidForAndProvidedBy(player,((AdGameTransaction)game).getPartner());
        }else{
            allAds = adDao.getAllValidFor(player);
        }

		Random ramRandom = new Random();
        if(nbAds>allAds.size()){
            throw new Exception("No more pub");
        }

		int i = 0;
		while (i < nbAds-1) {
            // Todo : comment mettre en concurence les annonceurs
			int nextInt = ramRandom.nextInt(allAds.size());
			Ad ad = mapTest.get(nextInt);
			if (ad == null) {
				ad = allAds.get(nextInt);
				mapTest.put(nextInt, ad);

				AdChoise choises = new AdChoise();
				int correct = ramRandom.nextInt(3);


                //TODO : comment choisir la règle
				List<AdRule> rules = ad.getRules();
                List<AdService> rulesPossible = new ArrayList<AdService>();

                LocalDate now = new LocalDate();

				for (AdRule adRule : rules) {
                    if (AdService.class.isAssignableFrom(adRule.getClass())) {
                        addValidAdService(player, rulesPossible, now, (AdService) adRule);
                    }
				}

                if(rulesPossible.isEmpty()){
                    if(mapTest.size()==allAds.size()){
                        throw new Exception("No more pub");
                    }
                    continue;
                }

                AdService rule = (AdService) rulesPossible.get(ramRandom.nextInt(rulesPossible.size()));

				choises.setQuestion(rule.getQuestion());



                List<Possibility> corrects = choises.getCorrects();
                if (corrects ==null){
                    corrects = new ArrayList<Possibility>();
                    choises.setCorrects(corrects);
                }


                if (rule instanceof MultiResponseRule) {
                    MultiResponseRule multiResponseRule = (MultiResponseRule) rule;
                    generatePossibiliesAndCorrects(ad,multiResponseRule,choises);
                }else{
                    choises.setPossiblities(generatePossibilies(ad, correct,rule));
                    corrects.add(choises.getPossiblities().get(correct));
                }



                choises.setNumber(i);
                choises.setAdGame(game);
                choises.setGeneratedBy((AdService) rule);
				res.put(i, choises);

				i++;
			}
		}

		return res;
	}



    private void addValidAdService(Player player, List<AdService> rulesPossible, LocalDate now, AdService adRule) {
        if(now.toDate().after(((AdService) adRule).getStartDate()) && now.toDate().before(((AdService) adRule).getEndDate())){
            ViewedAd forToday = viewedAdDao.findForToday(player, (AdService) adRule);
            if(((AdService) adRule).getMaxDisplayByUser()==null){
                rulesPossible.add((AdService) adRule);
            }else{
                if(forToday==null || forToday.getNb() < ((AdService) adRule).getMaxDisplayByUser()){
                    rulesPossible.add((AdService) adRule);
                }
            }
        }
    }


    private void generatePossibiliesAndCorrects(Ad ad, MultiResponseRule or, AdChoise choises) {

        List<Possibility> possibilities = new ArrayList<Possibility>();
        List<Possibility> corrects = new ArrayList<Possibility>();
        Random ramRandom = new Random();

        List<AdResponse> responses = or.getResponses();

        Set<Integer> answers = new HashSet<Integer>();

        for (int i = 0; i < 3; i++) {
            OpenPossibility pp = new OpenPossibility();
            pp.setAd(ad);

            int ramdom;
            do {
                ramdom = ramRandom.nextInt(responses.size());
            } while (answers.contains(ramdom));
            answers.add(ramdom);
            AdResponse adResponse = responses.get(ramdom);
            pp.setAnswerText(adResponse.getResponse());
            pp.setAnswerImage(adResponse.getImage());

            possibilities.add(pp);
            if(or.getCorrects().contains(adResponse)){
                corrects.add(pp);
            }
        }

        choises.setPossiblities(possibilities);
        choises.setCorrects(corrects);
    }


    private List<Possibility> generatePossibilies(Ad ad, int correct, AdRule rule) {
		List<Possibility> possibilities = new ArrayList<Possibility>();
		Random ramRandom = new Random();
		
		if (rule instanceof BrandRule) {
			List<Brand> brands = brandDao.getAllPossible((BrandRule) rule);
			int b = brands.indexOf(ad.getBrand());

			Set<Integer> answers = new HashSet<Integer>();
			answers.add(b);

			for (int i = 0; i < 3; i++) {
				BrandPossibility bp = new BrandPossibility();
				bp.setAd(ad);
				if (correct == i) {
					bp.setBrand(ad.getBrand());
				} else {
					int ramdom;
					do {
						ramdom = ramRandom.nextInt(brands.size());
                    } while (answers.contains(ramdom));
					answers.add(ramdom);
					bp.setBrand(brands.get(ramdom));
				}
				possibilities.add(bp);
			}
		}else if (rule instanceof ProductRule) {
			List<Product> products = ad.getBrand().getProducts();
			int p = products.indexOf(ad.getProduct());

			Set<Integer> answers = new HashSet<Integer>();
			answers.add(p);

			for (int i = 0; i < 3; i++) {
				ProductPossibility prp = new ProductPossibility();
				prp.setAd(ad);
				if (correct == i) {
					prp.setProduct(ad.getProduct());
				} else {
					// choisir un produit aléatoire
					int ramdom;
					do {
						ramdom = ramRandom.nextInt(products.size());
					} while (answers.contains(ramdom));
					answers.add(ramdom);
					prp.setProduct(products.get(ramdom));
				}
				possibilities.add(prp);
			}
		}else if (rule instanceof OpenRule) {
			OpenRule or = (OpenRule) rule;
			List<AdResponse> responses = or.getResponses();
			int b = responses.indexOf(or.getCorrect());

			Set<Integer> answers = new HashSet<Integer>();
			answers.add(b);

			for (int i = 0; i < 3; i++) {
				OpenPossibility pp = new OpenPossibility();
				pp.setAd(ad);
				if (correct == i) {
					pp.setAnswerText(or.getCorrect().getResponse());
                    pp.setAnswerImage(or.getCorrect().getImage());
				} else {
					int ramdom;
					do {
						ramdom = ramRandom.nextInt(responses.size());
					} while (answers.contains(ramdom));
					answers.add(ramdom);
					pp.setAnswerText(responses.get(ramdom).getResponse());
                    pp.setAnswerImage(responses.get(ramdom).getImage());
				}
				possibilities.add(pp);
			}
		}else if (rule instanceof MultiResponseRule) {
            MultiResponseRule or = (MultiResponseRule) rule;
            List<AdResponse> responses = or.getResponses();

            Set<Integer> answers = new HashSet<Integer>();

            for (int i = 0; i < 3; i++) {
                OpenPossibility pp = new OpenPossibility();
                pp.setAd(ad);

                int ramdom;
                do {
                    ramdom = ramRandom.nextInt(responses.size());
                } while (answers.contains(ramdom));
                answers.add(ramdom);
                pp.setAnswerText(responses.get(ramdom).getResponse());
                pp.setAnswerImage(responses.get(ramdom).getImage());

                possibilities.add(pp);
            }
        }

		return possibilities;
	}



	public void saveResponses(Long idAdGame, Integer score,
			Map<Integer, List<Long>> answers,StatusGame statusGame) throws Exception {
		AbstractAdGame adGame = adGameDao.get(idAdGame);
		AdScore adScore = new AdScore();
		adScore.setScore(score);
		Map<Integer, AdResponsePlayer> answersPlayer = new HashMap<Integer, AdResponsePlayer>();
		for (Entry<Integer,  List<Long>> answer : answers.entrySet()) {
			AdResponsePlayer r = new AdResponsePlayer();
			r.setAdScore(adScore);
			r.setNumber(answer.getKey());
            List<Long> value = answer.getValue();
            if(value !=null && !value.isEmpty()){

                List<Possibility> possibilities = new ArrayList<Possibility>();
                for (Long idPossibily : value) {
                    possibilities.add(possibilityDao.get(idPossibily));
                }

				r.setResponses(possibilities);
			}
			answersPlayer.put(answer.getKey(), r);
		}
		adScore.setAnswers(answersPlayer);
		adGame.setScore(adScore);

        adGame.setStatusGame(statusGame);
		/*if(score>=adGame.getMinScore()){
			adGame.setStatusGame(fr.k2i.adbeback.core.business.game.StatusGame.Win);	
		}else{
			adGame.setStatusGame(fr.k2i.adbeback.core.business.game.StatusGame.Lost);
		}*/
		
		adGameDao.save(adGame);
	}



}
