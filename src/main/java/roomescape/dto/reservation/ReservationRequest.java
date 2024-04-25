package roomescape.dto.reservation;

import java.time.LocalDate;
import java.util.Objects;
import roomescape.domain.reservation.Reservation;
import roomescape.domain.time.Time;

public final class ReservationRequest {
    private final String name;
    private final LocalDate date;
    private final Long timeId;

    public ReservationRequest(String name, LocalDate date, Long timeId) {
        this.name = name;
        this.date = date;
        this.timeId = timeId;
    }

    public Reservation toReservation(Time time) {
        return new Reservation(this.name, this.date, time);
    }

    public String name() {
        return name;
    }

    public LocalDate date() {
        return date;
    }

    public Long timeId() {
        return timeId;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        var that = (ReservationRequest) obj;
        return Objects.equals(this.name, that.name) &&
                Objects.equals(this.date, that.date) &&
                Objects.equals(this.timeId, that.timeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, date, timeId);
    }

    @Override
    public String toString() {
        return "ReservationRequest[" +
                "name=" + name + ", " +
                "date=" + date + ", " +
                "timeId=" + timeId + ']';
    }
}
