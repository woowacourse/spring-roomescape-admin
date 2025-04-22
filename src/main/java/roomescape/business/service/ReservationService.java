package roomescape.business.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

    @Transactional
    public Reservation saveAndGet(final ReservationCreateRequest request) {
        final long savedId = database.saveAndGetId(request);
        return database.findById(savedId);
    }

    @Transactional(readOnly = true)
    public List<Reservation> getAll() {
        return database.findAll();
    }

    @Transactional
    public void deleteById(final long reservationId) {
        database.deleteById(reservationId);
    }
}
