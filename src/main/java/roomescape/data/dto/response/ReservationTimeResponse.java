package roomescape.data.dto.response;

import java.time.LocalDate;

public class ReservationTimeResponse {

    private final long id;
    private final LocalDate date;

    public ReservationTimeResponse(final long id, final LocalDate date) {
        this.id = id;
        this.date = date;
    }

    public long getId() {
        return id;
    }

    public LocalDate getDate() {
        return date;
    }
}
