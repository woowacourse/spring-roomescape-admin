package roomescape.dto;

import java.time.LocalTime;

public record TimeResponseDto(
    Long id,
    LocalTime time
) {}
