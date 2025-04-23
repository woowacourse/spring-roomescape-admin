package roomescape.time;

import java.util.List;

public interface TimeDao {
    Long saveTime(Time time);
    List<Time> findAllTime();
    Time findTimeById(Long id);
    void deleteTimeById(Long id);
}
