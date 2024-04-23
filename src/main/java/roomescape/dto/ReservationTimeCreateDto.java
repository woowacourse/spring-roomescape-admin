package roomescape.dto;

import jakarta.validation.constraints.Pattern;
import roomescape.domain.ReservationTime;

public class ReservationTimeCreateDto {
    @Pattern(regexp = "^([0-1][0-9]|2[0-3]):([0-5][0-9])$", message = "시간은 HH:mm 형태로 입력해야 합니다.")
    private String startAt;

    public ReservationTimeCreateDto() {
    }

    public ReservationTimeCreateDto(String startAt) {
        this.startAt = startAt;
    }

    public ReservationTime toDomain() {
        return new ReservationTime(null, startAt);
    }

    public String getStartAt() {
        return startAt;
    }
}
