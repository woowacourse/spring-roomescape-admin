package roomescape.data.dao;

import java.util.List;
import java.util.Optional;
import roomescape.business.domain.Time;

public interface TimeDao {

    Long save(Time time);

    Optional<Time> find(Long id);

    List<Time> findAll();

    int remove(Long id);
}
