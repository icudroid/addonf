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


import fr.k2i.adbeback.core.business.game.*;
import fr.k2i.adbeback.core.business.goosegame.GooseLevel;
import fr.k2i.adbeback.core.business.player.Address;
import fr.k2i.adbeback.dao.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.k2i.adbeback.core.business.ad.Ad;
import fr.k2i.adbeback.core.business.ad.Brand;
import fr.k2i.adbeback.core.business.ad.Product;
import fr.k2i.adbeback.core.business.ad.rule.AdResponse;
import fr.k2i.adbeback.core.business.ad.rule.AdRule;
import fr.k2i.adbeback.core.business.ad.rule.BrandRule;
import fr.k2i.adbeback.core.business.ad.rule.OpenRule;
import fr.k2i.adbeback.core.business.ad.rule.ProductRule;
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
	private AdDao adDao;

    @Autowired
	private BrandDao brandDao;
	
	private AdGameDao adGameDao;

    @Autowired
	private PossibilityDao possibilityDao;

    @Autowired
	private PlayerDao playerDao;

    @Autowired
    private GooseLevelDao gooseLevelDao;

	@Autowired
	public void setAdGameDao(AdGameDao adGameDao) {
		this.dao = adGameDao;
		this.adGameDao = adGameDao;
	}


    @Transactional
	public AbstractAdGame generate(Long idPlayer, Long gooseLevel) throws Exception {
        GooseLevel level = gooseLevelDao.get(gooseLevel);
        AbstractAdGame game = null;

        if(level.isMultiple()){
            game = new AdGame();
        }else{
            game = new AdGameMedia();
        }


		game.setMinScore(level.getMinScore());

		game.setGenerated(new Date());

		AdScore score = new AdScore();
		score.setScore(0);
		game.setScore(score);

        Player player = playerDao.get(idPlayer);
        game.setChoises(generateChoises(level.getNbMaxAdByPlay(), game,player));

		game.setPlayer(player);
		return adGameDao.save(game);
	}

	private Map<Integer, AdChoise> generateChoises(Integer nbAds, AbstractAdGame game,Player player) throws Exception {
		Map<Integer, AdChoise> res = new HashMap<Integer, AdChoise>();

        Address address = player.getAddress();


        Map<Integer, Ad> mapTest = new HashMap<Integer, Ad>();

        //List<Ad> allAds = adDao.getAll(new Date());
        List<Ad> allAds =  adDao.getAllValideFor(player);
		Random ramRandom = new Random();

		int i = 0;
		while (i <= nbAds-1) {
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
                List<AdRule> rulesPossible = new ArrayList<AdRule>();


				for (AdRule adRule : rules) {
                    if (adRule instanceof BrandRule) {
                        rulesPossible.add(adRule);
                    }
                    if (adRule instanceof OpenRule) {
                        rulesPossible.add(adRule);
                    }
                    if (adRule instanceof ProductRule) {
                        rulesPossible.add(adRule);
                    }
				}

                AdRule rule = rulesPossible.get(ramRandom.nextInt(rulesPossible.size()));

				choises.setQuestion(rule.getQuestion());
				choises.setPossiblities(generatePossibilies(ad, correct,rule));
				choises.setCorrect(choises.getPossiblities().get(correct));
				choises.setNumber(i);
                choises.setAdGame(game);
				res.put(i, choises);

				i++;
			}
		}

		return res;
	}

	private List<Possibility> generatePossibilies(Ad ad, int correct, AdRule rule) {
		List<Possibility> possibilities = new ArrayList<Possibility>();
		Random ramRandom = new Random();
		
		if (rule instanceof BrandRule) {
			List<Brand> brands = brandDao.getAll();
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
					pp.setAnswer(or.getCorrect().getResponse());
				} else {
					int ramdom;
					do {
						ramdom = ramRandom.nextInt(responses.size());
					} while (answers.contains(ramdom));
					answers.add(ramdom);
					pp.setAnswer(responses.get(ramdom).getResponse());
				}
				possibilities.add(pp);
			}
		}

		return possibilities;
	}



	public void saveResponses(Long idAdGame, Integer score,
			Map<Integer, Long> answers) throws Exception {
		AbstractAdGame adGame = adGameDao.get(idAdGame);
		AdScore adScore = new AdScore();
		adScore.setScore(score);
		Map<Integer, AdResponsePlayer> answersPlayer = new HashMap<Integer, AdResponsePlayer>();
		for (Entry<Integer, Long> answer : answers.entrySet()) {
			AdResponsePlayer r = new AdResponsePlayer();
			r.setAdScore(adScore);
			r.setNumber(answer.getKey());
			if(answer.getValue()!=null && answer.getValue()!=-1){
				r.setResponse(possibilityDao.get(answer.getValue()));
			}
			answersPlayer.put(answer.getKey(), r);
		}
		adScore.setAnswers(answersPlayer);
		adGame.setScore(adScore);
		
		if(score>=adGame.getMinScore()){
			adGame.setStatusGame(fr.k2i.adbeback.core.business.game.StatusGame.Win);	
		}else{
			adGame.setStatusGame(fr.k2i.adbeback.core.business.game.StatusGame.Lost);
		}
		
		adGameDao.save(adGame);
	}



}
