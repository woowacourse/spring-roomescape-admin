package roomescape.domain.reservation.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public class ReservationRequestDTO {
    @NotBlank
    private final String name;

    @NotNull
    private final LocalDate date;

    @NotNull
    private final Long timeId;

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
