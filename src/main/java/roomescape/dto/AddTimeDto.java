package roomescape.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import roomescape.model.ReservationTime;

public record AddTimeDto(@JsonProperty(value = "startAt", defaultValue = "startAt") String start_at) {
    public static ReservationTime toEntity(Long id, final AddTimeDto dto) {
        return new ReservationTime(id, dto.start_at);
    }
}
