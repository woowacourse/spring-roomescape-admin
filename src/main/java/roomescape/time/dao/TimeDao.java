package roomescape.time.dao;

import java.util.List;
import roomescape.time.domain.Time;

public interface TimeDao {

    Time insert(Time time);

    List<Time> findAll();

    void delete(long id);
}
