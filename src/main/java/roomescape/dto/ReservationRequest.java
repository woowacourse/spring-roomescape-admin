package roomescape.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.springframework.cglib.core.Local;
import roomescape.model.Reservation;

public class ReservationRequest {

    private final String name;
    private final String date;
    private final String time;

    public ReservationRequest(String name, String date, String time) {
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public Reservation toReservation(Long id) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-ddHH:mm");
        LocalDateTime datetime = LocalDateTime.parse(this.date + this.time, formatter);
        return new Reservation(id, this.name, datetime);
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }
}
