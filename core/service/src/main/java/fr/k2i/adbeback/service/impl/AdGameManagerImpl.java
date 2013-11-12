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
import fr.k2i.adbeback.core.business.game.AdChoise;
import fr.k2i.adbeback.core.business.game.AdGame;
import fr.k2i.adbeback.core.business.game.AdResponsePlayer;
import fr.k2i.adbeback.core.business.game.AdScore;
import fr.k2i.adbeback.core.business.game.BrandPossibility;
import fr.k2i.adbeback.core.business.game.OpenPossibility;
import fr.k2i.adbeback.core.business.game.Possibility;
import fr.k2i.adbeback.core.business.game.ProductPossibility;
import fr.k2i.adbeback.core.business.player.Player;
import fr.k2i.adbeback.dao.AdDao;
import fr.k2i.adbeback.dao.AdGameDao;
import fr.k2i.adbeback.dao.BrandDao;
import fr.k2i.adbeback.dao.PlayerDao;
import fr.k2i.adbeback.dao.PossibilityDao;
import fr.k2i.adbeback.service.AdGameManager;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation of UserManager interface.
 * 
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 */
@Service("adGameManager")
public class AdGameManagerImpl extends GenericManagerImpl<AdGame, Long>
		implements AdGameManager {


	private AdDao adDao;

	private BrandDao brandDao;
	
	private AdGameDao adGameDao;
	
	private PossibilityDao possibilityDao;

	private PlayerDao playerDao;
	
	@Autowired
	public void setPlayerDao(PlayerDao playerDao) {
		this.playerDao = playerDao;
	}


	
	@Autowired
	public void setBrandDao(BrandDao brandDao) {
		this.brandDao = brandDao;
	}

	@Autowired
	public void setAdDao(AdDao adDao) {
		this.adDao = adDao;
	}

	@Autowired
	public void setAdGameDao(AdGameDao adGameDao) {
		this.dao = adGameDao;
		this.adGameDao = adGameDao;
	}

	@Autowired
	public void setPossibilityDao(PossibilityDao possibilityDao) {
		this.possibilityDao = possibilityDao;
	}

    @Transactional
	public AdGame generate(Long idPlayer) throws Exception {
		AdGame game = new AdGame();

		Integer nbAds = 0;
		game.setMinScore(nbAds);
		nbAds += 6;

		game.setGenerated(new Date());

		AdScore score = new AdScore();
		score.setScore(0);
		game.setScore(score);

		game.setChoises(generateChoises(nbAds, game));
		Player player = playerDao.get(idPlayer);
		game.setPlayer(player);
		return adGameDao.save(game);
	}

	private Map<Integer, AdChoise> generateChoises(Integer nbAds, AdGame game) throws Exception {
		Map<Integer, AdChoise> res = new HashMap<Integer, AdChoise>();

		Map<Integer, Ad> mapTest = new HashMap<Integer, Ad>();
		List<Ad> allAds = adDao.getAll(new Date());
		Random ramRandom = new Random();

		int i = 0;
		while (i <= nbAds-1) {
			int nextInt = ramRandom.nextInt(allAds.size());
			Ad ad = mapTest.get(nextInt);
			if (ad == null) {
				ad = allAds.get(nextInt);
				mapTest.put(nextInt, ad);

				AdChoise choises = new AdChoise();
				int correct = ramRandom.nextInt(3);
				
				List<AdRule> rules = ad.getRules();
				AdRule rule = null;
				Date now = new Date();
				
				for (AdRule adRule : rules) {
					if(now.after(adRule.getStartDate()) && now.before(adRule.getEndDate()) ){
						rule = adRule;
						break;
					}
				}
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
		AdGame adGame = adGameDao.get(idAdGame);
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
