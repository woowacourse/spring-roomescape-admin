package roomescape.entity;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import roomescape.dto.ReservationRequestDto;

@RequiredArgsConstructor
@Getter
@Builder
public class Reservation {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    private final long id;
    private final String name;
    private final LocalDate date;
    private final LocalTime time;

    public static Reservation from(final long id, final ReservationRequestDto reservationRequestDto) {
        return Reservation.builder()
            .id(id)
            .name(reservationRequestDto.name())
            .date(LocalDate.parse(reservationRequestDto.date(), DATE_FORMATTER))
            .time(LocalTime.parse(reservationRequestDto.time(), TIME_FORMATTER))
            .build();
    }

}
