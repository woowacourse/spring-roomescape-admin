package roomescape.entity;

import java.time.LocalDate;
import java.time.LocalTime;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import roomescape.dto.ReservationRequestDto;

@RequiredArgsConstructor
@Getter
@Builder
public class Reservation {

    private final long id;
    private final String name;
    private final LocalDate date;
    private final LocalTime time;

    public static Reservation from(final long id,
        final ReservationRequestDto reservationRequestDto) {
        return Reservation.builder()
            .id(id)
            .name(reservationRequestDto.name())
            .date(reservationRequestDto.date())
            .time(reservationRequestDto.time())
            .build();
    }

}
