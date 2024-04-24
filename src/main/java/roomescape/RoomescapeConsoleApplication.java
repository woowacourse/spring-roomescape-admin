package roomescape;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

@SpringBootApplication
public class RoomescapeConsoleApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(RoomescapeConsoleApplication.class)
                .web(WebApplicationType.NONE)
                .initializers(RoomescapeConsoleApplication::initializeContext)
                .run(args);
    }

    private static void initializeContext(ConfigurableApplicationContext context) {
        ConfigurableEnvironment environment = context.getEnvironment();
        environment.setActiveProfiles("console");
    }
}
