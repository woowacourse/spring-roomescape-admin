package roomescape.domain.reservation.entity;

import java.time.LocalDate;
import java.time.LocalTime;
import lombok.Getter;
import lombok.Setter;

@Getter
public class Reservation {

    @Setter
    private Long id;

    private String name;

    private LocalDate date;

    private LocalTime time;
}
