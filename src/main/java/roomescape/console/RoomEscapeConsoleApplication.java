package roomescape.console;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import roomescape.console.controller.ConsoleRoomEscapeController;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class RoomEscapeConsoleApplication {
    public static void main(String[] args) {

        SpringApplication application = new SpringApplication(RoomEscapeConsoleApplication.class);
        application.setWebApplicationType(WebApplicationType.NONE);
        ConfigurableApplicationContext context = application.run(args);

        ConsoleRoomEscapeController controller = context.getBean("consoleRoomEscapeController",
                ConsoleRoomEscapeController.class);
        controller.run();
    }
}
