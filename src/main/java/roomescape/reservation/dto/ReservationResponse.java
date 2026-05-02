package roomescape.reservation.dto;

import java.time.LocalDate;
import lombok.Builder;
import roomescape.reservation.domain.Reservation;
import roomescape.reservationtime.dto.ReservationTimeResponse;

@Builder
public record ReservationResponse (Long id, String name, LocalDate date, ReservationTimeResponse time) {

    public static ReservationResponse from(Reservation rerservation) {
        return ReservationResponse.builder()
                .id(rerservation.getId())
                .name(rerservation.getName())
                .date(rerservation.getDate())
                .time(ReservationTimeResponse.from(rerservation.getTime()))
                .build();
    }
}
