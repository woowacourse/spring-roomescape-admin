package roomescape;

import roomescape.console.ConsoleController;
import roomescape.console.fake.FakeReservationDao;
import roomescape.console.fake.FakeReservationDb;
import roomescape.console.fake.FakeReservationTimeDao;
import roomescape.console.fake.FakeReservationTimeDb;
import roomescape.dao.ReservationDao;
import roomescape.dao.ReservationTimeDao;
import roomescape.service.ReservationService;
import roomescape.service.ReservationTimeService;

public class ConsoleRoomescapeApplication {

    public static void main(String[] args) {
        ConsoleController consoleController = initConsoleController();
        consoleController.run();
    }

    private static ConsoleController initConsoleController() {
        FakeReservationDb fakeReservationDb = new FakeReservationDb();
        FakeReservationTimeDb fakeReservationTimeDb = new FakeReservationTimeDb();
        ReservationDao fakeReservationDao
                = new FakeReservationDao(fakeReservationDb, fakeReservationTimeDb);
        ReservationTimeDao fakeReservationTimeDao
                = new FakeReservationTimeDao(fakeReservationDb, fakeReservationTimeDb);
        ReservationService reservationService
                = new ReservationService(fakeReservationDao, fakeReservationTimeDao);
        ReservationTimeService reservationTimeService
                = new ReservationTimeService(fakeReservationTimeDao);
        return new ConsoleController(reservationService, reservationTimeService);
    }
}
