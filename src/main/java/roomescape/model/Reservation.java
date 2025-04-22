package roomescape.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import roomescape.exception.ReservationException;

public final class Reservation {

    private static final int MAX_NAME_LENGTH = 20;

    private final Long id;
    private final String name;
    private final ReservationDate date;
    private final ReservationTime time;

    public Reservation(Long id, String name, LocalDateTime startDateTime) {
        validateNameLength(name);
        validateNotPastDateTime(startDateTime);
        this.id = id;
        this.name = name;
        this.date = new ReservationDate(startDateTime.toLocalDate());
        this.time = new ReservationTime(startDateTime.toLocalTime());
    }

    public Reservation(Long id, String name, LocalDate date, LocalTime time) {
        this(id, name, LocalDateTime.of(date, time));
    }

    public Reservation(String name, LocalDate date, LocalTime time) {
        this(null, name, LocalDateTime.of(date, time));
    }

    public Reservation(Long id, String name, ReservationDate reservationDate, ReservationTime reservationTime) {
        validateNameLength(name);
        validateNotPastDateTime(LocalDateTime.of(reservationDate.getStartDate(), reservationTime.getStartTime()));
        this.id = id;
        this.name = name;
        this.date = reservationDate;
        this.time = reservationTime;
    }

    public Reservation(String name, ReservationDate reservationDate, ReservationTime reservationTime) {
        validateNameLength(name);
        validateNotPastDateTime(LocalDateTime.of(reservationDate.getStartDate(), reservationTime.getStartTime()));
        this.id = null;
        this.name = name;
        this.date = reservationDate;
        this.time = reservationTime;
    }

    private void validateNotPastDateTime(LocalDateTime startDateTime) {
        if (startDateTime.isBefore(LocalDateTime.now())) {
            throw new ReservationException("과거 일시로 예약을 생성할 수 없습니다.");
        }
    }

    private void validateNameLength(String name) {
        if (name.isEmpty() || MAX_NAME_LENGTH < name.length()) {
            throw new ReservationException("예약자명은 1자 이상 %d자 이하로만 가능합니다.".formatted(MAX_NAME_LENGTH));
        }
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public ReservationDate getDate() {
        return date;
    }

    public ReservationTime getTime() {
        return time;
    }
}
