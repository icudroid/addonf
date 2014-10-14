package fr.k2i.adbeback.core.business.game.lottery;

import fr.k2i.adbeback.core.business.BaseObject;
import fr.k2i.adbeback.core.business.IMetaData;
import fr.k2i.adbeback.core.business.game.AdGame;
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
@Table(name = IMetaData.TableMetadata.LUCKY_WIN_LOTTERY_CONFIG)
public class LuckyWinLotteryConfig extends BaseObject {
    @Id
    @SequenceGenerator(name = "LuckyWinLotteryConfig_Gen", sequenceName = "LuckyWinLotteryConfig_Sequence")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "LuckyWinLotteryConfig_Gen")
    private Long id;

    private Integer lucky;

    private Double minPrice;
    private Double maxPrice;


    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = IMetaData.ColumnMetadata.LuckyWinLotteryConfig.JOIN)
    private List<LuckyWinLottery> lotteries;


    public void addLuckyWinLottery(LuckyWinLottery luckyWinLottery) {
        if(lotteries == null){
            lotteries = new ArrayList<>();
        }
        lotteries.add(luckyWinLottery);
    }
}
