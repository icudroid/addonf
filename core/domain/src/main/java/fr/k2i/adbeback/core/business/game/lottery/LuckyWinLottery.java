package fr.k2i.adbeback.core.business.game.lottery;

import fr.k2i.adbeback.core.business.BaseObject;
import fr.k2i.adbeback.core.business.IMetaData;
import fr.k2i.adbeback.core.business.game.AdGameTransaction;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * User: dimitri
 * Date: 14/10/14
 * Time: 15:40
 * Goal:
 */
@Data
@Entity
@Table(name = IMetaData.TableMetadata.LUCKY_WIN_LOTTERY)
public class LuckyWinLottery extends BaseObject {
    @Id
    @SequenceGenerator(name = "LuckyWinLottery_Gen", sequenceName = "LuckyWinLottery_Sequence")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "LuckyWinLottery_Gen")
    private Long id;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = IMetaData.ColumnMetadata.LuckyWinLottery.JOIN)
    private List<AdGameTransaction> lostLottery;


    @OneToOne
    @JoinColumn(name = IMetaData.ColumnMetadata.LuckyWinLottery.WIN_LOTTERY_JOIN)
    private AdGameTransaction winLottery;

    public void addLostLottery(AdGameTransaction transaction) {
        if(lostLottery == null){
            lostLottery = new ArrayList<>();
        }
        lostLottery.add(transaction);
    }
}
