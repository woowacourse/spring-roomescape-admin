package roomescape.entity;

import roomescape.domain.Time;

public record TimeEntity(Long id, String startAt) {

    public static TimeEntity from(final Time time) {
        return new TimeEntity(
                time.getId(),
                time.getStartAt().toString()
        );
    }
}
