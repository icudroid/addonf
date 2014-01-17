package fr.k2i.adbeback.core.business.otp;


import fr.k2i.adbeback.core.business.BaseObject;
import fr.k2i.adbeback.core.business.player.Player;
import fr.k2i.adbeback.core.business.user.User;
import lombok.Data;

import javax.persistence.*;

/**
 * This class is used to represent available roles in the database.
 *
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 *         Version by Dan Kibler dan@getrolling.com
 *         Extended to implement Acegi GrantedAuthority interface
 *         by David Carter david@carter.net
 */
@Data
@Entity
@Table(name = "one_time_pwd_action")
public class OneTimePassword extends BaseObject {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;

    @Column(name = "opt_key")
    private String key;

    @Enumerated(EnumType.STRING)
    @Column(name = "opt_action")
    private OtpAction action;

    @ManyToOne
    @JoinColumn(name = "user_id",nullable = false)
    private Player user;

}