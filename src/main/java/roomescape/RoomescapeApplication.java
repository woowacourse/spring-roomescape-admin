package roomescape;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RoomescapeApplication /*implements CommandLineRunner*/ {
    public static void main(String[] args) {
        SpringApplication.run(RoomescapeApplication.class, args);
    }

//    @Override
//    public void run(String... args) throws Exception {
//        Reservations reservations = new Reservations();
//        ReservationTimes reservationTimes = new ReservationTimes();
//        ConsoleController controller = new ConsoleController(
//                new ReservationService(reservations, reservationTimes),
//                new ReservationTimeService(reservationTimes)
//        );
//        controller.run();
//    }
}
