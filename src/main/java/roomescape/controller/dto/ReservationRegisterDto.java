package roomescape.controller.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.time.LocalTime;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;
import roomescape.domain.Reservation;

public record ReservationRegisterDto(
        @FutureOrPresent
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        LocalDate date,

        @NotBlank
        @Length(min = 1, max = 4)
        String name,

        @DateTimeFormat(pattern = "HH:mm")
        LocalTime time
) {
    public Reservation toEntity() {
        return new Reservation(name, date, time);
    }
}
