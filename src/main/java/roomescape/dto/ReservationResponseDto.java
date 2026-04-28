package roomescape.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public record ReservationResponseDto(
    long id,
    String name,
    String date,
    String time
) {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    public static ReservationResponseDto from(long id, String name, LocalDate date, LocalTime time) {
        return new ReservationResponseDto(id, name, date.format(DATE_FORMATTER), time.format(TIME_FORMATTER));
    }
}
