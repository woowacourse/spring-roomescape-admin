package roomescape.admin.reservation.time;

import java.util.List;

public interface TimeRepository {

    List<Time> findAll();

    Time save(Time time);

    int delete(Long id);
}
