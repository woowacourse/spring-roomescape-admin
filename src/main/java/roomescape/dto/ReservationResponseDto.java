package roomescape.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import roomescape.domain.Reservation;

public class ReservationResponseDto {

    private final Long id;
    private final String name;
    private final String date;
    private final String time;

    public ReservationResponseDto(Long id, String name, LocalDate date, LocalTime time) {
        this.id = id;
        this.name = name;
        this.date = date.toString();
        this.time = time.toString();
    }

    public ReservationResponseDto(Reservation reservation) {
        this(reservation.getId(), reservation.getName(), reservation.getDate(), reservation.getTime());
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

    public String getTime() {
        return time;
    }
}
