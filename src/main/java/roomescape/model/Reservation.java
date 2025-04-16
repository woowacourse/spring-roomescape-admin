package roomescape.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import java.time.LocalTime;

public record Reservation(Long id, String name, @JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Seoul") LocalDate date, @JsonFormat(pattern = "HH:mm", timezone = "Asia/Seoul") LocalTime time) {

    public static Reservation injectId(Reservation reservation, Long id){
        return new Reservation(id, reservation.name, reservation.date, reservation.time);
    }
}
