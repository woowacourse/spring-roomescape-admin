package roomescape.time.dto;

import roomescape.time.Time;

public record TimeResponseDto(
        Long id,
        String startAt
) {
    public static TimeResponseDto from(Time time) {
        return new TimeResponseDto(
                time.getId(),
                time.getStartAt()
        );
    }
}
