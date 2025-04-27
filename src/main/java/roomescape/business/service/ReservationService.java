package roomescape.business.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roomescape.business.domain.Customer;
import roomescape.business.domain.Reservation;
import roomescape.business.domain.ReservationTime;
import roomescape.infra.ReservationDatabase;
import roomescape.infra.ReservationTimeDatabase;
import roomescape.infra.entity.ReservationEntity;
import roomescape.presentation.dto.request.ReservationCreateRequest;
import roomescape.presentation.dto.response.ReservationResponse;

import java.util.List;

@Service
public class ReservationService {

    private final ReservationDatabase reservationDatabase;
    private final ReservationTimeDatabase reservationTimeDatabase;

    public ReservationService(final ReservationDatabase reservationDatabase, final ReservationTimeDatabase reservationTimeDatabase) {
        this.reservationTimeDatabase = reservationTimeDatabase;
        this.reservationDatabase = reservationDatabase;
    }

    @Transactional
    public ReservationResponse saveAndGet(final ReservationCreateRequest request) {
        final ReservationTime reservationTime = reservationTimeDatabase.findById(request.timeId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 예약 시간입니다."))
                .toDomain();

        final Reservation reservation = new Reservation(new Customer(request.name()), request.date(), reservationTime);

        final long savedId = reservationDatabase.saveAndGetId(ReservationEntity.beforeSave(reservation, request.timeId()));
        final ReservationEntity entity = reservationDatabase.findById(savedId)
                .orElseThrow(() -> new IllegalStateException("예약이 저장되었으나, 서버에서 문제가 발생하였습니다."));

        return ReservationResponse.from(entity.toDomain(), entity.id(), entity.timeId());
    }

    @Transactional(readOnly = true)
    public List<ReservationResponse> getAll() {
        return reservationDatabase.findAll().stream()
                .map(entity -> ReservationResponse.from(entity.toDomain(), entity.id(), entity.timeId()))
                .toList();
    }

    @Transactional
    public void deleteById(final long id) {
        reservationDatabase.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 예약 id 입니다."));
        reservationDatabase.deleteById(id);
    }
}
