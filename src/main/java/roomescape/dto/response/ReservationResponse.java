package roomescape.dto.response;

import roomescape.infra.entity.ReservationEntity;
import roomescape.infra.entity.ReservationTimeEntity;

import java.time.LocalDate;

public record ReservationResponse(
        long id,
        String name,
        LocalDate date,
        ReservationTimeResponse time
) {
    public static ReservationResponse from(ReservationEntity reservationEntity) {
        final ReservationTimeEntity timeEntity = reservationEntity.getTime();
        return new ReservationResponse(
                reservationEntity.getId(),
                reservationEntity.getName(),
                reservationEntity.getDate(),
                ReservationTimeResponse.from(timeEntity)
        );
    }
}
