package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.domain.ReservationTime;
import roomescape.dto.request.ReservationTimeCreateRequest;
import roomescape.infra.ReservationTimeDatabase;

import java.util.List;

@Service
public class ReservationTimeService {

    private final ReservationTimeDatabase database;

    public ReservationTimeService(final ReservationTimeDatabase database) {
        this.database = database;
    }

    public ReservationTime saveAndGet(final ReservationTimeCreateRequest request) {
        final long savedId = database.saveAndGetId(request);
        return database.findById(savedId);
    }

    public List<ReservationTime> getAll() {
        return database.findAll();
    }

    public void deleteById(final long id) {
        database.deleteById(id);
    }
}
