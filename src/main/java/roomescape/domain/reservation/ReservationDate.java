package roomescape.domain.reservation;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ReservationDate {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private final LocalDate date;

    public ReservationDate(LocalDate date) {
        validateDate(date);
        this.date = date;
    }

    public ReservationDate(String date){
        this(LocalDate.parse(date, DATE_FORMATTER));
    }

    public static String formattedDate(LocalDate otherDate){
        return otherDate.format(DATE_FORMATTER);
    }

    private void validateDate(LocalDate date) {
        if(date ==null){
            throw new IllegalArgumentException("date is null");
        }

        if (LocalDate.now().isAfter(date)) {
            throw new IllegalArgumentException("예약은 현재 이후 날짜만 가능합니다.");
        }
    }

    public LocalDate getDate() {
        return date;
    }
}
