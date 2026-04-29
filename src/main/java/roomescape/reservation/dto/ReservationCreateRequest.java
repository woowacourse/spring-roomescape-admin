package roomescape.reservation.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import java.time.LocalDate;
import roomescape.reservation.domain.Reservation;
import roomescape.reservationtime.domain.ReservationTime;

public record ReservationCreateRequest(
        @NotBlank(message = "[ERROR] 이름은 비어있을 수 없습니다.")
        String name,
        @NotBlank(message = "[ERROR] 날짜는 비어있을 수 없습니다.")
        @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "[ERROR] 날짜 형식은 yyyy-MM-dd 이어야 합니다.")
        String date,
        @NotNull(message = "[ERROR] 시간은 비어있을 수 없습니다.")
        Long timeId
) {
    public Reservation toEntity(ReservationTime time) {
        return Reservation.builder()
                .name(name)
                .date(LocalDate.parse(date))
                .time(time)
                .build();
    }
}
