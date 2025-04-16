package roomescape.controller.dto;

import jakarta.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.time.LocalTime;
import org.springframework.format.annotation.DateTimeFormat;
import roomescape.domain.Reservation;

public record ReservationRegisterDto(
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        LocalDate date,

        @NotBlank
        String name,

        @DateTimeFormat(pattern = "HH:mm")
        LocalTime time
) {
    public Reservation toEntity() {
        return new Reservation(name, date, time);
    }
}
