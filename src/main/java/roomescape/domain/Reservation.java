package roomescape.domain;

import java.time.LocalDate;

import roomescape.utils.Parser;

public class Reservation {
    private final String name;
    private final LocalDate date;
    private final ReservationTime time;

    public Reservation(String name, String date, ReservationTime time) {
        validateName(name);
        this.name = name;
        this.date = Parser.parseDate(date);
        this.time = time;
    }

    private void validateName(String name) {
        if (name.length() > 255 || name.isBlank()) {
            throw new IllegalArgumentException("[ERROR] 이름은 255자 이하여야합니다.");
        }
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
