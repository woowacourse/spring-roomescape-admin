package roomescape.console;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import roomescape.console.fake.FakeReservationDao;
import roomescape.console.fake.FakeReservationDb;
import roomescape.console.fake.FakeReservationTimeDao;
import roomescape.console.fake.FakeReservationTimeDb;
import roomescape.dao.ReservationDao;
import roomescape.dao.ReservationTimeDao;
import roomescape.service.ReservationService;
import roomescape.service.ReservationTimeService;

@Profile("!test")
@Component
public class ConsoleRunner implements CommandLineRunner {
    @Override
    public void run(String... args) {
        ConsoleController consoleController = initConsoleController();
        consoleController.run();
    }

    private ConsoleController initConsoleController() {
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
