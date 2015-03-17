package fr.k2i.adbeback.core.business.otp;

import fr.k2i.adbeback.core.business.BaseObject;
import fr.k2i.adbeback.core.business.IMetaData;
import fr.k2i.adbeback.date.DateUtils;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * User: dimitri
 * Date: 18/02/13
 * Time: 11:29
 * Goal:
 */
@Entity
@Table(name= IMetaData.TableMetadata.OTP)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = IMetaData.ColumnMetadata.OTPSecurity.Discrimator.DISCRIMINATOR, discriminatorType = DiscriminatorType.STRING)
public abstract class OTPSecurity extends BaseObject implements Serializable {
    @Id
    @SequenceGenerator(name = "OTPSecurity_Gen", sequenceName = "OTPSecurity_Sequence")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "OTPSecurity_Gen")
    protected Long id;

    @Column(name=IMetaData.ColumnMetadata.OTPSecurity.KEY,length = 8)
    protected String key;


    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = IMetaData.ColumnMetadata.OTPSecurity.CREATION_DATE)
    protected Date creationDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = IMetaData.ColumnMetadata.OTPSecurity.EXPIRATION_DATE)
    protected Date expirationDate;


    public OTPSecurity(){
        creationDate = new Date();
    }

    public void expirationInHours(int expirationInHours) {
        LocalDateTime expire = DateUtils.asLocalDateTime(creationDate);
        expirationDate = DateUtils.asDate(expire.plusHours(expirationInHours));
    }

    public void computeNewExpirationInHours(int expirationInHours) {
        creationDate = new Date();
        expirationInHours(expirationInHours);
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }
}
