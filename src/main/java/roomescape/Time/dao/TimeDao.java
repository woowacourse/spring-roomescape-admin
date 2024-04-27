package roomescape.Time.dao;

import java.util.List;
import roomescape.Time.domain.Time;

public interface TimeDao {

    void save(Time reservationTime);

    List<Time> findAll();

    Time findById(long reservationTimeId);

    void deleteById(long reservationTimeId);
}
