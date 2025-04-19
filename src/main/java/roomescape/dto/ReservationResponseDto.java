package roomescape.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import roomescape.model.Reservation;
import roomescape.model.ReservationDateTime;

public record ReservationResponseDto (
        Long id,
        String name,
        LocalDate date,
        LocalTime time
){
    public static ReservationResponseDto from(Reservation reservationInfo) {
        return new ReservationResponseDto(
                reservationInfo.getId(),
                reservationInfo.getName(),
                reservationInfo.getDate(),
                reservationInfo.getTime()
        );
    }
}
