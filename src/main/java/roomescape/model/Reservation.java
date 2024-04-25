package roomescape.model;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class Reservation {

    private final Long id;
    private final String name;
    private final LocalDate date;
    private final ReservationTime time;

    private Reservation(Long id, String name, LocalDate date, ReservationTime time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public static Reservation of(Long id, String name, String date, ReservationTime time) {
        validateEmpty(name, date, time);
        LocalDate localDate = parseDate(date);
        return new Reservation(id, name, localDate, time);
    }

    private static void validateEmpty(String name, String date, ReservationTime time) {
        if (name == null || name.isEmpty() || date == null || time == null) {
            throw new IllegalArgumentException("예약 정보에 빈 값이 포함될 수 없습니다.");
        }
    }

    private static LocalDate parseDate(String date) {
        try {
            return LocalDate.parse(date);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("유효하지 않은 날짜입니다.");
        }
    }

    public Reservation addId(long id) {
        if (id <= 0) {
            throw new IllegalArgumentException("유효하지 않은 id입니다.");
        }
        return new Reservation(id, name, date, time);
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
