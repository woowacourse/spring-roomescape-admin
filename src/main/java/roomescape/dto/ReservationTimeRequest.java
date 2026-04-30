package roomescape.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record ReservationTimeRequest(
    @NotBlank(message = "시작 시간은 필수 입력값입니다.")
    @Pattern(regexp = "\\d{2}:\\d{2}", message = "시간 형식이 올바르지 않습니다. (HH:mm)")
    String startAt
) {
}
