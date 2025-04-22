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
        return database.findById(savedId)
                .orElseThrow(() -> new IllegalStateException("예약이 저장되었으나, 서버에서 문제가 발생하였습니다."));
    }

    @Transactional(readOnly = true)
    public List<Reservation> getAll() {
        return database.findAll();
    }

    @Transactional
    public void deleteById(final long reservationId) {
        database.findById(reservationId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 예약 id 입니다."));
        database.deleteById(reservationId);
    }
}
