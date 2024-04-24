package roomescape.dao;

import java.util.List;
import roomescape.domain.Time;

public interface TimeDao {
    List<Time> findAll();

    void save(Time time);
}
