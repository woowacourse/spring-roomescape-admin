package roomescape.reservation.application.converter;

import roomescape.reservation.domain.Reservation;
import roomescape.reservation.domain.ReservationDate;
import roomescape.reservation.domain.ReservationId;
import roomescape.reservation.domain.ReserverName;
import roomescape.reservation.infrastructure.entity.ReservationEntity;
import roomescape.reservation.ui.dto.ReservationRequestDto;
import roomescape.reservation.ui.dto.ReservationResponseDto;
import roomescape.reservation_time.application.converter.ReservationTimeConverter;
import roomescape.reservation_time.domain.ReservationTime;

import java.util.List;

public class ReservationConverter {

    public static Reservation toDomain(final ReservationEntity reservationEntity) {
        return Reservation.of(
                ReservationId.from(reservationEntity.getId()),
                ReserverName.from(reservationEntity.getName()),
                ReservationDate.from(reservationEntity.getDate().toLocalDate()),
                ReservationTimeConverter.toDomain(reservationEntity.getTime()));
    }

    public static Reservation toDomain(final ReservationRequestDto requestDto,
                                       final ReservationTime reservationTime) {
        return Reservation.of(
                ReservationId.unassigned(),
                ReserverName.from(requestDto.name()),
                ReservationDate.from(requestDto.date()),
                reservationTime);
    }

    public static ReservationResponseDto toDto(final Reservation reservation) {
        return new ReservationResponseDto(
                reservation.getId().getValue(),
                reservation.getName().getValue(),
                reservation.getDate().getValue(),
                ReservationTimeConverter.toDto(reservation.getTime()));
    }

    public static List<ReservationResponseDto> toDto(final List<Reservation> reservations) {
        return reservations.stream()
                .map(ReservationConverter::toDto)
                .toList();
    }
}
