package roomescape.domain.reservation;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import roomescape.domain.user.UserName;

public class Reservation {
    private final Long id;
    private final UserName name;
    private final LocalDateTime reservationDateTime;

    public Reservation(String name, LocalDateTime dateTime) {
        this(null, name, dateTime);
    }

    private Reservation(Long id, String name, LocalDateTime dateTime) {
        this(id, new UserName(name), dateTime);
    }

    private Reservation(Long id, UserName name, LocalDateTime dateTime) {
        validateDateTime(dateTime);
        this.id = id;
        this.name = name;
        this.reservationDateTime = dateTime;
    }

    private void validateDateTime(LocalDateTime dateTime) {
        if (LocalDateTime.now().isAfter(dateTime)) {
            throw new IllegalArgumentException("현재 시간 이후로 예약해야 합니다.");
        }
    }

    public Reservation updateId(Long id) {
        return new Reservation(id, name, reservationDateTime);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name.getValue();
    }

    public LocalDate getReservationDate() {
        return reservationDateTime.toLocalDate();
    }

    public LocalTime getReservationTime() {
        return reservationDateTime.toLocalTime();
    }
}
