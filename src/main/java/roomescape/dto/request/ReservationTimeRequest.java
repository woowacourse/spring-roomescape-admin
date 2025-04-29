package roomescape.dto.request;

import java.time.LocalTime;
import java.util.regex.Pattern;

public class ReservationTimeRequest {

    private static final Pattern TIME_PATTERN = Pattern.compile("^(0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$");

    private final LocalTime startAt;

    public ReservationTimeRequest(LocalTime startAt) {
        this.startAt = startAt;
    }

    public static ReservationTimeRequest of(String startAt) {
        validateTime(startAt);
        return new ReservationTimeRequest(LocalTime.parse(startAt));
    }

    private static void validateTime(String time) {
        if (!TIME_PATTERN.matcher(time).matches()) {
            throw new IllegalArgumentException("시간은 00:00 형식입니다");
        }
    }

    public LocalTime getStartAt() {
        return startAt;
    }
}
