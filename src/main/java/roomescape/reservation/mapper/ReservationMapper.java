package roomescape.reservation.mapper;

import roomescape.reservation.domain.Reservation;
import roomescape.reservation.dto.CreateReservationRequest;
import roomescape.reservation.dto.ReservationResultResponse;
import roomescape.reservation.repository.entity.ReservationEntity;
import roomescape.time.domain.ReservationTime;
import roomescape.time.dto.ReservationTimeResponse;
import roomescape.time.repository.entity.ReservationTimeEntity;

public class ReservationMapper {

    private ReservationMapper() {
    }

    public static Reservation toReservation(CreateReservationRequest base) {
        return new Reservation(base.getName(), base.getDate(), new ReservationTime(base.getTimeId()));
    }

    public static Reservation toReservation(ReservationEntity reservationEntity,
                                            ReservationTimeEntity reservationTimeEntity) {
        return new Reservation(reservationEntity.getId(), reservationEntity.getName(), reservationEntity.getDate(),
                new ReservationTime(reservationTimeEntity.getId(), reservationTimeEntity.getStartAt()));
    }

    public static ReservationResultResponse toReservationResultDto(Reservation reservation) {
        return new ReservationResultResponse(reservation.getId(), reservation.getName(), reservation.getDate(),
                new ReservationTimeResponse(reservation.getTime().getId(), reservation.getTime().getStartAt()));
    }

    public static ReservationEntity toReservationEntity(Long reservationId, Reservation reservation) {
        return new ReservationEntity(reservationId, reservation.getName(), reservation.getDate(),
                reservation.getTimeId());
    }
}
