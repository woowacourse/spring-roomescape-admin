package roomescape.dto.response;

import java.time.LocalTime;
import java.util.List;
import roomescape.domain.ReservationTime;

public record TimeResponse(
        Long id,
        LocalTime startAt
) {
    public static TimeResponse toDto(ReservationTime reservationTime) {
        return new TimeResponse(reservationTime.getId(), reservationTime.getStartAt());
    }

    public static List<TimeResponse> toDtos(List<ReservationTime> reservationTimes) {
        return reservationTimes.stream()
                .map(TimeResponse::toDto)
                .toList();
    }
}
