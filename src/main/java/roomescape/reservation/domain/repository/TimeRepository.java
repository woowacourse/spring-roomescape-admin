package roomescape.reservation.domain.repository;

import java.util.List;
import roomescape.reservation.domain.Time;

public interface TimeRepository {

    Long saveAndReturnId(Time time);

    List<Time> findAll();

    int deleteById(Long id);

    Time findById(Long id);

}
