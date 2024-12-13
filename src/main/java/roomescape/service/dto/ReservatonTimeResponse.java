package roomescape.service.dto;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import roomescape.domain.ReservationTime;

public record ReservatonTimeResponse(long id, String startAt) {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    public ReservatonTimeResponse(long id, LocalTime time) {
        this(id, time.format(FORMATTER));
    }

    public ReservatonTimeResponse(ReservationTime reservationTime) {
        this(reservationTime.getId(), reservationTime.getStartAt().format(FORMATTER));
    }

}
