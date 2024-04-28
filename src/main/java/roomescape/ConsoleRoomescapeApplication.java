package roomescape;

import roomescape.console.ConsoleController;
import roomescape.console.fake.FakeReservationDao;
import roomescape.console.fake.FakeReservationTimeDao;
import roomescape.console.fake.InMemoryReservationDb;
import roomescape.console.fake.InMemoryReservationTimeDb;
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
        InMemoryReservationDb inMemoryReservationDb = new InMemoryReservationDb();
        InMemoryReservationTimeDb inMemoryReservationTimeDb = new InMemoryReservationTimeDb();
        ReservationDao fakeReservationDao
                = new FakeReservationDao(inMemoryReservationDb, inMemoryReservationTimeDb);
        ReservationTimeDao fakeReservationTimeDao
                = new FakeReservationTimeDao(inMemoryReservationDb, inMemoryReservationTimeDb);
        ReservationService reservationService
                = new ReservationService(fakeReservationDao, fakeReservationTimeDao);
        ReservationTimeService reservationTimeService
                = new ReservationTimeService(fakeReservationTimeDao);
        return new ConsoleController(reservationService, reservationTimeService);
    }
}
