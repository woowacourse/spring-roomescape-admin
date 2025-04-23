package roomescape.application;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import roomescape.service.ReservationService;
import roomescape.service.ReservationTimeService;
import roomescape.view.InputView;
import roomescape.view.OutputView;

@Component
public class ConsoleApplication implements CommandLineRunner {

    private final InputView inputView;
    private final OutputView outputView;
    private final ReservationService reservationService;
    private final ReservationTimeService reservationTimeService;

    public ConsoleApplication(InputView inputView, OutputView outputView,
                              @Qualifier("consoleReservationService") ReservationService reservationService,
                              @Qualifier("consoleReservationTimeService") ReservationTimeService reservationTimeService) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.reservationService = reservationService;
        this.reservationTimeService = reservationTimeService;
    }

    @Override
    public void run(String... args) throws Exception {
    }
}
