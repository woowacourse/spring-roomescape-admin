package roomescape.reservation.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;
import org.springframework.stereotype.Service;
import roomescape.reservation.controller.request.ReservationCreateRequest;
import roomescape.reservation.domain.Reservation;
import roomescape.reservation.domain.Reservations;
import roomescape.reservation.service.exception.ReservationNotFoundException;

@Service
public class ReservationService {

    private final Reservations reservations = new Reservations();

    public Reservations findReservations() {
        return reservations;
    }

    public Reservation createReservation(ReservationCreateRequest request) {
        Reservation reservation = new Reservation(
                reservations.generateId(),
                request.name(),
                LocalDateTime.of(LocalDate.parse(request.date()), LocalTime.parse(request.time()))
        );
        reservations.create(reservation);
        return reservation;
    }

    public Reservation findReservation(Long id) {
        Optional<Reservation> reservation = reservations.findById(id);

        if (reservation.isEmpty()) {
            throw new ReservationNotFoundException("[ERROR] 예약을 찾을 수 없습니다.");
        }

        return reservation.get();
    }

    public void delete(Reservation reservation) {
        reservations.delete(reservation);
    }
}
