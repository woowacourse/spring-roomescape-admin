package roomescape.reservationtime.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import java.time.LocalTime;
import roomescape.reservationtime.domain.ReservationTime;

public record ReservationTimeCreateRequest(
        @NotBlank(message = "[ERROR] 시간은 비어있을 수 없습니다.")
        @Pattern(regexp = "^([01]\\d|2[0-3]):[0-5]\\d$", message = "[ERROR] 시간 형식은 HH:mm 이어야 합니다.")
        String startAt
) {

    public ReservationTime toEntity() {
        return ReservationTime.builder()
                .startAt(LocalTime.parse(startAt))
                .build();
    }
}
