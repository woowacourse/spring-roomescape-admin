package roomescape.business.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roomescape.infra.ReservationDatabase;
import roomescape.infra.entity.ReservationEntity;
import roomescape.presentation.dto.request.ReservationCreateRequest;
import roomescape.presentation.dto.response.ReservationResponse;

import java.util.List;

@Service
public class ReservationService {

    private final ReservationDatabase database;

    public ReservationService(final ReservationDatabase database) {
        this.database = database;
    }

    @Transactional
    public ReservationResponse saveAndGet(final ReservationCreateRequest request) {
        final long savedId = database.saveAndGetId(ReservationEntity.beforeSave(request));
        final ReservationEntity entity = database.findById(savedId)
                .orElseThrow(() -> new IllegalStateException("예약이 저장되었으나, 서버에서 문제가 발생하였습니다."));

        return ReservationResponse.from(entity.toDomain(), entity.id(), entity.timeId());
    }

    @Transactional(readOnly = true)
    public List<ReservationResponse> getAll() {
        return database.findAll().stream()
                .map(entity -> ReservationResponse.from(entity.toDomain(), entity.id(), entity.timeId()))
                .toList();
    }

    @Transactional
    public void deleteById(final long id) {
        database.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 예약 id 입니다."));
        database.deleteById(id);
    }
}
