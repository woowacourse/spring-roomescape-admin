package roomescape;

import roomescape.console.view.InputView;
import roomescape.console.view.OutputView;
import roomescape.controller.ConsoleMainController;
import roomescape.repository.MemoryReservationRepository;
import roomescape.repository.MemoryReservationTimeRepository;
import roomescape.repository.ReservationRepository;
import roomescape.repository.ReservationTimeRepository;
import roomescape.service.RoomescapeService;

public class AppConfig {

    public static ConsoleMainController consoleMainController() {
        return new ConsoleMainController(inputView(), outputView(), roomescapeService());
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

    public static InputView inputView() {
        return new InputView();
    }

    public static OutputView outputView() {
        return new OutputView();
    }
}
