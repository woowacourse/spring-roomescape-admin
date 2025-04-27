package roomescape.model;

import java.time.LocalDate;

public record Reservation(
    Long id,
    String name,
    LocalDate date,
    ReservationTime reservationTime
) implements Entity<Reservation> {

    @Override
    public Reservation withId(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("[ERROR] 전달받은 id는 null일 수 없습니다.");
        }
        return new Reservation(id, name, date, reservationTime);
    }

    public Reservation withReservationTime(ReservationTime reservationTime) {
        return new Reservation(id, name, date, reservationTime);
    }
}
