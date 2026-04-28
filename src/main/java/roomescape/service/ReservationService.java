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
    private final List<Reservation> reservations = new ArrayList<>();
    private final AtomicLong index = new AtomicLong(1);

    public ReservationResponse create(ReservationRequest reservationRequest) {
        Reservation reservation = new Reservation(
                index.getAndIncrement(),
                reservationRequest.name(),
                reservationRequest.date(),
                reservationRequest.time()
        );
        reservations.add(reservation);
        return ReservationResponse.from(reservation);
    }

    public List<ReservationResponse> read() {
        return reservations.stream()
                .map(ReservationResponse::from)
                .toList();
    }

    public void delete(Long id) {
        Reservation reservation = reservations.stream()
                .filter(r -> r.getId().equals(id))
                .findFirst()
                .orElseThrow(RuntimeException::new);
        reservations.remove(reservation);
    }
}
