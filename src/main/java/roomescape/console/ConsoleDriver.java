package roomescape.console;

import roomescape.controller.ReservationController;
import roomescape.controller.ReservationTimeController;
import roomescape.repository.ListReservationRepository;
import roomescape.repository.ListReservationTimeRepository;
import roomescape.service.ReservationService;
import roomescape.service.ReservationTimeService;

public class ConsoleDriver {

    public static void main(String[] args) {
        new ConsoleRunner(
                new ReservationController(
                        new ReservationService(
                                new ListReservationRepository(), new ListReservationTimeRepository())),
                new ReservationTimeController(
                        new ReservationTimeService(
                                new ListReservationTimeRepository()))
        ).run();
    }
}
