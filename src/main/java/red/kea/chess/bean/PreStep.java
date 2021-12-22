package red.kea.chess.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author： KeA
 * @date： 2021-12-22 14:30:50
 * @version: 1.0
 * @describe:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PreStep {

    /** 本人id */
    private String userId;

    /** 对手id */
    private String opponentId;

    /** 信息 */
    private String message;

    /** 当前落子 */
    private Integer current;

    /** 先手 */
    private Integer first;

    private Integer x;

    private Integer y;

}
