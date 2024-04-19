package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationRequestDto;
import roomescape.domain.ReservationResponseDto;
import roomescape.domain.Reservations;

import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class AdminService {

    private final Reservations reservations;
    private final AtomicLong index = new AtomicLong(1);


    public AdminService(Reservations reservations) {
        this.reservations = reservations;
    }

    public AdminService() {
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

    public Reservation findReservation(Long id) {
        return reservations.find(id);
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
