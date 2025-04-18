package roomescape.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import org.springframework.stereotype.Service;
import roomescape.dto.ReservationReq;
import roomescape.dto.ReservationRes;
import roomescape.model.Reservation;
import roomescape.model.Reservations;

@Service
public class DefaultReservationService implements ReservationService {

    private final Reservations reservations;

    public DefaultReservationService(final Reservations reservations) {
        this.reservations = reservations;
    }

    @Override
    public List<ReservationRes> get() {
        final List<ReservationRes> responses = new ArrayList<>();
        for (Entry<Long, Reservation> each : reservations.getReservations().entrySet()) {
            responses.add(ReservationRes.from(each.getKey(), each.getValue()));
        }
        return responses;
    }

    @Override
    public ReservationRes create(final ReservationReq req) {
        final Reservation reservation = req.toEntity();
        final long id = reservations.add(reservation);
        return ReservationRes.from(id, reservation);
    }

    @Override
    public void delete(final Long id) {
        reservations.deleteBy(id);
    }
}
