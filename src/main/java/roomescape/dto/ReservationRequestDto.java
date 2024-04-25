package roomescape.dto;

import java.time.LocalDate;

public class ReservationRequestDto {

    private String name;
    private LocalDate date;
    private Long timeId;

    public ReservationRequestDto(String name, LocalDate localDate, Long timeId) {
        this.name = name;
        this.date = localDate;
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
