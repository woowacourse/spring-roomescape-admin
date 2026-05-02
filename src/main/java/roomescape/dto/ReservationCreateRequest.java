package roomescape.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import roomescape.entity.Reservation;
import roomescape.entity.ReservationTime;

import java.time.LocalDate;

public record ReservationCreateRequest(

        @NotBlank(message = "이름은 필수입니다.")
        String name,

        @NotNull(message = "날짜는 필수입니다.")
        LocalDate date,

        @NotNull(message = "시간 ID는 필수입니다.")
        Long timeId
) {
    public Reservation toEntity(ReservationTime reservationTime) {
        return new Reservation(null, name, date, reservationTime);
    }
}
