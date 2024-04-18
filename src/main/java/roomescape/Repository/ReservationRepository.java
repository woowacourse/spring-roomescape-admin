package roomescape.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationResponse;
import roomescape.model.Reservation;

public class ReservationRepository {
    private final AtomicLong id = new AtomicLong(1);
    private final List<Reservation> reservations = new ArrayList<>();

    public ReservationResponse createReservation(ReservationRequest reservationRequest) {
        Reservation reservation = new Reservation(id.getAndIncrement(), reservationRequest.getName(),
                reservationRequest.getDate(), reservationRequest.getTime());
        reservations.add(reservation);
        return ReservationResponse.of(reservation);
    }

    public List<ReservationResponse> readReservations() {
        return reservations.stream()
                .map(ReservationResponse::of)
                .toList();
    }

    public boolean deleteReservation(Long id) {
        return reservations.removeIf(reservation -> reservation.equalId(id));
    }
}
