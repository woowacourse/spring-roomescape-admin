package roomescape.dto;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public class ReservationRequestDto {

    @NotNull
    private final String name;

    @NotNull
    private final LocalDate date;

    @NotNull
    private final Long timeId;

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
