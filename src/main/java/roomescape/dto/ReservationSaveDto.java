package roomescape.dto;

import roomescape.validation.annotation.NotBlank;
import roomescape.validation.annotation.NotNull;

import java.time.LocalDate;

public record ReservationSaveDto(

        @NotBlank(message = "name은 비어있을 수 없습니다.")
        String name,

        @NotNull(message = "date는 Null일 수 없습니다.")
        LocalDate date,

        @NotNull(message = "timeId는 Null일 수 없습니다.")
        Long timeId

) {
}
