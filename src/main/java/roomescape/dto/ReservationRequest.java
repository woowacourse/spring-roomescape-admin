package roomescape.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ReservationRequest(
    Long id,

    @NotBlank(message = "[ERROR] 이름은 반드시 필요합니다.")
    String name,

    @NotNull(message = "[ERROR] 날짜는 반드시 필요합니다.")
    LocalDate date,

    @NotNull(message = "[ERROR] 시간은 반드시 필요합니다.")
    Long timeId
) {
}
