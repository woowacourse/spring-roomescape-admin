package roomescape.dto;

import roomescape.validation.annotation.NotBlank;

import java.time.LocalDate;

public record ReservationSaveDto(

        @NotBlank(message = "name은 비어있을 수 없습니다.")
        String name,

        LocalDate date,

        Long timeId
) {
}
