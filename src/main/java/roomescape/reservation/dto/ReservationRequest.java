package roomescape.reservation.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import roomescape.reservation.domain.Reservation;
import roomescape.reservationtime.domain.ReservationTime;

public record ReservationRequest (
        @NotBlank(message = "이름은 필수입니다.")
        String name,

        @NotNull(message = "날짜는 필수입니다.")
        LocalDate date,

        @NotNull(message = "시간 ID는 필수입니다.")
        Long timeId
) {
    public Reservation toEntity(ReservationTime time) {
        return Reservation.builder()
                .name(name)
                .date(date)
                .time(time)
                .build();
    }
}
