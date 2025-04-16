package roomescape.model;

import java.time.LocalDate;
import java.time.LocalTime;

public record Reservation(Long id, String name, LocalDate date, LocalTime time) {

    public static Reservation injectId(Reservation reservation, Long id){
        return new Reservation(id, reservation.name, reservation.date, reservation.time);
    }
}
