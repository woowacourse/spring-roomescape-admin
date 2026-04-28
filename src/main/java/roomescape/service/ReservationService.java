package roomescape.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Service;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationResponse;

@Service
public class ReservationService {

    private List<Reservation> reservations = new ArrayList<>();
    private AtomicLong index = new AtomicLong(1);

    public ReservationResponse createReservation(ReservationRequest request) {
        Reservation reservation = new Reservation(
            index.getAndIncrement(),
            request.name(),
            request.date(),
            request.time()
        );
        reservations.add(reservation);
        return ReservationResponse.from(reservation);
    }

    public List<ReservationResponse> getReservations() {
        List<ReservationResponse> responses = new ArrayList<>();
        for (Reservation reservation : reservations) {
            ReservationResponse response = ReservationResponse.from(reservation);
            responses.add(response);
        }
        return responses;
    }

    public void deleteReservation(Long id) {
        reservations.removeIf(
            reservation -> reservation.getId().equals(id)
        );
    }
}
