package roomescape.domain;

import java.time.LocalDate;

public class Reservation {

    public static final String RESERVATION_NAME_IS_NOT_EMPTY = "예약자 이름은 비어 있을 수 없습니다.";
    public static final String RESERVATION_DATE_IS_NOT_EMPTY = "예약 날짜는 비어 있을 수 없습니다.";
    public static final String RESERVATION_TIME_IS_NOT_EMPTY = "예약 시간은 비어 있을 수 없습니다.";

    private Long id;
    private String name;
    private LocalDate date;
    private ReservationTime time;

    public Reservation() {
    }

    public Reservation(String name, LocalDate date, ReservationTime time) {
        this(null, name, date, time);
    }

    public Reservation(Long id, String name, LocalDate date, ReservationTime time) {
        validateName(name);
        validateDate(date);
        validateTime(time);

        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    private void validateName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException(RESERVATION_NAME_IS_NOT_EMPTY);
        }
    }

    private void validateDate(LocalDate date) {
        if (date == null) {
            throw new IllegalArgumentException(RESERVATION_DATE_IS_NOT_EMPTY);
        }
    }

    private void validateTime(ReservationTime time) {
        if (time == null) {
            throw new IllegalArgumentException(RESERVATION_TIME_IS_NOT_EMPTY);
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
