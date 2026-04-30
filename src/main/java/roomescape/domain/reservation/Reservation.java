package roomescape.domain.reservation;

import java.time.LocalDate;
import roomescape.domain.time.ReservationTime;
import roomescape.domain.user.User;

public class Reservation {
    private final Long id;
    private final User user;
    private final LocalDate date;
    private final ReservationTime time;

    public Reservation(Long id, User user, LocalDate date, ReservationTime time) {
        this.id = id;
        this.user = user;
        this.date = date;
        this.time = time;
    }

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public LocalDate getDate() {
        return date;
    }

    public ReservationTime getTime() {
        return time;
    }
}