package roomescape.reservation;

import roomescape.reservation.domain.Reservation;
import roomescape.reservation.repository.ReservationRepository;
import roomescape.reservationTime.domain.ReservationTime;

import java.time.LocalDate;

public class ReservationHelper {

    private final ReservationRepository repository;

    public ReservationHelper(ReservationRepository repository) {
        this.repository = repository;
    }

    public Reservation createReservation(String name, int plusDays, ReservationTime time) {
        LocalDate date = LocalDate.now().plusDays(plusDays);
        return Reservation.of(name, date, time);
    }
}

