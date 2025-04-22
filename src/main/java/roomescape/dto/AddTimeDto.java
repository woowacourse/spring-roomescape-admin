package roomescape.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import roomescape.model.Time;

public record AddTimeDto(@JsonProperty(value = "startAt", defaultValue = "startAt") String start_at) {
    public static Time toEntity(Long id, final AddTimeDto dto) {
        return new Time(id, dto.start_at);
    }
}
