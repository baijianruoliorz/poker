package com.alibaba.game.texasholdem.comparing;

import com.alibaba.game.texasholdem.Player;

import java.util.Map;

/**
 * Class {@code FourOfTheKindComparingImpl}
 * 四条的大小比较(直接比较炸弹)
 */
public class FourOfTheKindComparingImpl extends AbstractComparing {

    public int compare(Player o1, Player o2) {
        Map<Integer, Integer> p1CardMap = o1.getCardsRankCountMap();
        Map<Integer, Integer> p2CardMap = o2.getCardsRankCountMap();
        return this.multiComparing(p1CardMap, p2CardMap, 4);

    }

}
