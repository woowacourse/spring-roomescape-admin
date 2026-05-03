package roomescape.domain.reservationtime;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import lombok.Getter;
import roomescape.support.RoomescapeErrorCode;
import roomescape.support.RoomescapeException;

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
        try {
            LocalTime.parse(startAt, DateTimeFormatter.ofPattern("HH:mm"));
        } catch (DateTimeParseException e) {
            throw new RoomescapeException(RoomescapeErrorCode.INVALID_RESERVATION_TIME_FORMAT);
        }
    }
}
