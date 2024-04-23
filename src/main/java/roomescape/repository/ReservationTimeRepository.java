package roomescape.repository;

import java.util.List;
import roomescape.data.vo.ReservationTime;

public interface ReservationTimeRepository {

    long add(final ReservationTime time);

    List<ReservationTime> getAll();
}
