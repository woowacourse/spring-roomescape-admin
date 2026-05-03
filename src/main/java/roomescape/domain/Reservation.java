package roomescape.domain;

import java.time.LocalDate;

public class Reservation {

    private Long id;
    private String name;
    private LocalDate date;
    private ReservationTime time;

    public Reservation(Long id, String name, LocalDate date, ReservationTime time) {
        validateFields(name, date, time);
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public Reservation(String name, LocalDate date, ReservationTime time) {
        this(null, name, date, time);
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

    private void validateFields(String name, LocalDate date, ReservationTime time) {
        validateName(name);
        validateDate(date);
        validateReservationTime(time);
    }

    private void validateName(String name) {
        if (name == null) {
            throw new IllegalArgumentException("name은 Null일 수 없습니다.");
        }

        if (name.isBlank()) {
            throw new IllegalArgumentException("name은 비어있을 수 없습니다.");
        }
    }

    private void validateDate(LocalDate date) {
        if (date == null) {
            throw new IllegalArgumentException("date는 Null일 수 없습니다.");
        }
    }

    private void validateReservationTime(ReservationTime time) {
        if (time == null) {
            throw new IllegalArgumentException("reservationTime은 Null일 수 없습니다.");
        }
    }

}
