package roomescape.service;

import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;
import roomescape.dao.ReservationDao;
import roomescape.dao.ReservationTimeDao;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;

@Service
public class ReservationService {

    private final ReservationDao reservationDao;
    private final ReservationTimeDao reservationTimeDao;

    public ReservationService(ReservationDao reservationDao, ReservationTimeDao reservationTimeDao) {
        this.reservationDao = reservationDao;
        this.reservationTimeDao = reservationTimeDao;
    }

    public List<Reservation> findAll() {
        return reservationDao.findAll().stream()
                .map(this::toReservation)
                .toList();
    }

    public Reservation create(String name, String date, long timeId) {
        final long reservationId = reservationDao.save(name, date, timeId);
        final ReservationTime reservationTime = toReservationTime(reservationTimeDao.findById(timeId));
        return Reservation.create(reservationId, name, date, reservationTime);
    }

    public void delete(long id) {
        reservationDao.delete(id);
    }

    private Reservation toReservation(Map<String, Object> row) {
        return Reservation.create(
                ((Number) row.get("reservation_id")).longValue(),
                row.get("name").toString(),
                row.get("date").toString(),
                ReservationTime.create(
                        ((Number) row.get("time_id")).longValue(),
                        row.get("start_at").toString()
                )
        );
    }

    private ReservationTime toReservationTime(Map<String, Object> row) {
        return ReservationTime.create(
                ((Number) row.get("id")).longValue(),
                row.get("start_at").toString()
        );
    }
}
