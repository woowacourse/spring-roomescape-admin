package roomescape;

import roomescape.controller.ConsoleMainController;

public class ConsoleRoomescapeApplication {
    public static void main(String[] args) {
        ConsoleMainController controller = AppConfig.consoleMainController();

        controller.run();
    }
}
