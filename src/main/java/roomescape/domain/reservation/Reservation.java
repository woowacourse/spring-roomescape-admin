package roomescape.domain.reservation;

import java.time.LocalDate;
import roomescape.domain.reservationtime.ReservationTime;
import roomescape.service.dto.ReservationCreation;

public class Reservation {

    private static final String ERROR_SIGN = "[ERROR] ";

    private final Long id;
    private final String name;
    private final LocalDate date;
    private final ReservationTime time;

    public Reservation(Long id, String name, LocalDate date, ReservationTime time) {
        validate(name, date, time);
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public static Reservation from(ReservationCreation creation, ReservationTime time) {
        return new Reservation(null, creation.name(), creation.date(), time);
    }

    private void validate(String name, LocalDate date, ReservationTime time) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException(ERROR_SIGN + "이름이 비어 있을 수 없습니다.");
        }
        if (date == null) {
            throw new IllegalArgumentException(ERROR_SIGN + "날짜가 비어 있을 수 없습니다.");
        }
        if (time == null) {
            throw new IllegalArgumentException(ERROR_SIGN + "시간이 비어 있을 수 없습니다.");
        }
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
}
