package roomescape.time.controller;

import java.util.List;
import roomescape.time.domain.Time;

public interface TimeRepository {

    Time save(Time time);

    List<Time> findAll();

    void deleteById(Long id);
}
