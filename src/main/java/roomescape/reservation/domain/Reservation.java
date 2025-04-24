package roomescape.reservation.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDate;
import java.util.Objects;
import roomescape.reservationtime.domain.ReservationTime;

public class Reservation {

    private final Long id;
    private final String name;
    private final LocalDate date;
    private final ReservationTime time;

    public Reservation(Long id, String name, LocalDate date, ReservationTime time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public static Reservation createWithoutId(String name, LocalDate date, ReservationTime time) {
        return new Reservation(null, name, date, time);
    }

    public Long getId() {
        return id;
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

    @JsonIgnore
    public boolean isIdNull() {
        return this.id == null;
    }

    @JsonIgnore
    public boolean isIdEqualTo(Long id) {
        return this.id.equals(id);
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        Reservation that = (Reservation) object;
        return Objects.equals(getId(), that.getId()) && Objects.equals(getName(), that.getName())
                && Objects.equals(getDate(), that.getDate()) && Objects.equals(getTime(),
                that.getTime());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getDate(), getTime());
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", date=" + date +
                ", time=" + time +
                '}';
    }
}
