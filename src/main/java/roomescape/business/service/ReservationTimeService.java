package roomescape.business.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roomescape.business.domain.ReservationTime;
import roomescape.infra.ReservationTimeDatabase;
import roomescape.infra.entity.ReservationTimeEntity;
import roomescape.presentation.dto.request.ReservationTimeCreateRequest;
import roomescape.presentation.dto.response.ReservationTimeResponse;

import java.util.List;

@Service
public class ReservationTimeService {

    private final ReservationTimeDatabase database;

    public ReservationTimeService(final ReservationTimeDatabase database) {
        this.database = database;
    }

    @Transactional
    public ReservationTimeResponse saveAndGet(final ReservationTimeCreateRequest request) {
        final ReservationTime reservationTime = new ReservationTime(request.startAt());
        final long savedId = database.saveAndGetId(ReservationTimeEntity.beforeSave(reservationTime));
        final ReservationTimeEntity entity = database.findById(savedId)
                .orElseThrow(() -> new IllegalStateException("예약 시간이 저장되었으나, 서버에서 문제가 발생하였습니다."));
        return ReservationTimeResponse.from(entity.toDomain(), entity.id());
    }

    @Transactional(readOnly = true)
    public List<ReservationTimeResponse> getAll() {
        return database.findAll().stream()
                .map(entity -> ReservationTimeResponse.from(entity.toDomain(), entity.id()))
                .toList();
    }

    @Transactional
    public void deleteById(final long id) {
        database.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 예약 시간 id 입니다."));
        database.deleteById(id);
    }
}
