package roomescape.dao;

import java.time.LocalDate;
import java.time.LocalTime;

public class ReservationDao {
    private final String name;
    private final LocalDate date;
    private final Long timeId;

    public ReservationDao(String name, LocalDate date, Long timeId) {
        this.name = name;
        this.date = date;
        this.timeId = timeId;
    }

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
