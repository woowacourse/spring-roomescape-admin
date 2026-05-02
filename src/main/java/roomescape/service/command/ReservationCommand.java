package roomescape.service.command;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public record ReservationCommand(
        @NotNull(message = "예약자 이름 정보는 필수 값 입니다.")
        @NotBlank(message = "예약자 이름 정보는 비어있을 수 없습니다.")
        String name,
        @NotNull(message = "예약 날짜 정보는 필수 값입니다.")
        @FutureOrPresent(message = "이미 지난 날짜는 예약할 수 없습니다.")
        LocalDate date,
        @NotNull(message = "예약 시간 정보는 필수 값입니다.")
        Long timeId
) {
}
