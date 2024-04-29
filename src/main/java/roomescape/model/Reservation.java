package roomescape.model;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class Reservation {

    private static final Long DEFAULT_ID = 0L;

    private final Long id;
    private final String name;
    private final LocalDate date;
    private final ReservationTime time;

    public Reservation(String name, String date, ReservationTime time) {
        this(DEFAULT_ID, name, date, time);
    }

    public Reservation(Long id, String name, String date, ReservationTime time) {
        this(id, name, parseDate(date), time);
    }

    public Reservation(Long id, String name, LocalDate date, ReservationTime time) {
        validateEmpty(name, date, time);
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    private static void validateEmpty(String name, LocalDate date, ReservationTime time) {
        if (name == null || name.isEmpty() || date == null || time == null) {
            throw new IllegalArgumentException("예약 정보에 빈 값이 포함될 수 없습니다.");
        }
    }

    private static LocalDate parseDate(String date) {
        try {
            return LocalDate.parse(date);
        } catch (DateTimeParseException | NullPointerException e) {
            throw new IllegalArgumentException("유효하지 않은 날짜입니다.");
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
