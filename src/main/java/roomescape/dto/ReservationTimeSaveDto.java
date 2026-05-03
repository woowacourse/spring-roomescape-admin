package roomescape.dto;

import roomescape.validation.annotation.NotNull;

import java.time.LocalTime;

public record ReservationTimeSaveDto(

        @NotNull(message = "startAt은 Null일 수 없습니다.")
        LocalTime startAt

) {
}
