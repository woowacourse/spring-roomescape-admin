package roomescape.reservation.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Reservation {

    private final Long id;
    private final String name;
    private final ReservationDateTime datetime;

    private Reservation(final Long id, final String name, final ReservationDateTime datetime) {
        this.id = id;
        this.name = name;
        this.datetime = datetime;
    }

    public static Reservation createWithId(Long id, String name, LocalDateTime dateTime) {
        return new Reservation(id, name, ReservationDateTime.from(dateTime));
    }

    public static Reservation createWithoutId(String name, LocalDateTime dateTime) {
        return new Reservation(null, name, ReservationDateTime.from(dateTime));
    }

    public boolean isSaved() {
        return id == null;
    }

    public Long getId() {
        if (isSaved()) {
            throw new IllegalStateException("저장되지 않은 예약은 예약 번호가 존재하지 않습니다");
        }
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDate getDate() {
        return datetime.getDate();
    }

    public LocalTime getTime() {
        return datetime.getTime();
    }
}
