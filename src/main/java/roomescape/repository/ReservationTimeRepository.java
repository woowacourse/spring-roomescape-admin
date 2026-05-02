package roomescape.repository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;
import roomescape.dao.ReservationTimeDao;
import roomescape.domain.ReservationTime.ReservationTime;
import roomescape.domain.ReservationTime.ReservationTimeCommand;
import roomescape.exception.DataReferencedException;
import roomescape.exception.ErrorMessage;

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

    public void deleteReservationTime(long id) {
        try {
            reservationTimeDao.deleteReservationTime(id);
        }  catch(DataIntegrityViolationException e) {
            throw new DataReferencedException(ErrorMessage.CANNOT_DELETE_RESERVATION_TIME_IN_USE);
        }
    }
}
