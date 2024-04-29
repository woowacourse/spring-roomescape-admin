package roomescape.dao;

import roomescape.dto.ReservationSaveDto;
import roomescape.entity.Reservation;
import roomescape.entity.ReservationTime;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ReservationDaoMemory implements ReservationDao {

    private final Map<Long, Reservation> reservations = new LinkedHashMap<>();
    private long key = 1;

    @Override
    public List<Reservation> findAll() {
        return reservations.values().stream().toList();
    }

    @Override
    public Reservation findById(long id) {
        return reservations.get(id);
    }

    @Override
    public long save(ReservationSaveDto reservationSaveDto) {
        Reservation value = new Reservation(
                key,
                reservationSaveDto.getName(),
                reservationSaveDto.getDate(),
                new ReservationTime(
                        reservationSaveDto.getTimeId(),
                        reservationSaveDto.getStartAt()
                )
        );
        reservations.put(key, value);
        return key++;
    }

    @Override
    public void delete(long id) {
        reservations.remove(id);
    }
}
