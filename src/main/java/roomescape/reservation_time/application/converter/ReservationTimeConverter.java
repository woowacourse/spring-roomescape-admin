package roomescape.reservation_time.application.converter;

import roomescape.reservation_time.domain.ReservationTime;
import roomescape.reservation_time.domain.ReservationTimeId;
import roomescape.reservation_time.infrastructure.entity.ReservationTimeEntity;
import roomescape.reservation_time.ui.dto.ReservationTimeRequestDto;
import roomescape.reservation_time.ui.dto.ReservationTimeResponseDto;

import java.util.List;

public class ReservationTimeConverter {

    public static ReservationTime toDomain(final ReservationTimeRequestDto requestDto) {
        return ReservationTime.of(
                ReservationTimeId.unassigned(),
                requestDto.startAt());
    }

    public static ReservationTime toDomain(final ReservationTimeEntity entity) {
        return ReservationTime.of(
                ReservationTimeId.from(entity.getId()),
                entity.getTime().toLocalTime());
    }

    public static ReservationTime toDomain(final Long id) {
        return ReservationTime.of(
                ReservationTimeId.from(id),
                null);
    }

    public static ReservationTimeResponseDto toDto(final ReservationTime reservationTime) {
        return new ReservationTimeResponseDto(
                reservationTime.getId().getValue(),
                reservationTime.getValue());
    }

    public static List<ReservationTimeResponseDto> toDto(final List<ReservationTime> reservationTimes) {
        return reservationTimes.stream()
                .map(ReservationTimeConverter::toDto)
                .toList();
    }
}
