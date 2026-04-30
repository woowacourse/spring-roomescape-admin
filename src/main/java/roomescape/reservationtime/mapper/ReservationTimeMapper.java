package roomescape.reservationtime.mapper;

import roomescape.reservationtime.domain.ReservationTime;
import roomescape.reservationtime.dto.ReservationTimeRequest;
import roomescape.reservationtime.dto.ReservationTimeResponse;

public class ReservationTimeMapper {

    private ReservationTimeMapper() { }

    public static ReservationTime toEntity(ReservationTimeRequest reservationTimeRequest) {
        return ReservationTime.builder()
                .startAt(reservationTimeRequest.startAt())
                .build();
    }

    public static ReservationTimeResponse toResponse(ReservationTime reservationTime) {
        return ReservationTimeResponse.builder()
                .id(reservationTime.getId())
                .startAt(reservationTime.getStartAt())
                .build();
    }
}
