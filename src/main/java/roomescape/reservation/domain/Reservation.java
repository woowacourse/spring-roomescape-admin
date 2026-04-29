package roomescape.reservation.domain;

import java.time.LocalDate;
import java.time.LocalTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Reservation {
    private Long id;
    private String name;
    private LocalDate date;
    private LocalTime time;
}
