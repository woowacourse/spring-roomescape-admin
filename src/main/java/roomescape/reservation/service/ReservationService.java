package roomescape.reservation.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.common.Dao;
import roomescape.reservation.Reservation;
import roomescape.reservation.dto.ReservationDto;
import roomescape.reservationTime.ReservationTime;

@Service
public class ReservationService {
    private final Dao<Reservation> reservationDao;
    private final Dao<ReservationTime> reservationTimeDao;

    public ReservationService(Dao<Reservation> reservationDao, Dao<ReservationTime> reservationTimeDao) {
        this.reservationDao = reservationDao;
        this.reservationTimeDao = reservationTimeDao;
    }

    public Reservation add(ReservationDto reservationDto) {
        Reservation reservation = reservationDto.createReservation();
        Reservation findReservation = reservationDao.add(reservation);
        ReservationTime findReservationTime = reservationTimeDao.getById(reservationDto.timeId());
        return new Reservation( // ?
                findReservation.getId(),
                findReservation.getName(),
                findReservation.getDate(),
                findReservationTime);
    }

    public List<Reservation> getAll() {
        return reservationDao.getAll();
    }

    public void deleteById(Long id) {
        reservationDao.deleteById(id);
    }
}
