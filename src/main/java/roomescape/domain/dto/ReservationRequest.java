package roomescape.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;
import roomescape.domain.entity.Reservation;
import roomescape.domain.entity.ReservationTime;

import java.time.LocalDate;

public record ReservationRequest(
        @NotBlank(message = "이름은 필수 값입니다.")
        String name,

        @NotNull(message = "날짜는 필수 값입니다.")
        @DateTimeFormat(pattern = "yyyy-mm-dd")
        LocalDate date,

        @NotNull(message = "time id는 필수 값입니다.")
        Long timeId
) {
    public Reservation toEntity(ReservationTime time) {
        return Reservation.create(null, name, date, time);
    }
}
