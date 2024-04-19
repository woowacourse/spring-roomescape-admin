package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.domain.reservation.Reservation;
import roomescape.domain.reservation.Reservations;
import roomescape.dto.ReservationRequestDto;
import roomescape.dto.ReservationResponseDto;

import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class ReservationService {

    private final Reservations reservations;
    private final AtomicLong index = new AtomicLong(1);


    public ReservationService(Reservations reservations) {
        this.reservations = reservations;
    }

    public ReservationService() {
        this(new Reservations());
    }

    public Set<Reservation> getAllReservations() {
        return reservations.getReservations();
    }

    public ReservationResponseDto reserve(ReservationRequestDto reservationRequestDto) {
        Reservation reservation = createReservation(reservationRequestDto);
        addReservation(reservation);
        return reservation.toResponseDto();
    }

    public void deleteReservation(Long id) {
        reservations.delete(id);
    }

    private void addReservation(Reservation reservation) {
        reservations.add(reservation.getId(), reservation);
    }

    private Reservation createReservation(ReservationRequestDto reservationRequestDto) {
        return new Reservation(index.getAndIncrement(), reservationRequestDto);
    }
}
