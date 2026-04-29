package roomescape.domain.reservation.entity;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public class Reservation {

    @Setter
    private Long id;

    private String name;

    private LocalDate date;

    private ReservationTime time;

    public Reservation(String name, LocalDate date, ReservationTime time) {
        this.name = name;
        this.date = date;
        this.time = time;
    }
}
