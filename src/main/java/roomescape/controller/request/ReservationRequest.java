package roomescape.controller.request;

import java.time.LocalDate;
import java.time.LocalTime;

public record ReservationRequest(LocalDate date, String name, LocalTime time) {

    @Override
    public LocalDate date() {
        return date;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public LocalTime time() {
        return time;
    }
}
