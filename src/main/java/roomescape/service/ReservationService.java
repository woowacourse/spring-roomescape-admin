package roomescape.service;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import roomescape.dto.ReservationResponseDto;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationRequestDto;
import roomescape.domain.Reservations;

public class ReservationService {

    private final Reservations reservations;
    private final AtomicLong index = new AtomicLong(1);

    public ReservationService(Reservations reservations) {
        this.reservations = reservations;
    }

    public ReservationService() {
        this(new Reservations());
    }

    public List<Reservation> getAllReservations() {
        return reservations.getReservations();
    }

    public ReservationResponseDto addReservation(ReservationRequestDto reservationRequestDto) {
        Reservation reservation = new Reservation(
                index.getAndIncrement(), reservationRequestDto.name(), reservationRequestDto.date(), reservationRequestDto.time()
        );
        reservations.add(reservation);
        return new ReservationResponseDto(reservation);
    }

    public void deleteReservation(Long id) {
        reservations.delete(id);
    }
}
