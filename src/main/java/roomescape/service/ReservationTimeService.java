package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.domain.ReservationTime;
import roomescape.dto.request.ReservationTimeCreateRequest;
import roomescape.infra.ReservationTimeDatabase;

import java.util.List;

@Service
public class ReservationTimeService {

    private final ReservationTimeDatabase reservationTimeDatabase;

    public ReservationTimeService(final ReservationTimeDatabase reservationTimeDatabase) {
        this.reservationTimeDatabase = reservationTimeDatabase;
    }

    public ReservationTime saveAndGet(final ReservationTimeCreateRequest request) {
        final long savedId = reservationTimeDatabase.saveAndGetId(request);
        return reservationTimeDatabase.findById(savedId);
    }

    public List<ReservationTime> getAll() {
        return reservationTimeDatabase.findAll();
    }

    public void deleteById(final long id) {
        reservationTimeDatabase.deleteById(id);
    }
}
