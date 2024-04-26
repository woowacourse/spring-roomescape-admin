package roomescape;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import roomescape.console.ConsoleController;

@SpringBootApplication
public class RoomescapeApplication implements CommandLineRunner {
    @Autowired
    private ConsoleController consoleController;

    public static void main(String[] args) {
        SpringApplication.run(RoomescapeApplication.class, args);
    }

    @Override
    public void run(String... args) {
        consoleController.run();
    }
}
