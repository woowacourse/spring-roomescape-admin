package roomescape.reservationtime.mapper;

import roomescape.reservationtime.domain.ReservationTime;
import roomescape.reservationtime.dto.ReservationTimeRequest;

public class ReservationTimeMapper {

    private ReservationTimeMapper() { }

    public static ReservationTime toEntity(ReservationTimeRequest reservationTimeRequest) {
        return ReservationTime.builder()
                .startAt(reservationTimeRequest.startAt())
                .build();
    }
}
