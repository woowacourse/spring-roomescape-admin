package roomescape.reservation.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ReservationRequest(

        @NotBlank(message = "이름은 비어 있을 수 없습니다.")
        String name,

        @NotBlank(message = "예약 날짜는 필수입니다.")
        String date,

        @NotNull(message = "시간 ID는 필수입니다.")
        Long timeId
) {
}
