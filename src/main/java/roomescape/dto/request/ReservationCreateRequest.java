package roomescape.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import roomescape.domain.Reservation;

/*
1. 기본생성자 + setter / 리플렉션
2. 생성자 한개 여야 해요
    - 생성자를 한개로 줄이기
    - 공부해라
 */
public record ReservationCreateRequest(
        @NotBlank String name,
        @NotNull LocalDate date,
        @NotNull LocalTime time
) {
    public Reservation toReservation() {
        LocalDateTime localDateTime = LocalDateTime.of(date, time);
        return new Reservation(name, localDateTime);
    }
}
