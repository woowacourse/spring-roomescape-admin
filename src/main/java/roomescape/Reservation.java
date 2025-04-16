package roomescape;

import java.time.LocalDate;
import java.time.LocalTime;

public class Reservation {

    private Long id;
    private String name;
    private LocalDate date;
    private LocalTime time;

    public Reservation(Long id, String name, LocalDate date, LocalTime time) {
        validateName(name);
        validateDate(date);
        validateTime(time);
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public static Reservation toEntity(Reservation reservation, Long id) {
        return new Reservation(id, reservation.name, reservation.date, reservation.time);
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

    public LocalTime getTime() {
        return time;
    }

    private void validateName(String value) {
        if (value.isBlank()) {
            throw new IllegalArgumentException("이름을 입력해주세요.");
        }
    }

    private void validateDate(LocalDate date) {
        if (date == null) {
            throw new IllegalArgumentException("날짜를 입력해주세요.");
        }
    }

    private void validateTime(LocalTime time) {
        if (time == null) {
            throw new IllegalArgumentException("시간을 입력해주세요.");
        }
    }
}
