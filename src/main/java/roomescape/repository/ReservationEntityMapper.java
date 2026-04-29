package roomescape.repository;

import org.springframework.stereotype.Component;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;

@Component
public class ReservationEntityMapper {

    private final ReservationTimeEntityMapper timeEntityMapper;

    public ReservationEntityMapper(ReservationTimeEntityMapper timeEntityMapper) {
        this.timeEntityMapper = timeEntityMapper;
    }

    public ReservationEntity toReservationEntity(Reservation reservation) {
        return new ReservationEntity(
                reservation.id(),
                reservation.name(),
                reservation.date(),
                convertReservationTimeFromReservationEntityObject(reservation));
    }

    private ReservationTimeEntity convertReservationTimeFromReservationEntityObject(Reservation reservation) {
        return timeEntityMapper.toReservationTimeEntity(reservation.time());
    }

    public Reservation toReservation(ReservationEntity entity) {
        return new Reservation(
                entity.id(),
                entity.name(),
                entity.date(),
                convertReservationTimeEntityToDomainObject(entity)
        );
    }

    private ReservationTime convertReservationTimeEntityToDomainObject(ReservationEntity entity) {
        return timeEntityMapper.toReservationTime(entity.timeEntity());
    }
}
