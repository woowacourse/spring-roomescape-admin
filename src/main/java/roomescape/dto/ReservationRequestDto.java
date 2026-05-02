package roomescape.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.STRING;

public record ReservationRequestDto (
        @NotBlank String name,
        @NotNull LocalDate date,
        Long timeId
){
}
