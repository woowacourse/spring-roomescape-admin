package roomescape;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import roomescape.console.ConsoleApplication;

@SpringBootApplication
public class RoomEscapeApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(RoomEscapeApplication.class, args);

        ConsoleApplication consoleApplication = applicationContext.getBean("consoleApplication",
                ConsoleApplication.class);

        consoleApplication.run();
    }
}
