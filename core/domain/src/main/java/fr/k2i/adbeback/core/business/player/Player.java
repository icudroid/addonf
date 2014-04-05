package fr.k2i.adbeback.core.business.player;

import fr.k2i.adbeback.core.business.BaseObject;
import fr.k2i.adbeback.core.business.IMetaData;
import fr.k2i.adbeback.core.business.LabelValue;
import fr.k2i.adbeback.core.business.ad.ViewedAd;
import fr.k2i.adbeback.core.business.game.AbstractAdGame;
import fr.k2i.adbeback.core.business.goosegame.GooseToken;
import fr.k2i.adbeback.core.business.goosegame.GooseWin;
import fr.k2i.adbeback.core.business.user.User;
import lombok.Data;

import javax.persistence.*;
import java.util.*;

/**
 * This class represents the basic "user" object in AppFuse that allows for authentication
 * and user management.  It implements Acegi Security's UserDetails interface.
 *
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 *         Updated by Dan Kibler (dan@getrolling.com)
 *         Extended to implement Acegi UserDetails interface
 *         by David Carter david@carter.net
 */

@Data
@Entity
@DiscriminatorValue("Player")
public class Player extends User{

    @Enumerated(EnumType.STRING)
    protected Sex sex;

    @Temporal(TemporalType.DATE)
    protected Date birthday;

    protected Boolean newsletter;

    @OneToMany(mappedBy="player",cascade=CascadeType.ALL)
    protected List<AbstractAdGame> games = new ArrayList<AbstractAdGame>();

    @OneToMany(cascade=CascadeType.ALL,mappedBy="player")
    @OrderBy("windate DESC")
    protected List<GooseWin> wins = new ArrayList<GooseWin>();

    @OneToMany(cascade=CascadeType.ALL)
    @JoinColumn(name="PLAYER_ID")
    protected List<GooseToken> gooseTokens;

    protected Integer validatedLevel;

    @OneToMany(cascade=CascadeType.ALL,mappedBy="player")
    protected List<ViewedAd> viewedAds = new ArrayList<ViewedAd>();

    @Enumerated(EnumType.STRING)
    protected AgeGroup ageGroup;


    public void addGooseToken(GooseToken gooseToken) {
        if(gooseTokens==null){
            gooseTokens = new ArrayList<GooseToken>();
        }
        gooseToken.setPlayer(this);
        gooseTokens.add(gooseToken);
    }


    @Override
    public String toString() {
        return "Player{" +
                "username='" + username + '\'' +
                ", enabled=" + enabled +
                ", roles=" + roles +
                '}';
    }
}
