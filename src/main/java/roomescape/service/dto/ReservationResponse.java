package roomescape.service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

public class ReservationResponse {
    private final long id;
    private final String name;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private final LocalDate date;
    private final ReservationTimeResponse reservationTimeResponse;

    public ReservationResponse(long id, String name, LocalDate date, ReservationTimeResponse reservationTimeResponse) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.reservationTimeResponse = reservationTimeResponse;
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

    public ReservationTimeResponse getTime() {
        return reservationTimeResponse;
    }
}
