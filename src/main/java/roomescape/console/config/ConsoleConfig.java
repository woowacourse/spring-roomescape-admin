package roomescape.console.config;

import roomescape.console.controller.MainController;
import roomescape.core.repository.ReservationMemoryRepository;
import roomescape.core.repository.ReservationTimeMemoryRepository;
import roomescape.core.service.ReservationService;
import roomescape.core.service.ReservationTimeService;

public class ConsoleConfig {
    private static final ReservationTimeMemoryRepository reservationTimeMemoryRepository = new ReservationTimeMemoryRepository();
    private static final ReservationMemoryRepository reservationMemoryRepository = new ReservationMemoryRepository(
            reservationTimeMemoryRepository);

    private ConsoleConfig() {
    }

    public static MainController mainController() {
        return new MainController(reservationService(), reservationTimeService());
    }

    private static ReservationService reservationService() {
        return new ReservationService(reservationMemoryRepository());
    }

    private static ReservationTimeService reservationTimeService() {
        return new ReservationTimeService(reservationTimeMemoryRepository());
    }

    private static ReservationMemoryRepository reservationMemoryRepository() {
        return reservationMemoryRepository;
    }

    private static ReservationTimeMemoryRepository reservationTimeMemoryRepository() {
        return reservationTimeMemoryRepository;
    }
}
