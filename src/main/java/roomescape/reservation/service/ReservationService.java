package roomescape.reservation.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.reservation.domain.Reservation;
import roomescape.reservation.dto.ReservationRequest;
import roomescape.reservation.repository.ReservationJdbcDao;
import roomescape.time.domain.ReservationTime;
import roomescape.time.repository.ReservationTimeJdbcDao;

@Service
public class ReservationService {

    private final ReservationJdbcDao reservationJdbcDao;
    private final ReservationTimeJdbcDao reservationTimeJdbcDao;

    public ReservationService(ReservationJdbcDao reservationJdbcDao, ReservationTimeJdbcDao reservationTimeJdbcDao) {
        this.reservationJdbcDao = reservationJdbcDao;
        this.reservationTimeJdbcDao = reservationTimeJdbcDao;
    }

    public List<Reservation> findAll() {
        return reservationJdbcDao.findAll();
    }

    public Reservation save(ReservationRequest dto) {
        ReservationTime reservationTime = reservationTimeJdbcDao.findById(dto.timeId());
        Reservation reservation = Reservation.create(dto, reservationTime);

        Long savedReservationId = reservationJdbcDao.save(reservation);

        Reservation savedReservation = Reservation.create(savedReservationId,
                reservation.getName(),
                reservation.getDate(),
                reservation.getReservationTime());

        return savedReservation;
    }

    public int deleteById(Long id) {
        return reservationJdbcDao.deleteById(id);
    }
}
