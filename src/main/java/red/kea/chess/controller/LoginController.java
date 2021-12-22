package red.kea.chess.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author： KeA
 * @date： 2021-12-17 13:31:34
 * @version: 1.0
 * @describe:
 */
@RestController
@RequestMapping("back")
public class LoginController {

    @GetMapping("login")
    public String login(String username) throws IOException {
        System.out.println(username);
        return username;
    }
}
