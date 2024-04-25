package roomescape;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import roomescape.controller.ConsoleController;

@SpringBootApplication
public class RoomEscapeApplication implements CommandLineRunner {

    private final ConsoleController consoleController;

    @Autowired
    public RoomEscapeApplication(ConsoleController consoleController) {
        this.consoleController = consoleController;
    }

    public static void main(String[] args) {
        SpringApplication.run(RoomEscapeApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        consoleController.run();
    }
}
