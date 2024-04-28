package roomescape.console;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"roomescape.console", "roomescape.general"})
public class RoomescapeConsoleApplication {
    public static void main(final String[] args) {
        ConfigurableApplicationContext configurableApplicationContext = SpringApplication.run(
                RoomescapeConsoleApplication.class, args);
        configurableApplicationContext.close();
    }
}
