package roomescape.presentation.web.request;

import java.time.LocalTime;
import jakarta.validation.constraints.NotNull;
import roomescape.application.request.CreateReservationTimeRequest;

public record CreateReservationTimeWebRequest(

        @NotNull
        LocalTime startAt
) {

    public CreateReservationTimeRequest toServiceRequest() {
        return new CreateReservationTimeRequest(startAt);
    }
}
