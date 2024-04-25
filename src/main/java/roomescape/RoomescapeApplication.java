package roomescape;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import roomescape.controller.console.MainController;

@SpringBootApplication
public class RoomescapeApplication {
    public static void main(String[] args) {
        // SpringApplication.run(RoomescapeApplication.class, args);
        MainController controller = new MainController();
        controller.run();
    }

}
