package roomescape.console.request;

import roomescape.core.domain.ReservationTime;

import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

public record ReservationTimeRequest(LocalTime startAt) {
    public ReservationTimeRequest {
        Objects.requireNonNull(startAt);
    }

    public static ReservationTimeRequest from(List<String> contents) {
        try {
            LocalTime localTime = getLocalTime(contents.get(0));
            return new ReservationTimeRequest(localTime);
        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            throw new IllegalArgumentException("'시간:분' 형식으로 입력해 주세요! 입력한 값 - '" + contents + "'");
        }
    }

    private static LocalTime getLocalTime(String localTime) {
        String[] split = localTime.split(":");
        return LocalTime.of(Integer.parseInt(split[0]), Integer.parseInt(split[1]));
    }

    public ReservationTime toEntity() {
        return new ReservationTime(startAt);
    }
}
