package roomescape.domain.time;

import java.util.List;

public interface TimeRepository {

    Time findById(Long timeId);

    List<Time> findAll();

    Time create(Time time);

    void delete(Long id);
}
