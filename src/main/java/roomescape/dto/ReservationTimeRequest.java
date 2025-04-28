package roomescape.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import java.time.LocalTime;
import roomescape.model.ReservationTime;

public record ReservationTimeRequest(@NotNull(message = "예약 시간 입력은 필수입니다.") @JsonFormat(pattern = "HH:mm", timezone = "Asia/Seoul")LocalTime startAt) {



    public ReservationTime toEntity() {

        return new ReservationTime(null, startAt);
    }
}
