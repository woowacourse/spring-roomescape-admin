package roomescape.reservation.mapper;

import roomescape.reservation.domain.Reservation;
import roomescape.reservation.dto.CreateReservationRequest;
import roomescape.reservation.dto.ReservationResultResponse;
import roomescape.reservation.repository.entity.ReservationEntity;

public class ReservationMapper {

    private ReservationMapper() {
    }

    public static Reservation toReservation(CreateReservationRequest base) {
        return new Reservation(base.getName(), base.getDate(), base.getTime());
    }

    public static Reservation toReservation(ReservationEntity entity) {
        return new Reservation(entity.getId(), entity.getName(), entity.getDate(), entity.getTime());
    }

    public static ReservationResultResponse toReservationResultDto(Reservation reservation) {
        return new ReservationResultResponse(reservation.getId(), reservation.getName(), reservation.getDate(), reservation.getTime());
    }
}
