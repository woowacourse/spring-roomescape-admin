package roomescape.reservationtime.domain;

import java.time.LocalTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReservationTime {
    private Long id;
    private LocalTime startAt;
}
