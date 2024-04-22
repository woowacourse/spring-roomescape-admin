package roomescape.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import roomescape.Reservation;

public record ReservationResponse(
        long id,
        String name,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        LocalDate date,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
        ReservationTimeResponse time) {

    public static ReservationResponse of(int id, Reservation reservation) {
        return new ReservationResponse(id, reservation.getName(), reservation.getDate(), new ReservationTimeResponse(id, reservation.getTime().getStartAt()));
    }
}
