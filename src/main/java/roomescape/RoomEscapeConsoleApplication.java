package roomescape;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import roomescape.controller.ConsoleController;

@SpringBootApplication
public class RoomEscapeConsoleApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(RoomEscapeConsoleApplication.class)
                .web(WebApplicationType.NONE)
                .run(args)
                .getBean(ConsoleController.class)
                .run();
    }
}
