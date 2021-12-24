package red.kea.chess.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import red.kea.chess.service.PreparationStage;

/**
 * @author： KeA
 * @date： 2021-12-22 10:29:00
 * @version: 1.0
 * @describe:
 */
@RestController
@RequestMapping("base")
public class CommonController {

    @Autowired
    private PreparationStage preparationStage;

    @RequestMapping("prepare")
    public String prepare(String userId){
        return preparationStage.prepare(userId);
    }
}
