package roomescape.reservation.controller.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalTime;
import roomescape.reservation.domain.Time;

public record TimeRequest(@JsonFormat(pattern = "HH:mm", timezone = "Asia/Seoul") LocalTime startAt) {

    public TimeRequest {
        validateStartAt(startAt);
    }

    public Time toTimeWithoutId() {
        return new Time(null, startAt);
    }

    private void validateStartAt(LocalTime startAt) {
        if (startAt == null) {
            throw new IllegalArgumentException("시작 시각은 필수입니다.");
        }
    }

}
