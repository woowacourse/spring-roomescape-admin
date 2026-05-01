package roomescape.service;

import roomescape.domain.Time;

import java.util.List;

public interface TimeService {

    List<Time> findAllTimes();

    Time add(Time time);

    Time findById(Long id);

    void remove(Long id);
}
