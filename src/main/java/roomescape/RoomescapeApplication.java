package roomescape;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import roomescape.console.ConsoleController;
import roomescape.console.fakedao.FakeReservationDao;
import roomescape.console.fakedao.FakeReservationDb;
import roomescape.console.fakedao.FakeReservationTimeDao;
import roomescape.console.fakedao.FakeReservationTimeDb;
import roomescape.service.ReservationService;
import roomescape.service.ReservationTimeService;

@SpringBootApplication
public class RoomescapeApplication implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(RoomescapeApplication.class, args);
    }

    @Override
    public void run(String... args) {
        ConsoleController consoleController = initConsoleController();
        consoleController.run();
    }

    private ConsoleController initConsoleController() {
        FakeReservationDb fakeReservationDb = new FakeReservationDb();
        FakeReservationTimeDb fakeReservationTimeDb = new FakeReservationTimeDb();
        FakeReservationDao fakeReservationDao
                = new FakeReservationDao(fakeReservationDb, fakeReservationTimeDb);
        FakeReservationTimeDao fakeReservationTimeDao
                = new FakeReservationTimeDao(fakeReservationDb, fakeReservationTimeDb);
        ReservationService reservationService
                = new ReservationService(fakeReservationDao, fakeReservationTimeDao);
        ReservationTimeService reservationTimeService
                = new ReservationTimeService(fakeReservationTimeDao);
        return new ConsoleController(reservationService, reservationTimeService);
    }
}
