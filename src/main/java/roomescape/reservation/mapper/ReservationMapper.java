package roomescape.reservation.mapper;

import roomescape.reservation.domain.Reservation;
import roomescape.reservation.dto.CreateReservationRequest;
import roomescape.reservation.dto.ReservationResponse;
import roomescape.reservation.repository.entity.ReservationEntity;
import roomescape.time.domain.ReservationTime;
import roomescape.time.dto.ReservationTimeResponse;
import roomescape.time.repository.entity.ReservationTimeEntity;

public class ReservationMapper {

    private ReservationMapper() {
    }

    public static Reservation toReservation(CreateReservationRequest base, ReservationTime reservationTime) {
        return new Reservation(base.getName(), base.getDate(), reservationTime);
    }

    public static Reservation toReservation(ReservationEntity reservationEntity,
                                            ReservationTimeEntity reservationTimeEntity) {
        return new Reservation(reservationEntity.getId(), reservationEntity.getName(), reservationEntity.getDate(),
                new ReservationTime(reservationTimeEntity.getId(), reservationTimeEntity.getStartAt()));
    }

    public static ReservationResponse toReservationResultDto(Reservation reservation) {
        return new ReservationResponse(reservation.getId(), reservation.getName(), reservation.getDate(),
                new ReservationTimeResponse(reservation.getTime().getId(), reservation.getTime().getStartAt()));
    }

    public static ReservationEntity toReservationEntity(Reservation reservation) {
        return new ReservationEntity(reservation.getId(), reservation.getName(), reservation.getDate(),
                reservation.getTimeId());
    }
}
