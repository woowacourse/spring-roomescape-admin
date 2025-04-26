package roomescape.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import roomescape.entity.ReservationWithTimeId;

public record ReservationRequest(
        @NotBlank(message = "이름은 필수입니다.")
        String name,

        @NotNull(message = "예약 날짜는 필수입니다.")
        @JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
        LocalDate date,

        @NotNull(message = "예약 시간 ID는 필수입니다.")
        Long timeId) {

    public ReservationWithTimeId toReservationWithId() {
        return new ReservationWithTimeId(null, name, date, timeId);
    }
}
