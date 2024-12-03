package roomescape.domain;

import java.time.LocalDate;
import java.time.LocalTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Reservation {

    private Long id;
    private String userName;
    private LocalDate date;
    private LocalTime time;
}
