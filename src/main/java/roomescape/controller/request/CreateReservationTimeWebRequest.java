package roomescape.controller.request;

import java.time.LocalTime;
import jakarta.validation.constraints.NotNull;
import roomescape.service.request.CreateReservationTimeRequest;

public record CreateReservationTimeWebRequest(@NotNull LocalTime startAt) {

    public CreateReservationTimeRequest toServiceRequest() {
        return new CreateReservationTimeRequest(startAt);
    }
}
