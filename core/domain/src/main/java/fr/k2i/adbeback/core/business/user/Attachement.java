package fr.k2i.adbeback.core.business.user;

import fr.k2i.adbeback.core.business.BaseObject;
import fr.k2i.adbeback.core.business.IMetaData;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="attachement")
@Data
public class Attachement extends BaseObject implements Serializable {

    @Id
    @SequenceGenerator(name = "Attachement_Gen", sequenceName = "Attachement_Sequence")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Attachement_Gen")
    private String id;

    @Column(name=IMetaData.ColumnMetadata.Attachement.ORIGINAL_NAME,length = 255)
    private String originalName;

    @Column(name=IMetaData.ColumnMetadata.Attachement.EXTENTION,length = 10)
    private String extention;

    @Column(name=IMetaData.ColumnMetadata.Attachement.SIZE)
    private Long size;

    @Column(name=IMetaData.ColumnMetadata.Attachement.FULL_PATH,length = 255)
    private String fullPath;

    @Enumerated(value = EnumType.STRING)
    @Column(name=IMetaData.ColumnMetadata.Attachement.STATUS,length = 10)
    private AttachementStatus status;

    @ManyToOne
    private Agency agency;

    public Attachement(){
        status = AttachementStatus.NOPRESENT;
        size=0L;
        originalName="";
        extention="";
        fullPath="";
    }

}
