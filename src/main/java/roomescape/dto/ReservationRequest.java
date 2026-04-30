package roomescape.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record ReservationRequest(
    @NotBlank(message = "이름은 필수 입력값입니다.")
    String name,

    @NotBlank(message = "날짜는 필수 입력값입니다.")
    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "날짜 형식이 올바르지 않습니다. (yyyy-MM-dd)")
    String date,

    @NotNull(message = "시간 ID는 필수 입력값입니다.")
    Long timeId
) {
}
