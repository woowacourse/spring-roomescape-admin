package roomescape;

import org.springframework.boot.SpringApplication;
import roomescape.config.WebConfig;

public class RoomescapeApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebConfig.class, args);
    }
}
