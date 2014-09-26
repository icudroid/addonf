package fr.k2i.adbeback.service.impl;

import java.util.*;
import java.util.Map.Entry;


import fr.k2i.adbeback.bean.ResponseUser;
import fr.k2i.adbeback.core.business.ad.ViewedAd;
import fr.k2i.adbeback.core.business.ad.rule.*;
import fr.k2i.adbeback.core.business.game.*;
import fr.k2i.adbeback.core.business.goosegame.GooseLevel;
import fr.k2i.adbeback.core.business.goosegame.IMultiGooseLevel;
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

    @Autowired
    IMediaDao mediaDao;

	@Autowired
	public void setAdGameDao(fr.k2i.adbeback.dao.IAdGameDao adGameDao) {
		this.dao = adGameDao;
		this.adGameDao = adGameDao;
	}


    @Override
    @Transactional
    public AbstractAdGame generateBorrowGame(Map<Ad, Double> winBidAds,Long idPlayer, GooseLevel level) throws Exception {
        return generate(winBidAds,null,null,idPlayer,level);
    }


    @Override
    @Transactional
	public AbstractAdGame generate(Map<Ad, Double> winBidAds, String idPartner, String idTransaction, Long idPlayer, GooseLevel level) throws Exception {

        AbstractAdGame game = null;

        if(level instanceof IMultiGooseLevel){
            game = new AdGame();
        }else{
            game = new AdGameTransaction();
            if(idTransaction !=null)
                ((AdGameTransaction)game).setIdTransaction(idTransaction);
            if(idPartner != null)
                ((AdGameTransaction)game).setMedia(mediaDao.findByExtId(idPartner));
        }

		game.setGenerated(new Date());

		AdScore score = new AdScore();
		score.setScore(0);
		game.setScore(score);

        Player player = playerDao.get(idPlayer);
        game.setChoises(generateChoises(winBidAds, game,player));

		game.setPlayer(player);
		return adGameDao.save(game);
	}

	private Map<Integer, AdChoise> generateChoises(Map<Ad, Double> allAds, AbstractAdGame game, Player player) throws Exception {
		Map<Integer, AdChoise> res = new TreeMap<Integer, AdChoise>(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1.compareTo(o2);
            }
        });

        Random ramRandom = new Random();

        for (Entry<Ad, Double> adEntry : allAds.entrySet()) {
            Ad adWinned = adEntry.getKey();

            int index = 0;
            do{
                index = ramRandom.nextInt(allAds.size());
            }while (res.get(index) !=null);


            AdChoise choises = new AdChoise();
            int correct = ramRandom.nextInt(3);
            choises.setWinBidPrice(adEntry.getValue());

            //TODO : comment choisir la règle
            List<AdRule> rules = adWinned.getRules();
            List<AdService> rulesPossible = new ArrayList<AdService>();

            LocalDate now = new LocalDate();

            for (AdRule adRule : rules) {
                if (AdService.class.isAssignableFrom(adRule.getClass())) {
                    addValidAdService(player, rulesPossible, now, (AdService) adRule);
                }
            }

            AdService rule = rulesPossible.get(ramRandom.nextInt(rulesPossible.size()));

            choises.setQuestion(rule.getQuestion());



            List<Possibility> corrects = choises.getCorrects();
            if (corrects ==null){
                corrects = new ArrayList<Possibility>();
                choises.setCorrects(corrects);
            }


            if (rule instanceof MultiResponseRule) {
                MultiResponseRule multiResponseRule = (MultiResponseRule) rule;
                generatePossibiliesAndCorrects(adWinned,multiResponseRule,choises);
            }else{
                choises.setPossiblities(generatePossibilies(adWinned, correct,rule));
                corrects.add(choises.getPossiblities().get(correct));
            }

            choises.setNumber(index);
            choises.setAdGame(game);
            choises.setGeneratedBy(rule);
            res.put(index, choises);


		}

		return res;
	}



    private void addValidAdService(Player player, List<AdService> rulesPossible, LocalDate now, AdService adRule) {
        if(now.toDate().after((adRule).getStartDate()) ||now.toDate().equals((adRule).getStartDate())  && now.toDate().before((adRule).getEndDate()) || now.toDate().equals((adRule).getEndDate())){
            ViewedAd forToday = viewedAdDao.findForToday(player,  adRule);
            if((adRule).getMaxDisplayByUser()==null){
                rulesPossible.add( adRule);
            }else{
                if(forToday==null || forToday.getNb() < ( adRule).getMaxDisplayByUser()){
                    rulesPossible.add( adRule);
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
            pp.setGeneratedBy(adResponse);

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
                    pp.setGeneratedBy(or.getCorrect());
				} else {
					int ramdom;
					do {
						ramdom = ramRandom.nextInt(responses.size());
					} while (answers.contains(ramdom));
					answers.add(ramdom);
					pp.setAnswerText(responses.get(ramdom).getResponse());
                    pp.setAnswerImage(responses.get(ramdom).getImage());
                    pp.setGeneratedBy(responses.get(ramdom));
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
                pp.setGeneratedBy(responses.get(ramdom));

                possibilities.add(pp);
            }
        }

		return possibilities;
	}



	public void saveResponses(Long idAdGame, Integer score,
			Map<Integer,ResponseUser> answers,StatusGame statusGame) throws Exception {
		AbstractAdGame adGame = adGameDao.get(idAdGame);
		AdScore adScore = new AdScore();
		adScore.setScore(score);
		Map<Integer, AdResponsePlayer> answersPlayer = new HashMap<Integer, AdResponsePlayer>();

		for (Entry<Integer,  ResponseUser> answer : answers.entrySet()) {
			AdResponsePlayer r = new AdResponsePlayer();

			r.setAdScore(adScore);
			r.setNumber(answer.getKey());
            ResponseUser value = answer.getValue();
            List<Long> responses = value.getResponses();
            Boolean correct = value.isCorrect();
            r.setPlayed(value.getPlayed());

            if(value !=null && responses!=null && !responses.isEmpty()){

                List<Possibility> possibilities = new ArrayList<Possibility>();
                for (Long idPossibily : responses) {
                    possibilities.add(possibilityDao.get(idPossibily));
                }

				r.setResponses(possibilities);
			}

            r.setCorrectAnswer(correct);
            r.setAdService(value.getAdService());

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
