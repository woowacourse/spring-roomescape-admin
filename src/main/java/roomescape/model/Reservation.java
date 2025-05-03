package roomescape.model;

import java.time.LocalDate;

public class Reservation {

    private final Long id;
    private final String name;
    private final LocalDate date;
    private final ReservationTime time;

    public Reservation(Long id, String name, LocalDate date, ReservationTime time) {
        validateName(name);
        validateDate(date);
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
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

    private void validateName(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("[ERROR] 이름을 입력해주세요.");
        }
    }

    private void validateDate(LocalDate date) {
        if (date == null) {
            throw new IllegalArgumentException("[ERROR] 날짜를 입력해주세요.");
        }
    }
}
