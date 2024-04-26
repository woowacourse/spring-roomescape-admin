package roomescape.dto.request;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record ReservationTimeCreateRequest(
        @NotNull @Pattern(regexp = "^(?:[01]\\d|2[0-3]):[0-5]\\d$") String startAt) {
}
