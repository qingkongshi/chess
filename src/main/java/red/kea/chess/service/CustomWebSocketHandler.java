package red.kea.chess.service;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author： KeA
 * @date： 2021-12-16 16:50:35
 * @version: 1.0
 * @describe: 创建一个WebSocket server
 */
@Slf4j
@Component
public class CustomWebSocketHandler extends TextWebSocketHandler implements WebSocketHandler {

    // 在线用户列表
    private static final Map<String, WebSocketSession> users;
    // 用户标识
    private static final String CLIENT_ID = "mchNo";

    static {
        users = new HashMap<>();
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        log.info("成功建立websocket-spring连接");
        String mchNo = getMchNo(session);
        if (StrUtil.isNotEmpty(mchNo)) {
            users.put(mchNo, session);
            session.sendMessage(new TextMessage("成功建立websocket-spring连接"));
            log.info("用户标识：{},Session：{}", mchNo, session.toString());
        }
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) {
        log.info("收到客户端消息：{}", message.getPayload());
        JSONObject msgJson = JSONUtil.parseObj(message.getPayload());
        String to = msgJson.getStr("to");
        String msg = msgJson.getStr("msg");
        WebSocketMessage<?> webSocketMessageServer = new TextMessage("server:" +message);
        try {
//            session.sendMessage(webSocketMessageServer);
            if("all".equals(to.toLowerCase())) {
                sendMessageToAllUsers(new TextMessage(getMchNo(session) + ":" +msg));
            }else {
                sendMessageToUser(to, new TextMessage(getMchNo(session) + ":" +msg));
            }
        } catch (Exception e) {
            log.info("handleTextMessage method error：{}", e);
        }
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        if (session.isOpen()) {
            session.close();
        }
        log.info("连接出错");
        PreparationStage.queue.poll();
        users.remove(getMchNo(session));
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        log.info("连接已关闭：" + status);
        PreparationStage.queue.poll();
        users.remove(getMchNo(session));
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }

    public void sendMessage(String jsonData) {
        log.info("收到客户端消息sendMessage：{}", jsonData);
        JSONObject msgJson = JSONUtil.parseObj(jsonData);
        String mchNo = StrUtil.isEmpty(msgJson.getStr(CLIENT_ID)) ? "陌生人" : msgJson.getStr(CLIENT_ID);
        String to = msgJson.getStr("to");
        String msg = msgJson.getStr("msg");
        if("all".equals(to.toLowerCase())) {
            sendMessageToAllUsers(new TextMessage(mchNo + ":" +msg));
        }else {
            sendMessageToUser(to, new TextMessage(mchNo + ":" +msg));
        }
    }

    /**
     * 发送信息给指定用户
     * @Title: sendMessageToUser
     * @Description: TODO
     * @Date 2018年8月21日 上午11:01:08
     * @author OnlyMate
     * @param mchNo
     * @param message
     * @return
     */
    public boolean sendMessageToUser(String mchNo, TextMessage message) {
        if (users.get(mchNo) == null){
            log.info("客户端:{},已断开连接，发送消息失败", mchNo);
            return false;
        }
        WebSocketSession session = users.get(mchNo);
        log.info("sendMessage：{} ,msg：{}", session, message.getPayload());
        if (!session.isOpen()) {
            log.info("客户端:{},已断开连接，发送消息失败", mchNo);
            return false;
        }
        try {
            session.sendMessage(message);
        } catch (IOException e) {
            log.info("sendMessageToUser method error：{}", e);
            return false;
        }
        return true;
    }

    /**
     * 广播信息
     * @Title: sendMessageToAllUsers
     * @Description: TODO
     * @Date 2018年8月21日 上午11:01:14
     * @author OnlyMate
     * @param message
     * @return
     */
    public boolean sendMessageToAllUsers(TextMessage message) {
        boolean allSendSuccess = true;
        Set<String> mchNos = users.keySet();
        WebSocketSession session = null;
        for (String mchNo : mchNos) {
            try {
                session = users.get(mchNo);
                if (session.isOpen()) {
                    session.sendMessage(message);
                }else {
                    log.info("客户端:{},已断开连接，发送消息失败", mchNo);
                }
            } catch (IOException e) {
                log.info("sendMessageToAllUsers method error：{}", e);
                allSendSuccess = false;
            }
        }

        return allSendSuccess;
    }

    /**
     * 获取用户标识
     * @Title: getMchNo
     * @Description: TODO
     * @Date 2018年8月21日 上午11:01:01
     * @author OnlyMate
     * @param session
     * @return
     */
    private String getMchNo(WebSocketSession session) {
        try {
            String mchNo = session.getAttributes().get(CLIENT_ID).toString();
            return mchNo;
        } catch (Exception e) {
            return null;
        }
    }
}
