package roomescape.repository;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import roomescape.dao.ReservationDao;
import roomescape.dto.ReservationSaveDto;
import roomescape.entity.Reservation;

@Repository
public class ReservationRepository {

    private final ReservationDao reservationDao;

    @Autowired
    public ReservationRepository(ReservationDao reservationDao) {
        this.reservationDao = reservationDao;
    }

    public List<Reservation> findAll() {
        return reservationDao.findAllReservations();
    }

    public Reservation save(ReservationSaveDto reservationDto) {
        long id = reservationDao.save(reservationDto);
        return reservationDao.findById(id);
    }

    public void delete(long id) {
        reservationDao.delete(id);
    }
}
