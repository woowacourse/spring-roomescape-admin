package roomescape;

import roomescape.controller.ConsoleController;

public class ConsoleRoomescapeApplication {
    public static void main(String[] args) {
        ConsoleController controller = ConsoleAppConfig.consoleMainController();

        controller.run();
    }
}
