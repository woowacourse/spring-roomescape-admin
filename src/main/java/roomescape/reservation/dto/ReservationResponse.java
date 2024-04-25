package roomescape.reservation.dto;

import java.time.LocalDate;
import roomescape.reservation.domain.Reservation;
import roomescape.time.dto.TimeResponse;

public class ReservationResponse {
    private Long id;
    private String name;
    private LocalDate date;
    private TimeResponse time;

    public ReservationResponse() {
    }

    public ReservationResponse(final Long id, final String name, final LocalDate date, final TimeResponse time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public static ReservationResponse toResponse(final Reservation reservation) {
        return new ReservationResponse(
                reservation.getId(),
                reservation.getName(),
                reservation.getDate(),
                TimeResponse.toResponse(reservation.getTime())
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

    public TimeResponse getTime() {
        return time;
    }
}
