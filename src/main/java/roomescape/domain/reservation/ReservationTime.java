package roomescape.domain.reservation;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class ReservationTime {
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");
    private static final LocalTime START_TIME = LocalTime.of(9, 0);
    private static final LocalTime END_TIME = LocalTime.of(22, 0);

    private final LocalTime time;

    public ReservationTime(LocalTime time) {
        validateTime(time);
        this.time = time;
    }

    public ReservationTime(String time){
        this(LocalTime.parse(time, TIME_FORMATTER));
    }

    public static String formattedTime(LocalTime otherTime) {
        return otherTime.format(TIME_FORMATTER);
    }

    public static LocalTime startTime() {
        return START_TIME;
    }

    public static LocalTime endTime() {
        return END_TIME;
    }

    private void validateTime(LocalTime time) {
        if (time == null) {
            throw new IllegalArgumentException("time is null");
        }

        if (time.isBefore(START_TIME) || time.isAfter(END_TIME)) {
            throw new IllegalArgumentException("예약 가능 시간은 " + START_TIME + "부터 " + END_TIME + "까지입니다.");
        }
    }

    public LocalTime getTime() {
        return time;
    }
}
