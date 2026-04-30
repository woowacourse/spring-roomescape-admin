package roomescape.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ReservationRequest(
        @NotBlank(message = "이름은 필수 값입니다.")
        String name,
        @NotBlank(message = "날짜는 필수 값입니다.")
        String date,
        @NotNull(message = "time id는 필수 값입니다.")
        Long timeId
) {
}
