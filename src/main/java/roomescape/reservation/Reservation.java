package roomescape.reservation;

import java.time.LocalDate;
import java.time.LocalTime;

public class Reservation {

    private final long id;
    private final String name;
    private final LocalDate date;
    private final LocalTime time;

    private Reservation(long id, String name, LocalDate date, LocalTime time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public long getId() {
        return id;
    }

    public static Reservation of(ReservationRequest reservationRequest, long index) {
        return new Reservation(index, reservationRequest.name(), reservationRequest.date(), reservationRequest.time());
    }

    public ReservationResponse toResponse() {
        return new ReservationResponse(
                this.id,
                this.name,
                this.date,
                this.time
        );
    }
}
