package roomescape;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import roomescape.console.controller.ConsoleMainController;

@SpringBootApplication
public class RoomescapeApplication {
    public static void main(final String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(RoomescapeApplication.class, args);
        ConsoleMainController controller = context.getBean("consoleMainController", ConsoleMainController.class);
        controller.run();
        context.close();
    }
}
