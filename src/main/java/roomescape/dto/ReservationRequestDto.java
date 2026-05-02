package roomescape.dto;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record ReservationRequestDto(
        @NotNull
        String name,

        @NotNull
        LocalDate date,

        @NotNull
        Long timeId
) { }
