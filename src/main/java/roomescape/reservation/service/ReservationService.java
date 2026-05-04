package roomescape.reservation.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roomescape.reservation.domain.Reservation;
import roomescape.reservation.dto.ReservationRequest;
import roomescape.reservation.repository.ReservationJdbcDao;
import roomescape.time.domain.ReservationTime;
import roomescape.time.repository.ReservationTimeJdbcDao;

@Service
@Transactional
public class ReservationService {

    private final ReservationJdbcDao reservationJdbcDao;
    private final ReservationTimeJdbcDao reservationTimeJdbcDao;

    public ReservationService(ReservationJdbcDao reservationJdbcDao, ReservationTimeJdbcDao reservationTimeJdbcDao) {
        this.reservationJdbcDao = reservationJdbcDao;
        this.reservationTimeJdbcDao = reservationTimeJdbcDao;
    }

    @Transactional(readOnly = true)
    public List<Reservation> findAll() {
        return reservationJdbcDao.findAll();
    }

    public Reservation save(ReservationRequest dto) {
        ReservationTime reservationTime = reservationTimeJdbcDao.findById(dto.timeId());
        Reservation reservation = Reservation.create(dto, reservationTime);

        Long savedReservationId = reservationJdbcDao.save(reservation);

        return Reservation.create(savedReservationId,
                reservation.getName(),
                reservation.getDate(),
                reservation.getReservationTime());
    }

    public int deleteById(Long id) {
        return reservationJdbcDao.deleteById(id);
    }
}
