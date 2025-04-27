package roomescape.model;

import static roomescape.util.FormatValidator.validateDateFormat;

public class ReservationDate {
    private final String date;

    public ReservationDate(String date) {
        validateDateFormat(date);
        this.date = date;
    }

    public String getDate() {
        return date;
    }

}
