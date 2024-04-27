package roomescape.console;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"roomescape.console", "roomescape.core"})
public class RoomescapeConsoleApplication {
    public static void main(String[] args) {
        SpringApplication.run(RoomescapeConsoleApplication.class, args);
    }

}
