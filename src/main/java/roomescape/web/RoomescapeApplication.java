package roomescape.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"roomescape.web", "roomescape.core"})
public class RoomescapeApplication {
    public static void main(String[] args) {
        SpringApplication.run(RoomescapeApplication.class, args);
    }
}
