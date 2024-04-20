package roomescape.dto;

import java.time.LocalDate;
import roomescape.entity.Reservation;

public class ReservationResponseDto {

    private final Long id;
    private final String name;
    private final LocalDate date;
    private final ReservationTimeDto time;

    public ReservationResponseDto(Long id, String name, LocalDate date, ReservationTimeDto time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public static ReservationResponseDto from(Reservation reservation) {
        return new ReservationResponseDto(
                reservation.getId(),
                reservation.getName(),
                reservation.getDate(),
                ReservationTimeDto.from(reservation.getTime())
        );
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDate getDate() {
        return date;
    }

    public ReservationTimeDto getTime() {
        return time;
    }
}
