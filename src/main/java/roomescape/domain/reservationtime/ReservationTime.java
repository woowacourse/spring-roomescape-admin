package roomescape.domain.reservationtime;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import lombok.Getter;

@Getter
public class ReservationTime {

    private final Long id;
    private final String startAt;

    private ReservationTime(Long id, String startAt) {
        validate(startAt);
        this.id = id;
        this.startAt = startAt;
    }

    private ReservationTime(String startAt) {
        validate(startAt);
        this.id = null;
        this.startAt = startAt;
    }

    public static ReservationTime createWithoutId(String startAt) {
        return new ReservationTime(startAt);
    }

    public static ReservationTime createWithId(Long id, ReservationTime reservationTime) {
        return new ReservationTime(id, reservationTime.getStartAt());
    }

    public static ReservationTime of(Long id, String startAt) {
        return new ReservationTime(id, startAt);
    }

    private static void validate(String startAt) {
        if (startAt == null || startAt.isBlank()) {
            throw new IllegalArgumentException("시간은 필수입니다.");
        }
        if (startAt.length() != 5 || startAt.charAt(2) != ':') {
            throw new IllegalArgumentException("시간은 HH:MM 형식이어야 합니다.");
        }
        try {
            LocalTime.parse(startAt, DateTimeFormatter.ofPattern("HH:mm"));
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("시간은 HH:MM 형식이어야 합니다.");
        }
    }
}
