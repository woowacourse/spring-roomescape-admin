package roomescape.service;

import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;
import roomescape.dao.ReservationDao;
import roomescape.domain.Reservation;

@Service
public class RoomEscapeService {

    private final ReservationDao reservationDao;

    public RoomEscapeService(final ReservationDao reservationDao) {
        this.reservationDao = reservationDao;
    }

    public List<Reservation> findAll() {
        return reservationDao.findAll().stream()
                .map(this::toReservation)
                .toList();
    }

    public Reservation create(final String name, final String date, final String time) {
        final long id = reservationDao.save(name, date, time);
        return Reservation.create(id, name, date, time);
    }

    public void delete(final long id) {
        reservationDao.delete(id);
    }

    private Reservation toReservation(final Map<String, Object> row) {
        return Reservation.create(
                ((Number) row.get("id")).longValue(),
                row.get("name").toString(),
                row.get("date").toString(),
                row.get("time").toString()
        );
    }
}
