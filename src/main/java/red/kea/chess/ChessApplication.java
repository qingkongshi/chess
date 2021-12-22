package red.kea.chess;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import red.kea.chess.utils.SpringUtil;

@SpringBootApplication
public class ChessApplication {

    public static void main(String[] args) {
        SpringApplication.run(ChessApplication.class, args);
    }

    // 注入SpringUtil
    @Bean
    public SpringUtil springUtil(){
        return new SpringUtil();
    }

}
