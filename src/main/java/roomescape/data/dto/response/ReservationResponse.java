package roomescape.data.dto.response;

import java.time.LocalDate;
import roomescape.data.vo.ReservationTime;

public class ReservationResponse {
    private final String name;
    private final LocalDate date;
    private final ReservationTime time;

    public ReservationResponse(final String name, final LocalDate date, final ReservationTime time) {
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public LocalDate getDate() {
        return date;
    }

    public ReservationTime getTime() {
        return time;
    }
}
