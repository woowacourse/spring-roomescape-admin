package roomescape.reservation.application.converter;

import roomescape.common.domain.DomainEntityId;
import roomescape.reservation.domain.Reservation;
import roomescape.reservation.domain.ReservationDate;
import roomescape.reservation.domain.ReserverName;
import roomescape.reservation_time.domain.ReservationTime;
import roomescape.reservation.infrastructure.entity.ReservationEntity;
import roomescape.reservation.ui.dto.ReservationRequestDto;
import roomescape.reservation.ui.dto.ReservationResponseDto;

import java.util.List;

public class ReservationConverter {

    public static Reservation toDomain(final ReservationEntity entity) {
        return Reservation.of(
                DomainEntityId.from(entity.getId()),
                ReserverName.from(entity.getName()),
                ReservationDate.from(entity.getDate()),
                ReservationTime.of(DomainEntityId.notAssigned(), entity.getTime()));
    }

    public static Reservation toDomain(final ReservationEntity reservationEntity,
                                       final ReservationTime reservationTime) {
        return Reservation.of(
                DomainEntityId.from(reservationEntity.getId()),
                ReserverName.from(reservationEntity.getName()),
                ReservationDate.from(reservationEntity.getDate()),
                reservationTime);
    }

    public static Reservation toDomain(final ReservationRequestDto requestDto) {
        return Reservation.of(
                DomainEntityId.notAssigned(),
                ReserverName.from(requestDto.name()),
                ReservationDate.from(requestDto.date()),
                ReservationTime.of(DomainEntityId.notAssigned(), requestDto.time()));
    }

    public static ReservationResponseDto toDto(final Reservation reservation) {
        return new ReservationResponseDto(
                reservation.getId().getValue(),
                reservation.getName().getValue(),
                reservation.getDate().getValue(),
                reservation.getTime().getValue());
    }

    public static List<ReservationResponseDto> toDto(final List<Reservation> reservations) {
        return reservations.stream()
                .map(ReservationConverter::toDto)
                .toList();
    }
}
