package roomescape.domain;

import java.time.LocalDate;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Reservation {

    private static final long PENDING_RESERVATION_ID = -1L;

    private final long id;
    private final String name;
    private final LocalDate reservationDate;
    private final ReservationTime reservationTime;

    public static Reservation pending(String username, LocalDate date) {
        return new Reservation(PENDING_RESERVATION_ID, username, date, ReservationTime.none());
    }

    public static Reservation create(long id, String username, LocalDate date, ReservationTime time) {
        return new Reservation(id, username, date, time);
    }

    public String username() {
        return name;
    }

    public LocalDate date() {
        return reservationDate;
    }

    public ReservationTime time() {
        return reservationTime;
    }

    public long id() {
        return id;
    }
}
