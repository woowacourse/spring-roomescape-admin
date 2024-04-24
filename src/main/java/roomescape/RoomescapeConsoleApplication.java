package roomescape;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class RoomescapeConsoleApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(RoomescapeConsoleApplication.class)
                .web(WebApplicationType.NONE)
                .run(args);
    }
}
