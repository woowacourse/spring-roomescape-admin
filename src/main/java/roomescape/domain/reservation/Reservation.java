package roomescape.domain.reservation;

import java.time.LocalDate;
import lombok.Getter;
import roomescape.domain.reservationtime.ReservationTime;

@Getter
public class Reservation {

    private final Long id;
    private final String name;
    private final LocalDate date;
    private final ReservationTime time;

    private Reservation(Long id, String name, LocalDate date, ReservationTime time) {
        validate(name, date, time);
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public static Reservation createWithoutId(String name, LocalDate date, ReservationTime time) {
        return new Reservation(
            null,
            name,
            date,
            time
        );
    }

    public static Reservation of(
        long id,
        String name,
        LocalDate date,
        ReservationTime time
    ) {
        return new Reservation(id, name, date, time);
    }

    private static void validate(String name, LocalDate date, ReservationTime time) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("이름은 비어 있을 수 없습니다.");
        }
        if (date == null) {
            throw new IllegalArgumentException("날짜는 필수입니다.");
        }
        if (time == null) {
            throw new IllegalArgumentException("시간은 필수입니다.");
        }
    }
}
