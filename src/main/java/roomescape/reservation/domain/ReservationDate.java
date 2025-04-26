package roomescape.reservation.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public final class ReservationDate {

    private final LocalDate value;

    public static ReservationDate from(final LocalDate date) {
        validate(date);
        return new ReservationDate(date);
    }

    private static void validate(final LocalDate value) {
        validateNull(value);
    }

    private static void validateNull(final LocalDate value) {
        if (value == null) {
            throw new IllegalArgumentException("예약 시간은 null 일 수 없습니다");
        }
    }
}
