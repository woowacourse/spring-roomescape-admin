package roomescape.repository.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalTime;

public record SaveTimeSlotDto(
    @JsonProperty("startAt") LocalTime startAt
) {

    public SaveTimeSlotDto {
        if (startAt == null) {
            throw new IllegalArgumentException("모든 값이 존재해야 합니다.");
        }
    }
}
