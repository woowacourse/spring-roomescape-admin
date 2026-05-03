package roomescape.dto;

import roomescape.domain.Reservation;

import java.time.LocalDate;

public class ReservationResDto {

    private Long id;
    private String name;
    private LocalDate date;
    private ReservationTimeResDto time;

    private ReservationResDto(Long id, String name, LocalDate date, ReservationTimeResDto time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public static ReservationResDto from(Reservation reservation) {
        ReservationTimeResDto timeDto = ReservationTimeResDto.from(reservation.getTime());
        return new ReservationResDto(
                reservation.getId(),
                reservation.getName(),
                reservation.getDate(),
                timeDto
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

    public ReservationTimeResDto getTime() {
        return time;
    }
}
