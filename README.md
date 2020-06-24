#### 设计思路
1. 总则: 拿到5张牌, **先比较牌型, 如果牌型相同, 再根据牌型比较大小即可**
2. 由于上述要求只有四个, 但是实际上德州扑克的牌型包含了**10种**, 因此我扩展了全部的规则, 避免在出现类似**葫芦**这种牌型的时候被当做**高牌**处理
3. 为了使整个系统更加完善, 避免直接数据运行, 抽象出如下几个角色:
    - Poker: 代表一副扑克, 包含`发牌`动作
    - Dealer: 荷官, 系统的主导者, 负责`发牌`, `计算玩家牌型比较`等
    - Player: 玩家, 持有`5张牌`及其`牌型结果`对象等, 该类实现`Comparable`作为内部排序, 调用**comparing**逻辑实现**玩家的输赢**
4. 系统其他设计:
    - Card: 包括`牌色`和`牌数字`, 很明显, 这2个对象都用**枚举**来存储, 该类实现`Comparable`接口作为集合的内部排序
    - CardSuitEnum: `牌色`, 包括**红心, 黑桃, 草花, 方块**
    - CardRankEnum: `牌数字`, **2-A**, 其中A最大, 抽象成数字**14**表示
    - RankingEnum: `牌型`, 如`high card`, `one pair`等10种牌型
5. 关键设计: 根据**总则**, 抽象出如下2个过程
    - ranking: 5张牌牌型的解析
    - comparing: 相同牌型的比较, 比如都是`高牌`, 依次比较最大牌即可
6. ranking的设计
    - 使用模板设计模式, 每个`牌型解析器`作为一个单独的类自己实现其解析逻辑, 如`FourOfTheKindRankingImpl`用于检验5张牌是不是`四条`
    - 每个`解析类`都具有**可插拔**的性质, 全部注册在**门面类RankingFacade**中, 如**题目要求**只要四个规则, 即注册对应的4个解析类即可
    - 继承结构: `XXXRankingImpl` < `AbstractRanking` < `IRanking`
7. comparing的设计
    - 依赖`ranking`的解析结果, 使用`策略模式`取得对应的`比较器`, 比较相同的牌型结果
    - 继承结构: `XXXComparingImpl` < `AbstractComparing` < `IComparing` < `Comparator<Player>`

#### 代码结构
- com.alibaba.game.*: 主函数入口
- com.alibaba.game.texasholdem.*: 基本对象
- com.alibaba.game.texasholdem.ranking.*: `ranking`的逻辑, 每个实现类具有可插拔的性质, 如要扩展`牌型规则`, 在这里实现具体的`解析类`即可
- com.alibaba.game.texasholdem.comparing.*: `comparing`的逻辑, 每个实现类具有可插拔的性质, 如要扩展`牌型比较规则`, 在这里实现具体的`比较类`即可
- TEST: 包含对应类的**单元测试**

