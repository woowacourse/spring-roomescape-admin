package roomescape.fixture;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;

public class TextFixture {

    private static final String DATE_FORMAT = "%d-%02d-%02d";
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    public static String makeNowTime() {
        return LocalTime.now().format(TIME_FORMATTER);
    }

    public static String makeTodayMessage() {
        LocalDate today = LocalDate.now();
        return String.format(DATE_FORMAT, today.getYear(), today.getMonthValue(), today.getDayOfMonth());
    }

    public static String makeYesterdayMessage() {
        LocalDate yesterday = LocalDate.now().minusDays(1);
        return String.format(DATE_FORMAT, yesterday.getYear(), yesterday.getMonthValue(), yesterday.getDayOfMonth());
    }

    public static Reservation makeReservation(final long reservationId, final long reservationTimeId) {
        ReservationTime reservationTime = makeReservationTime(reservationTimeId);
        return new Reservation(reservationId, "밍트", LocalDate.now(), reservationTime);
    }

    public static ReservationTime makeReservationTime(final long reservationTimeId) {
        return new ReservationTime(reservationTimeId, LocalTime.now());
    }
}
