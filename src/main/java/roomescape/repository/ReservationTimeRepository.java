package roomescape.repository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;
import roomescape.dao.ReservationTimeDao;
import roomescape.domain.ReservationTime;
import roomescape.domain.ReservationTimeCommand;

@Repository
public class ReservationTimeRepository {
    private final ReservationTimeDao reservationTimeDao;

    public ReservationTimeRepository(ReservationTimeDao reservationTimeDao) {
        this.reservationTimeDao = reservationTimeDao;
    }

    public ReservationTime addReservationTime(ReservationTimeCommand reservationTimeCommand) {
        return new ReservationTime(reservationTimeDao.insertReservationTime(reservationTimeCommand), reservationTimeCommand.startAt());
    }

    public Optional<ReservationTime> getReservationTime(long id) {
        return reservationTimeDao.getReservationTime(id);
    }

    public List<ReservationTime> getAllReservationTime() {
        return Collections.unmodifiableList(reservationTimeDao.getAllReservationTime());
    }

    public int deleteReservationTime(long id) {
        return reservationTimeDao.deleteReservation(id);
    }
}
