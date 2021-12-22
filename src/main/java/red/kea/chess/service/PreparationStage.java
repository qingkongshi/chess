package red.kea.chess.service;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import red.kea.chess.bean.MatchedPlayer;
import red.kea.chess.bean.PreStep;
import red.kea.chess.utils.SpringUtil;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @author： KeA
 * @date： 2021-12-22 10:27:47
 * @version: 1.0
 * @describe:
 */
@Service
public class PreparationStage {

    static Queue<String> queue = new ConcurrentLinkedQueue<>();

    static List<MatchedPlayer> list = new ArrayList<>();

//    private final CustomWebSocketHandler customWebSocketHandler;
//
//    public PreparationStage() {
//        customWebSocketHandler = SpringUtil.getBean(CustomWebSocketHandler.class);
//    }


    public String prepare(String userId){
        CustomWebSocketHandler customWebSocketHandler = SpringUtil.getBean(CustomWebSocketHandler.class);
        String poll = queue.poll();
        if (null == poll){
            System.out.println("没有等待玩家");
            queue.add(userId);
        }else{
            Random random = new Random();
            MatchedPlayer matchedPlayer = new MatchedPlayer();
            if (random.nextBoolean()){
//                matchedPlayer.setCircle(userId);
//                matchedPlayer.setFork(poll);
                PreStep one = new PreStep(userId,poll,"已匹配到对手，你是先手",1,1,null,null);
                JSONObject jsonOne = JSONUtil.parseObj(one);
                PreStep two = new PreStep(poll,userId,"已匹配到对手，你是后手",0,0,null,null);
                JSONObject jsonTwo = JSONUtil.parseObj(two);
                customWebSocketHandler.sendMessageToUser(userId,new TextMessage(jsonOne.toString()));
                customWebSocketHandler.sendMessageToUser(poll,new TextMessage(jsonTwo.toString()));
            }else{
//                matchedPlayer.setCircle(poll);
//                matchedPlayer.setFork(userId);
                PreStep one = new PreStep(userId,poll,"已匹配到对手，你是后手",0,0,null,null);
                JSONObject jsonOne = JSONUtil.parseObj(one);
                PreStep two = new PreStep(poll,userId,"已匹配到对手，你是先手",1,1,null,null);
                JSONObject jsonTwo = JSONUtil.parseObj(two);
                customWebSocketHandler.sendMessageToUser(userId,new TextMessage(jsonOne.toString()));
                customWebSocketHandler.sendMessageToUser(poll,new TextMessage(jsonTwo.toString()));
            }

//            list.add(matchedPlayer);
        }
        return "1";
    }
}
