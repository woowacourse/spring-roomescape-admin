package roomescape.reservation.mapper;

import roomescape.reservation.domain.Reservation;
import roomescape.reservation.dto.CreateReservationDto;
import roomescape.reservation.dto.ReservationResultDto;
import roomescape.reservation.repository.entity.ReservationEntity;

public class ReservationMapper {

    private ReservationMapper() {
    }

    public static Reservation toReservation(CreateReservationDto base) {
        return new Reservation(base.getName(), base.getDate(), base.getTime());
    }

    public static Reservation toReservation(ReservationEntity entity) {
        return new Reservation(entity.getId(), entity.getName(), entity.getDate(), entity.getTime());
    }

    public static ReservationResultDto toReservationResultDto(Reservation reservation) {
        return new ReservationResultDto(reservation.getId(), reservation.getName(), reservation.getDate(), reservation.getTime());
    }
}
