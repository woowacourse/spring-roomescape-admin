package roomescape.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

public class ReservationRequestDTO {
    private String name;
    private LocalDate date;

    @JsonProperty("time_id")
    private Long timeId;

    public ReservationRequestDTO() {
    }

    public ReservationRequestDTO(String name, LocalDate date, Long timeId) {
        this.name = name;
        this.date = date;
        this.timeId = timeId;
    }

    public String getName() {
        return name;
    }

    public LocalDate getDate() {
        return date;
    }

    public Long getTimeId() {
        return timeId;
    }
}
