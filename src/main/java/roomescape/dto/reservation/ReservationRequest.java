package roomescape.dto.reservation;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;

public record ReservationRequest(
        @NotBlank(message = "이름은 공백일 수 없습니다.")
        String name,
        @NotNull(message = "날짜가 입력되어야 합니다.")
        LocalDate date,
        @NotNull(message = "시간 아이디가 입력되어야 합니다.")
        Long timeId
) {

    public Reservation toReservation() {
        ReservationTime reservationTime = new ReservationTime(timeId, null);

        return new Reservation(null, name, date.toString(), reservationTime);
    }
}
