package roomescape.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import roomescape.model.Reservation;
import roomescape.model.ReservationTime;

import java.time.LocalDate;

public record ReservationResponseDto(Long id, String name, @JsonFormat(pattern = "yyyy-MM-dd") LocalDate date,
                                     ReservationTimeResponseDto time) {

    public static ReservationResponseDto from(Reservation reservation) {
        ReservationTime reservationTime = reservation.reservationTime();
        ReservationTimeResponseDto responseDto = ReservationTimeResponseDto.from(reservationTime);

        return new ReservationResponseDto(
                reservation.id(),
                reservation.name(),
                reservation.date(),
                responseDto);
    }
}
