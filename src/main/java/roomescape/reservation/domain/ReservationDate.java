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
        return new ReservationDate(date);
    }
}
