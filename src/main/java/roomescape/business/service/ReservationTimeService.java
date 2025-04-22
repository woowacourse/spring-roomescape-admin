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
        return database.findById(savedId)
                .orElseThrow(() -> new IllegalStateException("예약 시간이 저장되었으나, 서버에서 문제가 발생하였습니다."));
    }

    @Transactional(readOnly = true)
    public List<ReservationTime> getAll() {
        return database.findAll();
    }

    @Transactional
    public void deleteById(final long id) {
        database.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 예약 시간 id 입니다."));
        database.deleteById(id);
    }
}
