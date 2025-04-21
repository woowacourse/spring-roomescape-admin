package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.Reservation;
import roomescape.dto.request.ReservationCreateRequest;
import roomescape.infra.ReservationDatabase;

import java.util.List;

@Service
public class ReservationService {

    private final ReservationDatabase reservationDatabase;

    public ReservationService(final ReservationDatabase reservationDatabase) {
        this.reservationDatabase = reservationDatabase;
    }

    public List<Reservation> getAll() {
        return reservationDatabase.findAll();
    }

    public Reservation saveAndGet(final ReservationCreateRequest request) {
        final long savedId = reservationDatabase.saveAndGetId(request);
        return reservationDatabase.findById(savedId);
    }

    public void deleteById(final long reservationId) {
        reservationDatabase.deleteById(reservationId);
    }
}
