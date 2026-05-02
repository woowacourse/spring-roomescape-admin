package roomescape.reservation.mapper;

import java.util.Optional;
import roomescape.reservation.domain.Reservation;
import roomescape.reservation.dto.ReservationRequest;
import roomescape.reservation.dto.ReservationResponse;
import roomescape.reservationtime.domain.ReservationTime;
import roomescape.reservationtime.dto.ReservationTimeResponse;
import roomescape.reservationtime.mapper.ReservationTimeMapper;

public class ReservationMapper {

    private ReservationMapper() {}

    public static Reservation toEntity(ReservationRequest reservationRequest, ReservationTime time) {
        return Reservation.builder()
                .name(reservationRequest.name())
                .date(reservationRequest.date())
                .time(time)
                .build();
    }

    public static ReservationResponse toResponse(Reservation reservation) {
        return ReservationResponse.builder()
                .id(reservation.getId())
                .name(reservation.getName())
                .date(reservation.getDate())
                .time(ReservationTimeMapper.toResponse(reservation.getTime()))
                .build();
    }
}
