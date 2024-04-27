package roomescape;

import roomescape.controller.ConsoleReservationController;

public class RoomescapeConsoleApplication {
    public static void main(String[] args) {
        ConsoleReservationController controller = new ConsoleReservationController();
        controller.run();
    }
}
