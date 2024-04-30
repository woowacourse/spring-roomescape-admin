package roomescape.console;

import roomescape.console.controller.MainController;
import roomescape.console.repository.ReservationMemoryRepository;
import roomescape.console.repository.ReservationTimeMemoryRepository;
import roomescape.core.service.ReservationService;
import roomescape.core.service.ReservationTimeService;

public class RoomescapeConsoleApplicaition {
    public static void main(String[] args) {
        MainController mainController = new MainController(
                new ReservationService(new ReservationMemoryRepository()),
                new ReservationTimeService(new ReservationTimeMemoryRepository()));
        mainController.run();
    }
}
