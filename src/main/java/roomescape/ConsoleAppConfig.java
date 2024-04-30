package roomescape;

import roomescape.console.view.ConsoleInputView;
import roomescape.console.view.ConsoleOutputView;
import roomescape.controller.ConsoleController;
import roomescape.repository.MemoryReservationRepository;
import roomescape.repository.MemoryReservationTimeRepository;
import roomescape.repository.ReservationRepository;
import roomescape.repository.ReservationTimeRepository;
import roomescape.service.RoomescapeService;

public class ConsoleAppConfig {

    public static ConsoleController consoleMainController() {
        return new ConsoleController(inputView(), outputView(), roomescapeService());
    }

    public static RoomescapeService roomescapeService() {
        return new RoomescapeService(reservationRepository(), reservationTimeRepository());
    }

    public static ReservationRepository reservationRepository() {
        return new MemoryReservationRepository();
    }

    public static ReservationTimeRepository reservationTimeRepository() {
        return new MemoryReservationTimeRepository();
    }

    public static ConsoleInputView inputView() {
        return new ConsoleInputView();
    }

    public static ConsoleOutputView outputView() {
        return new ConsoleOutputView();
    }
}
