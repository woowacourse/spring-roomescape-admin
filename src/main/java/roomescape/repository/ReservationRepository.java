package roomescape.repository;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.springframework.stereotype.Repository;
import roomescape.dao.ReservationDao;
import roomescape.dao.ReservationTimeDao;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationCommand;
import roomescape.domain.ReservationTime;

@Repository
public class ReservationRepository {
    private static final String INVALID_RESERVATION_TIME_ID = "유효하지 않은 시간 id입니다.";

    private final ReservationDao reservationDao;
    private final ReservationTimeDao reservationTimeDao;

    public ReservationRepository(ReservationDao reservationDao, ReservationTimeDao reservationTimeDao) {
        this.reservationDao = reservationDao;
        this.reservationTimeDao = reservationTimeDao;
    }

    public List<Reservation> getAllReservation() {
        return Collections.unmodifiableList(reservationDao.getAllReservation());
    }

    public Reservation addReservation(ReservationCommand reservationCommand) {
        Optional<ReservationTime> optionalReservationTime = reservationTimeDao.getReservationTime(reservationCommand.timeId());

        if(optionalReservationTime.isEmpty()) {
            throw new NoSuchElementException(INVALID_RESERVATION_TIME_ID);
        }

        long id = reservationDao.insertReservation(reservationCommand);

        return new Reservation(id, reservationCommand.name(), reservationCommand.date(), optionalReservationTime.get());
    }

    public int deleteReservation(long id) {
        return reservationDao.deleteReservation(id);
    }
}
