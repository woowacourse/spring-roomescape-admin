package roomescape.reservation.application;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Service;
import roomescape.reservation.domain.Reservation;
import roomescape.reservation.presentation.dto.request.ReservationSaveRequest;
import roomescape.reservation.presentation.dto.response.ReservationSaveResponse;

@Service
public class ReservationService {
    private List<Reservation> reservations = new ArrayList<>();
    private AtomicLong index = new AtomicLong(0);

    public ReservationSaveResponse saveReservation(ReservationSaveRequest body) {
        Reservation reservation = new Reservation(index.incrementAndGet(), body.name(), body.date(), body.time());
        reservations.add(reservation);
        return new ReservationSaveResponse(reservation.getId(), reservation.getName(), reservation.getDate(),
                reservation.getTime());
    }
}
