package roomescape.dto.response;

import java.time.format.DateTimeFormatter;
import java.util.List;
import roomescape.model.ReservationTime;

public record TimeResponse(
        Long id,
        String startAt
) {
    private final static DateTimeFormatter RESERVATION_TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    public static TimeResponse toDto(ReservationTime reservationTime) {
        String formattedStartAt = reservationTime.getStartAt().format(RESERVATION_TIME_FORMATTER);
        Long id = reservationTime.getId();
        return new TimeResponse(id, formattedStartAt);
    }

    public static List<TimeResponse> toDtos(List<ReservationTime> reservationTimes) {
        return reservationTimes.stream()
                .map(TimeResponse::toDto)
                .toList();
    }
}
