package roomescape.dto;

import roomescape.entity.ReservationEntity;

public record ReservationResponse(
        long id,
        String name,
        String date,
        String time) {
    public ReservationResponse(ReservationEntity reservationEntity) {
        this(reservationEntity.getId(),
                reservationEntity.getName(),
                reservationEntity.getDate(),
                reservationEntity.getTime());
    }
}
