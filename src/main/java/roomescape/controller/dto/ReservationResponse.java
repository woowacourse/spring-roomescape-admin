package roomescape.controller.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDate;
import java.time.LocalTime;
import roomescape.entity.Reservation;

public class ReservationResponse {
    private final Long id;
    private final String name;
    private final LocalDate date;
    private final LocalTime time;

    @JsonCreator
    public ReservationResponse(@JsonProperty("id") long id,
                               @JsonProperty("name") String name,
                               @JsonProperty("date") LocalDate date,
                               @JsonProperty("time") LocalTime time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public static ReservationResponse from(Reservation reservation) {
        return new ReservationResponse(
                reservation.getId(),
                reservation.getName(),
                reservation.getStartDate(),
                reservation.getStartTime());
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTime() {
        return time;
    }
}
