package roomescape.domain;

import roomescape.exception.Validator;

public class Reservation {
    private String name;
    private String date;
    private ReservationTime time;

    public Reservation(String name, String date, ReservationTime time) {
        validateName(name);
        Validator.validateDate(date);
        this.name = name;
        this.date = date;
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

    public String getDate() {
        return date;
    }

    public ReservationTime getTime() {
        return time;
    }
}
