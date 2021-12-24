package red.kea.chess.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * @author： KeA
 * @date： 2021-12-16 17:24:59
 * @version: 1.0
 * @describe: WebSocket握手时的拦截器
 */
@Slf4j
public class CustomWebSocketInterceptor implements HandshakeInterceptor {

    /**
     * 关联HeepSession和WebSocketSession，
     * beforeHandShake方法中的Map参数 就是对应websocketSession里的属性
     */

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        if (request instanceof ServletServerHttpRequest) {
            log.info("*****beforeHandshake******");
            HttpServletRequest httpServletRequest = ((ServletServerHttpRequest) request).getServletRequest();
            HttpSession session = httpServletRequest.getSession(true);

            log.info("mchNo：{}", httpServletRequest.getParameter("mchNo"));
            if (session != null) {
                log.info(session.getId());
//                session.setAttribute("mchNo",httpServletRequest.getParameter("mchNo"));
                attributes.put("sessionId",session.getId());
                attributes.put("mchNo", httpServletRequest.getParameter("mchNo"));
            }
        }
        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {
        log.info("*****afterHandshake******");
    }
}
