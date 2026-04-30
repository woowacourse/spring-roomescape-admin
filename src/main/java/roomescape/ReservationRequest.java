package roomescape;

import java.time.LocalDate;

public class ReservationRequest {

    private String name;
    private LocalDate date;
    private Long timeId;

    public String getName() {
        return name;
    }

    public LocalDate getDate() {
        return date;
    }

    public Long getTimeId() {
        return timeId;
    }
}
