package roomescape.reservation;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import java.time.LocalTime;

public class Reservation {

    private final long id;
    private final String name;
    private final LocalDate date;

    @JsonFormat(pattern = "HH:mm")
    private final LocalTime time;

    public Reservation(long id, String name, LocalDate date, LocalTime time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTime() {
        return time;
    }

    public static Reservation from(ReservationRequest reservationRequest, long index) {
        return new Reservation(index, reservationRequest.name(), reservationRequest.date(), reservationRequest.time());
    }
}
