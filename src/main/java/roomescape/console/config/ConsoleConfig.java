package roomescape.console.config;

import roomescape.console.domain.ConsoleReservationRepository;
import roomescape.console.domain.ConsoleReservationTimeRepository;
import roomescape.console.view.InputView;
import roomescape.console.view.OutputView;
import roomescape.domain.ReservationRepository;
import roomescape.domain.ReservationTimeRepository;
import roomescape.service.ReservationService;
import roomescape.service.ReservationTimeService;

public class ConsoleConfig {
    private final ReservationTimeRepository reservationTimeRepository;
    private final ReservationRepository reservationRepository;

    public ConsoleConfig() {
        this.reservationTimeRepository = new ConsoleReservationTimeRepository();
        this.reservationRepository = new ConsoleReservationRepository();
    }

    public ReservationTimeRepository reservationTimeRepository() {
        return reservationTimeRepository;
    }

    public ReservationRepository reservationRepository() {
        return reservationRepository;
    }

    public ReservationTimeService reservationTimeService() {
        return new ReservationTimeService(reservationTimeRepository());
    }

    public ReservationService reservationService() {
        return new ReservationService(reservationRepository(), reservationTimeRepository());
    }

    public InputView inputView() {
        return new InputView();
    }

    public OutputView outputView() {
        return new OutputView();
    }
}
