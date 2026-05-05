package roomescape.dto;

import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonFormat;

public record TimeCreateResponse(
        Long id,
        @JsonFormat(pattern = "HH:mm")
        LocalTime startAt) {
}
