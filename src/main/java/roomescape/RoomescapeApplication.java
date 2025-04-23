package roomescape;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

//@SpringBootApplication
@EnableAutoConfiguration
//@ComponentScan
@SpringBootConfiguration
public class RoomescapeApplication {
    public static void main(String[] args) {
        SpringApplication.run(RoomescapeApplication.class, args);
    }
}
