package roomescape.controller.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDate;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

public record ReservationRegister(
        @FutureOrPresent
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        LocalDate date,

        @NotBlank
        @Length(min = 1, max = 4)
        String name,

        long timeId
) {
}
