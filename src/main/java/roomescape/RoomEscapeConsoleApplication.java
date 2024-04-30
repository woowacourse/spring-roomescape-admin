package roomescape;

import roomescape.controller.ConsoleController;
import roomescape.dao.ReservationDao;
import roomescape.dao.ReservationDaoMemory;
import roomescape.dao.ReservationTimeDao;
import roomescape.dao.ReservationTimeDaoMemory;
import roomescape.service.ReservationService;
import roomescape.service.ReservationTimeService;
import roomescape.view.ConsoleView;

public class RoomEscapeConsoleApplication {

    public static void main(String[] args) {
        ConsoleView view = new ConsoleView();
        ReservationDao reservationDao = new ReservationDaoMemory();
        ReservationTimeDao reservationTimeDao = new ReservationTimeDaoMemory();
        ReservationService reservationService = new ReservationService(reservationDao, reservationTimeDao);
        ReservationTimeService reservationTimeService = new ReservationTimeService(reservationTimeDao);
        ConsoleController controller = new ConsoleController(view, reservationService, reservationTimeService);

        controller.run();
    }
}
