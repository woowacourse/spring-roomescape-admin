package roomescape.dto.reservation;

import java.time.LocalDate;
import java.util.Objects;
import roomescape.domain.reservation.Reservation;
import roomescape.dto.time.ReservationTimeResponse;

public class ReservationResponse {

    private final long id;
    private final String name;
    private final LocalDate date;
    private final ReservationTimeResponse time;

    private ReservationResponse(long id, String name, LocalDate date, ReservationTimeResponse time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public static ReservationResponse from(Reservation reservation) {
        return new ReservationResponse(
            reservation.getId(),
            reservation.getName(),
            reservation.getDate(),
            ReservationTimeResponse.from(reservation.getTime())
        );
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

    public ReservationTimeResponse getTime() {
        return time;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof ReservationResponse that)) {
            return false;
        }
        return id == that.id && Objects.equals(name, that.name) && Objects.equals(date, that.date)
            && Objects.equals(time, that.time);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, date, time);
    }

    @Override
    public String toString() {
        return "ReservationResponse{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", date=" + date +
            ", time=" + time +
            '}';
    }
}
