package roomescape;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import roomescape.controller.console.MainController;

@SpringBootApplication
public class RoomescapeApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(RoomescapeApplication.class, args);
        MainController mainController = applicationContext.getBean("mainController", MainController.class);
        mainController.run();
    }

}
