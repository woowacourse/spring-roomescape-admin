package roomescape.dto;

import roomescape.times.Times;

public record ResponseTimes(Long id, String startAt) {

    public ResponseTimes(Times times) {
        this(times.getId(), times.getStartAt());
    }
}
