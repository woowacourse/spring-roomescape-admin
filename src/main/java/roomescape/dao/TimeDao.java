package roomescape.dao;

import java.util.List;
import java.util.Optional;
import roomescape.domain.Time;

public interface TimeDao {
    List<Time> findAll();

    Optional<Time> findById(Long id);

    void save(Time time);

    void delete(Time time);
}
