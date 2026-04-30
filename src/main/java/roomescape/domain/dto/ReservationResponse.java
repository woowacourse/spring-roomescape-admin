package roomescape.domain.dto;

import lombok.Builder;
import org.springframework.format.annotation.DateTimeFormat;
import roomescape.domain.entity.Reservation;

import java.time.LocalDate;

@Builder
public record ReservationResponse(
        Long id,

        String name,

        @DateTimeFormat(pattern = "yyyy-mm-dd")
        LocalDate date,

        ReservationTimeResponse time
) {
    public static ReservationResponse from(Reservation reservation) {
        return ReservationResponse.builder()
                .id(reservation.getId())
                .name(reservation.getName())
                .date(reservation.getDate())
                .time(ReservationTimeResponse.from(reservation.getTime()))
                .build();
    }
}
