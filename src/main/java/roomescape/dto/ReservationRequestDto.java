package roomescape.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.STRING;

public record ReservationRequestDto (
        @NotBlank   @Size(max = 20, message = "이름은 20자 이하여야 합니다")
        String name,
        @NotNull LocalDate date,
        @NotNull Long timeId
){
}
