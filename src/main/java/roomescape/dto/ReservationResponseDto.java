package roomescape.dto;

import roomescape.domain.reservation.Reservation;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public record ReservationResponseDto(Long id, String name, LocalDate date, LocalTime time) {

    public ReservationResponseDto(Long id, String name, LocalDate date, LocalTime time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public ReservationResponseDto(Reservation reservation){
        this(reservation.getId(), reservation.getName().getName(),
                reservation.getDate().getDate(), reservation.getTime().getTime());
    }

    public static Set<ReservationResponseDto> listOf(List<Reservation> reservations){
        return reservations.stream()
                .map(ReservationResponseDto::new)
                .collect(Collectors.toSet());
    }
}
