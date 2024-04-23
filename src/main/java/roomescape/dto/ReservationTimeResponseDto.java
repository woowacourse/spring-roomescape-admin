package roomescape.dto;

import roomescape.domain.reservation.ReservationTime;

import java.time.LocalTime;
import java.util.List;

public record ReservationTimeResponseDto(Long id, LocalTime time) {
    public ReservationTimeResponseDto(ReservationTime time){
        this(time.getId(), time.getTime());
    }

    public static List<ReservationTimeResponseDto> listOf(List<ReservationTime> reservationTimes){
        return reservationTimes.stream()
                .map(ReservationTimeResponseDto::new)
                .toList();
    }
}
