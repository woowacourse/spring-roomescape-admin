package roomescape.dto;

import roomescape.domain.reservation.ReservationTime;

import java.time.LocalTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public record ReservationTimeResponseDto(Long id, LocalTime time) {
    public ReservationTimeResponseDto(ReservationTime time){
        this(time.getId(), time.getTime());
    }

    public static Set<ReservationTimeResponseDto> listOf(List<ReservationTime> reservationTimes){
        return reservationTimes.stream()
                .map(ReservationTimeResponseDto::new)
                .collect(Collectors.toSet());
    }
}
