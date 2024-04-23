package roomescape.dto;

import roomescape.domain.reservation.Reservation;
import roomescape.domain.reservation.ReservationTime;

import java.time.LocalDate;
import java.util.List;

public record ReservationResponseDto(Long id, String name, LocalDate date, ReservationTime time) {

    public ReservationResponseDto(Long id, String name, LocalDate date, ReservationTime time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public ReservationResponseDto(Reservation reservation){
        this(reservation.getId(), reservation.getName().getName(),
                reservation.getDate().getDate(), reservation.getTime());
    }

    public static List<ReservationResponseDto> listOf(List<Reservation> reservations){
        return reservations.stream()
                .map(ReservationResponseDto::new)
                .toList();
    }
}
