package roomescape.console;

import roomescape.repository.MemoryReservationRepository;
import roomescape.repository.MemoryReservationTimeRepository;
import roomescape.repository.ReservationRepository;
import roomescape.repository.ReservationTimeRepository;
import roomescape.service.ReservationService;
import roomescape.service.ReservationTimeService;

public class ConsoleApplication {

    public static void main(String[] args) {
        ReservationTimeRepository timeRepository = new MemoryReservationTimeRepository();
        ReservationRepository reservationRepository = new MemoryReservationRepository();

        ReservationTimeService reservationTimeService = new ReservationTimeService(timeRepository);
        ReservationService reservationService = new ReservationService(reservationRepository, timeRepository);

        new RoomescapeConsole(reservationService, reservationTimeService).run();
    }
}
