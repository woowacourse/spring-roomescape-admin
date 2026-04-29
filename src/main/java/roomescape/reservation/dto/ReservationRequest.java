package roomescape.reservation.dto;

import jakarta.validation.constraints.NotBlank;

public record ReservationRequest(
        @NotBlank String name,
        @NotBlank String date,
        @NotBlank String time
) {

}
