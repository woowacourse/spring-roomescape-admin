package roomescape.console.controller.request;

import roomescape.core.domain.Reservation;
import roomescape.core.domain.ReservationTime;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public record ReservationRequest(
        String name,
        LocalDate date,
        Long timeId
) {
    public ReservationRequest {
        Objects.requireNonNull(name);
        Objects.requireNonNull(date);
        Objects.requireNonNull(timeId);
    }

    public static ReservationRequest from(final List<String> contents) {
        try {
            String name = contents.get(0);
            LocalDate date = getLocalDate(contents.get(1));
            long timeId = Long.parseLong(contents.get(2));
            return new ReservationRequest(name, date, timeId);
        } catch (NumberFormatException | DateTimeException e) {
            throw new IllegalArgumentException("날짜는 '년-월-일' 형식으로 입력해 주세요. 입력한 값 - '" +
                    String.join(" ", contents) + "'");
        } catch (IndexOutOfBoundsException e) {
            throw new IllegalArgumentException("'이름 날짜 시간ID' 형식으로 모두 입력해 주세요.");
        }
    }

    private static LocalDate getLocalDate(final String localDate) {
        String[] split = localDate.split("-");
        return LocalDate.of(Integer.parseInt(split[0]), Integer.parseInt(split[1]), Integer.parseInt(split[2]));
    }

    public Reservation toEntity(ReservationTime reservationTime) {
        return new Reservation(name, date, reservationTime);
    }
}
