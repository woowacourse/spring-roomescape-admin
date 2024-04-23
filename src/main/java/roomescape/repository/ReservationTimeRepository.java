package roomescape.repository;

import java.util.List;
import roomescape.data.vo.ReservationTime;

public interface ReservationTimeRepository {
    long add(final ReservationTime time);
    List<ReservationTime> getAll();
    ReservationTime get(final long id);
    void remove(final long id);
}
