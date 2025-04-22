package roomescape.business.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roomescape.business.domain.ReservationTime;
import roomescape.dto.request.ReservationTimeCreateRequest;
import roomescape.infra.ReservationTimeDatabase;

import java.util.List;

@Service
public class ReservationTimeService {

    private final ReservationTimeDatabase database;

    public ReservationTimeService(final ReservationTimeDatabase database) {
        this.database = database;
    }

    @Transactional
    public ReservationTime saveAndGet(final ReservationTimeCreateRequest request) {
        final long savedId = database.saveAndGetId(request);
        return database.findById(savedId);
    }

    @Transactional(readOnly = true)
    public List<ReservationTime> getAll() {
        return database.findAll();
    }

    @Transactional
    public void deleteById(final long id) {
        database.deleteById(id);
    }
}
