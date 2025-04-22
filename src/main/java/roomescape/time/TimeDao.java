package roomescape.time;

import java.util.List;

public interface TimeDao {
    Time saveTime(Time time);
    List<Time> findAllTime();
}
