package roomescape.domain;

import java.time.LocalDate;

public record Reservation(Long id, String name, LocalDate date, ReservationTime reservationTime) {
    public Reservation {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("예약자 이름이 비어있습니다");
        }
    }
}
