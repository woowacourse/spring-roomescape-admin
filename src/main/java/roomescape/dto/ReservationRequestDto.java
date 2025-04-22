package roomescape.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;

public record ReservationRequestDto(
        @NotEmpty(message = "예약자명은 필수입니다.") String name,
        @NotNull(message = "예약 날짜는 필수입니다.") LocalDate date,
        @NotNull(message = "예약 시간은 필수입니다.") LocalTime time
) {
}
