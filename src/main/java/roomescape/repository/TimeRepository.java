package roomescape.repository;

import roomescape.domain.time.Time;

public interface TimeRepository {
    Time save(Time timeRequest);
}
