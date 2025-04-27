package roomescape.dao;

import java.util.List;
import roomescape.domain.Time;

public interface TimeDAO {

    List<Time> findAllTime();

    Long insertTime(Time time);

    int deleteTimeById(Long id);
}
