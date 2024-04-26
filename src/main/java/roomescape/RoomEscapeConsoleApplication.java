package roomescape;

import roomescape.console.controller.ConsoleRunner;
import roomescape.console.config.ConsoleConfig;

public class RoomEscapeConsoleApplication {

    public static void main(String[] args) {
        ConsoleRunner consoleRunner = ConsoleConfig.consoleRunner();
        consoleRunner.run();
    }
}
