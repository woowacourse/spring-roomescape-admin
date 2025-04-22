package roomescape.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record ReservationRequest(

        @NotBlank(message = "날짜는 필수입니다.")
        @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}")
        String date,

        @NotBlank
        String name,

        @NotNull
        Long timeId
) {
}
