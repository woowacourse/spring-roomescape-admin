package roomescape;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import roomescape.environment.ConsoleConfig;

public class RoomescapeConsoleApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(ConsoleConfig.class)
                .web(WebApplicationType.NONE)
                .run(args);
    }
}
