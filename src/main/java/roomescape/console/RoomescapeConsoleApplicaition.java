package roomescape.console;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import roomescape.console.controller.MainController;
import roomescape.web.RoomescapeApplication;

public class RoomescapeConsoleApplicaition {
    public static void main(String[] args) {
        MainController mainController = new MainController();
        mainController.run();
    }
}
