package roomescape.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import org.springframework.stereotype.Service;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationResponse;
import roomescape.model.Reservation;
import roomescape.model.Reservations;

@Service
public class ReservationService {

    private final Reservations reservations;

    public ReservationService(final Reservations reservations) {
        this.reservations = reservations;
    }

    public List<ReservationResponse> get() {
        final List<ReservationResponse> responses = new ArrayList<>();
        for (Entry<Long, Reservation> each : reservations.getReservations().entrySet()) {
            responses.add(ReservationResponse.from(each.getKey(), each.getValue()));
        }
        return responses;
    }

    public ReservationResponse create(final ReservationRequest req) {
        final Reservation reservation = req.toEntity();
        final long id = reservations.add(reservation);
        return ReservationResponse.from(id, reservation);
    }

    public void delete(final Long id) {
        reservations.deleteBy(id);
    }
}
