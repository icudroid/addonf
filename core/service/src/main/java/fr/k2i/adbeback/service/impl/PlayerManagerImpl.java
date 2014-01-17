package fr.k2i.adbeback.service.impl;

import java.util.List;

import fr.k2i.adbeback.core.business.otp.OneTimePassword;
import fr.k2i.adbeback.core.business.otp.OtpAction;
import fr.k2i.adbeback.dao.IOneTimePasswordDao;
import fr.k2i.adbeback.dao.IPlayerDao;
import fr.k2i.adbeback.dao.jpa.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.dao.SaltSource;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import fr.k2i.adbeback.core.business.player.Player;
import fr.k2i.adbeback.service.PlayerManager;
import fr.k2i.adbeback.service.UserExistsException;
import org.springframework.transaction.annotation.Transactional;


/**
 * Implementation of UserManager interface.
 *
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 */
@Service("playerManager")
public class PlayerManagerImpl extends GenericManagerImpl<Player, Long> implements PlayerManager {
    private PasswordEncoder passwordEncoder;
    private IPlayerDao playerDao;
    private CountryRepository countryRepository;
    @Autowired(required = false)
    private SaltSource saltSource;
    @Autowired
    private IOneTimePasswordDao oneTimePasswordDao;


    @Autowired
    public void setCountryRepository(CountryRepository countryRepository) {
		this.countryRepository = countryRepository;
	}

	@Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    public void setPlayerDao(IPlayerDao playerDao) {
        this.dao = playerDao;
        this.playerDao = playerDao;
    }

    /**
     * {@inheritDoc}
     */
    public Player getPlayer(String playerId) {
        return playerDao.get(new Long(playerId));
    }

    /**
     * {@inheritDoc}
     */
    public List<Player> getPlayers() {
        return playerDao.getAllDistinct();
    }

    /**
     * {@inheritDoc}
     */
    public Player savePlayer(Player player) throws UserExistsException {

        if (player.getVersion() == null) {
            // if new user, lowercase userId
            player.setUsername(player.getUsername().toLowerCase());
        }

        // Get and prepare password management-related artifacts
        boolean passwordChanged = false;
        if (passwordEncoder != null) {
            // Check whether we have to encrypt (or re-encrypt) the password
            if (player.getVersion() == null) {
                // New user, always encrypt
                passwordChanged = true;
            } else {
                // Existing user, check password in DB
                final String currentPassword = playerDao.getPlayerPassword(player.getUsername());
                if (currentPassword == null) {
                    passwordChanged = true;
                } else {
                    if (!currentPassword.equals(player.getPassword())) {
                        passwordChanged = true;
                    }
                }
            }

            // If password was changed (or new user), encrypt it
            if (passwordChanged) {
                if (saltSource == null) {
                    // backwards compatibility
                    player.setPassword(passwordEncoder.encodePassword(player.getPassword(), null));
                    log.warn("SaltSource not set, encrypting password w/o salt");
                } else {
                    player.setPassword(passwordEncoder.encodePassword(player.getPassword(),
                            saltSource.getSalt(player)));
                }
            }
        } else {
            log.warn("PasswordEncoder not set, skipping password encryption...");
        }

        try {
            return playerDao.savePlayer(player);
        } catch (final Exception e) {
            e.printStackTrace();
            log.warn(e.getMessage());
            throw new UserExistsException("User '" + player.getUsername() + "' already exists!");
        }







/*    	if(player.getUsername() == null )return null;
    	
        if (player.getVersion() == null) {
            // if new user, lowercase userId
            player.setUsername(player.getUsername().toLowerCase());
        }

        if(player.getAddress()!=null && player.getAddress().getCountry()!=null && player.getAddress().getCountry().getCode()!=null){
        	try {
				Country byCode = ICountryDao.getByCode(player.getAddress().getCountry().getCode());
				player.getAddress().setCountry(byCode);
			} catch (Exception e) {
				e.printStackTrace();
			}
        }
        
        // Get and prepare password management-related artifacts
        boolean passwordChanged = false;
        if (passwordEncoder != null) {
            // Check whether we have to encrypt (or re-encrypt) the password
            if (player.getVersion() == null) {
                // New user, always encrypt
                passwordChanged = true;
            } else {
                // Existing user, check password in DB
                String currentPassword = playerDao.getPlayerPassword(player.getUsername());
                if (currentPassword == null) {
                    passwordChanged = true;
                } else {
                    if (!currentPassword.equals(player.getPassword())) {
                        passwordChanged = true;
                    }
                }
            }

            // If password was changed (or new user), encrypt it
            if (passwordChanged) {
                player.setPassword(passwordEncoder.encodePassword(player.getPassword(), null));
            }
        } else {
            log.warn("PasswordEncoder not set, skipping password encryption...");
        }

        try {
            return playerDao.savePlayer(player);
        } catch (DataIntegrityViolationException e) {
            //e.printStackTrace();
            log.warn(e.getMessage());
            throw new UserExistsException("User '" + player.getUsername() + "' already exists!");
        } catch (JpaSystemException e) { // needed for JPA
            //e.printStackTrace();
            log.warn(e.getMessage());
            throw new UserExistsException("User '" + player.getUsername() + "' already exists!");
        }*/
    }


    /**
     * {@inheritDoc}
     */
    public void removePlayer(String playerId) {
        log.debug("removing user: " + playerId);
        playerDao.remove(new Long(playerId));
    }

    /**
     * {@inheritDoc}
     *
     * @param username the login name of the human
     * @return User the populated user object
     * @throws UsernameNotFoundException thrown when username not found
     */
    public Player getPlayerByUsername(String username) throws UsernameNotFoundException {
        return (Player) playerDao.loadUserByUsername(username);
    }

	public Player getPlayerByEmail(String email) {
		return (Player) playerDao.loadUserByEmail(email);
		
	}

    @Transactional
    @Override
    public void changePasswd(String username, String newPwd) {
        Player user = playerDao.findByEmailorUserName(username);
        OneTimePassword otp = oneTimePasswordDao.findBy(user, OtpAction.FORGOTTEN_PWD);
        oneTimePasswordDao.remove(otp);
        if (saltSource == null) {
            // backwards compatibility
            user.setPassword(passwordEncoder.encodePassword(newPwd, null));
            log.warn("SaltSource not set, encrypting password w/o salt");
        } else {
            user.setPassword(passwordEncoder.encodePassword(newPwd, saltSource.getSalt(user)));
        }
    }

}
