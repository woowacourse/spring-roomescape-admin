package roomescape.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"roomescape.web", "roomescape.general"})
public class RoomescapeWebApplication {
    public static void main(String[] args) {
        SpringApplication.run(RoomescapeWebApplication.class, args);
    }
}
