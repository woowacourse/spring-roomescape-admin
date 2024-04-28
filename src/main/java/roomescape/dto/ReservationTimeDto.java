package roomescape.dto;

import roomescape.domain.ReservationTime;
import roomescape.repository.entity.ReservationTimeEntity;

public record ReservationTimeDto(Long id, String startAt) {

    public ReservationTimeDto(ReservationTimeEntity reservationTimeEntity) {
        this(reservationTimeEntity.id(), reservationTimeEntity.startAt());
    }

    public ReservationTimeEntity toEntity() {
        return new ReservationTimeEntity(id, startAt);
    }

    public ReservationTime toDomain() {
        return new ReservationTime(id, startAt);
    }
}
