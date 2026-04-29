package roomescape.domain.reservation.entity;

import java.time.LocalTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ReservationTime {

    private Long id;

    private LocalTime startAt;
}
