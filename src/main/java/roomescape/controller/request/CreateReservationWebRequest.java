package roomescape.controller.request;

import java.time.LocalDate;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import roomescape.application.request.CreateReservationRequest;

public record CreateReservationWebRequest(

        @NotNull
        LocalDate date,

        @NotNull
        String name,

        @Positive
        @NotNull
        Long timeId
) {

    public CreateReservationRequest toServiceRequest() {
        return new CreateReservationRequest(date, name, timeId);
    }
}
