package red.kea.chess.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author： KeA
 * @date： 2021-12-14 16:12:51
 * @version: 1.0
 * @describe:
 */
@Controller
public class IndexController {

    @GetMapping(value={"","login"})
    public String login(){
        return "login";
    }

    @GetMapping(value={"index"})
    public String index(String user, Model model){
        model.addAttribute("userId",user);
        return "index";
    }
}
