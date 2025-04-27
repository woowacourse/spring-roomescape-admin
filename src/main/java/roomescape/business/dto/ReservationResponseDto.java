package roomescape.business.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import roomescape.business.Reservation;

public record ReservationResponseDto(
        long id,
        String name,
        @JsonFormat(pattern = "yyyy-MM-dd") LocalDate date,
        ReservationTimeResponseDto time) {

    public static ReservationResponseDto from(Reservation reservation) {
        ReservationTimeResponseDto time = ReservationTimeResponseDto.from(reservation.getTime());
        return new ReservationResponseDto(
                reservation.getId(), reservation.getName(), reservation.getDate(), time);
    }
}
