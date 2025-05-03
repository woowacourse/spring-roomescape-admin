package roomescape.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import roomescape.model.Reservation;
import roomescape.model.ReservationTime;

public record ReservationResDto(
        Long id,
        String name,
        @JsonFormat(pattern = "yyyy-MM-dd") LocalDate date,
        ReservationTimeResDto time
) {

    public static ReservationResDto of(Reservation reservation) {
        ReservationTime reservationTime = reservation.getTime();
        return new ReservationResDto(reservation.getId(), reservation.getName(), reservation.getDate(),
                new ReservationTimeResDto(reservationTime.getId(), reservationTime.getTime()));
    }
}
