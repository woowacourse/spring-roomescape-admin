package roomescape.test.fixture;

import java.time.LocalDate;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.repository.ReservationRepository;

public class ReservationFixture {

    public static Reservation createReservation(String name, LocalDate date, ReservationTime reservationTime) {
        return Reservation.createWithoutId(name, date, reservationTime);
    }

    public static Reservation addReservationInRepository(
            ReservationRepository repository, LocalDate date, ReservationTime time) {
        Reservation reservation = ReservationFixture.createReservation("reservation1", date, time);
        long reservationId = repository.add(reservation);
        return repository.findById(reservationId).get();
    }
}
