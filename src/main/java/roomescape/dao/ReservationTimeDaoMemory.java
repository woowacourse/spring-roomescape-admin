package roomescape.dao;

import roomescape.dto.ReservationTimeSaveDto;
import roomescape.entity.ReservationTime;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ReservationTimeDaoMemory implements ReservationTimeDao {

    private final Map<Long, ReservationTime> reservationTimes = new LinkedHashMap<>();
    private long key = 1;

    @Override
    public List<ReservationTime> findAll() {
        return reservationTimes.values().stream().toList();
    }

    @Override
    public ReservationTime findById(long id) {
        return reservationTimes.get(id);
    }

    @Override
    public long save(ReservationTimeSaveDto reservationTimeDto) {
        reservationTimes.put(key, new ReservationTime(
                key,
                reservationTimeDto.getStartAt()
        ));
        return key++;
    }

    @Override
    public void delete(long id) {
        reservationTimes.remove(id);
    }
}
