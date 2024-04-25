package roomescape.reservation.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import roomescape.reservationtime.controller.ReservationTimeResponse;

import java.time.LocalDate;

public class ReservationResponse {

    private final Long id;
    private final String name;
    private final LocalDate date;
    private final ReservationTimeResponse reservationTimeResponse;

    public ReservationResponse(final Long id, final String name, final LocalDate date, final ReservationTimeResponse reservationTimeResponse) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.reservationTimeResponse = reservationTimeResponse;
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

    @JsonProperty(value = "time")
    public ReservationTimeResponse getReservationTime() {
        return reservationTimeResponse;
    }
}
