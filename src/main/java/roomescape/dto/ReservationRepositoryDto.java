package roomescape.dto;

import roomescape.repository.entity.ReservationEntity;

public record ReservationRepositoryDto(Long id, String name, String date, Long timeId) {

    public ReservationRepositoryDto(ReservationEntity reservationEntity) {
        this(reservationEntity.id(), reservationEntity.name(), reservationEntity.date(), reservationEntity.timeId());
    }

    public ReservationEntity toEntity() {
        return new ReservationEntity(id, name, date, timeId);
    }
}
