package roomescape.dto;

import java.time.LocalDate;

public class ReservationCreateReqDto {

    private String name;
    private LocalDate date;
    private Long timeId;

    public ReservationCreateReqDto(String name, LocalDate date, Long timeId) {
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
