package roomescape.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import roomescape.domain_entity.Id;
import roomescape.domain_entity.Reservation;
import roomescape.domain_entity.ReservationTime;

public record ReservationRequestDto(String name, LocalDate date, long timeId) {
    public ReservationRequestDto {
        validateNotNull(name, date, timeId);
    }

    private void validateNotNull(String name, LocalDate date, long timeId) {
        if (name == null || date == null || timeId == 0) {
            throw new IllegalArgumentException("요청 필드가 올바르지 않습니다.");
        }
    }

    public Reservation toReservation() {
        return new Reservation(
                name, date, new ReservationTime(new Id(timeId))
        );
    }
}
