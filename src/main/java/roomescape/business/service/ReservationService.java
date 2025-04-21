package roomescape.business.service;

import org.springframework.stereotype.Service;
import roomescape.business.domain.Reservation;
import roomescape.dto.request.ReservationCreateRequest;
import roomescape.infra.ReservationDatabase;

import java.util.List;

@Service
public class ReservationService {

    private final ReservationDatabase database;

    public ReservationService(final ReservationDatabase database) {
        this.database = database;
    }

    public List<Reservation> getAll() {
        return database.findAll();
    }

    public Reservation saveAndGet(final ReservationCreateRequest request) {
        final long savedId = database.saveAndGetId(request);
        return database.findById(savedId);
    }

    public void deleteById(final long reservationId) {
        database.deleteById(reservationId);
    }
}
