package roomescape.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = {"roomescape.web", "roomescape.core"})
@SpringBootApplication
public class RoomescapeWebApplication {
    public static void main(String[] args) {
        SpringApplication.run(RoomescapeWebApplication.class, args);
    }

}
