package roomescape.dto;

import roomescape.entity.Reservation;
import roomescape.entity.ReservationTime;

public class ReservationResponseDto {

    private final Long id;
    private final String name;
    private final String date;
    private final ReservationTime time;

    private ReservationResponseDto(Long id, String name, String date, ReservationTime time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public static ReservationResponseDto from(Reservation reservation) {
        return new ReservationResponseDto(
                reservation.getId(),
                reservation.getName(),
                reservation.getDate().toString(),
                reservation.getReservationTime()
        );
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public ReservationTime getTime() {
        return time;
    }
}
