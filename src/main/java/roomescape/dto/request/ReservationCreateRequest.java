package roomescape.dto.request;

import roomescape.Reservation;

import java.time.LocalDate;
import java.time.LocalTime;

public record ReservationCreateRequest(
        String name,
        LocalDate date,
        LocalTime time
) {
    public ReservationCreateRequest {
        if (name == null) {
            throw new IllegalArgumentException("이름은 필수값입니다.");
        }
        if (name.isBlank()) {
            throw new IllegalArgumentException("이름은 비어 있을 수 없습니다.");
        }
        if (date == null) {
            throw new IllegalArgumentException("날짜는 필수값입니다.");
        }
        if (time == null) {
            throw new IllegalArgumentException("시간은 필수값입니다.");
        }
    }

    public Reservation toDomain() {
        return new Reservation(name, date, time);
    }
}
