package roomescape.user.domain;

import java.time.LocalDate;
import java.time.LocalTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Reservation {

    private Long id;
    private String name;
    private LocalDate date;
    private LocalTime time;
}
