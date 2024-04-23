package roomescape.dto.reservation;

import java.util.Objects;
import roomescape.domain.reservation.Date;
import roomescape.domain.reservation.Name;
import roomescape.domain.reservation.Reservation;
import roomescape.domain.reservationtime.ReservationTime;

public class ReservationCreateRequestDto {

    private final String name;
    private final String date;
    private final Long timeId;

    private ReservationCreateRequestDto(String name, String date, Long timeId) {
        this.name = name;
        this.date = date;
        this.timeId = timeId;
    }

    public static ReservationCreateRequestDto of(Reservation reservation, ReservationTime reservationTime) {
        return new ReservationCreateRequestDto(
                reservation.getName().getValue(),
                reservation.getDate().toStringDate(),
                reservationTime.getId()
        );
    }

    public static ReservationCreateRequestDto of(String name,
                                                 String date,
                                                 Long timeId) {
        return new ReservationCreateRequestDto(
                name,
                date,
                timeId
        );
    }

    public Reservation toDomain(ReservationTime reservationTime) {
        return new Reservation(
                null,
                new Name(name),
                Date.from(date),
                reservationTime);
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public Long getTimeId() {
        return timeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ReservationCreateRequestDto other = (ReservationCreateRequestDto) o;
        return Objects.equals(this.name, other.name)
                && Objects.equals(this.date, other.date)
                && Objects.equals(this.timeId, other.timeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, date, timeId);
    }

    @Override
    public String toString() {
        return "ReservationCreateRequestDto{" +
                "name='" + name + '\'' +
                ", date='" + date + '\'' +
                ", timeId=" + timeId +
                '}';
    }
}
