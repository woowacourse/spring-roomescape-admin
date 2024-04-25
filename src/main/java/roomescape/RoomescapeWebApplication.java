package roomescape;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import roomescape.environment.WebConfig;

@SpringBootApplication
public class RoomescapeWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebConfig.class, args);
    }
}
