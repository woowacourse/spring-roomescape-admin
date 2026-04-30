package roomescape.dto;

import java.time.LocalDate;

public class ReservationRequestDto {
    private String name;
    private LocalDate date;
    private Long timeId;

    public ReservationRequestDto() {
    }

    public ReservationRequestDto(String name, LocalDate date, Long timeId) {
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
