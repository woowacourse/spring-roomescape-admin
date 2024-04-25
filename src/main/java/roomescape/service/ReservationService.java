package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.entity.Reservation;
import roomescape.entity.ReservationTime;
import roomescape.dao.JdbcReservationDao;
import roomescape.dao.JdbcReservationTimeDao;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationResponse;

@Service
public class ReservationService {

    private final JdbcReservationDao jdbcReservationDao;
    private final JdbcReservationTimeDao jdbcReservationTimeDao;

    public ReservationService(JdbcReservationDao jdbcReservationDao, JdbcReservationTimeDao jdbcReservationTimeDao) {
        this.jdbcReservationDao = jdbcReservationDao;
        this.jdbcReservationTimeDao = jdbcReservationTimeDao;
    }

    public List<ReservationResponse> findAll() {
        List<Reservation> reservations = jdbcReservationDao.findAll();
        return reservations.stream()
                .map(ReservationResponse::of)
                .toList();
    }

    public ReservationResponse save(ReservationRequest reservationRequest) {
        ReservationTime reservationTime = jdbcReservationTimeDao.findById(reservationRequest.timeId());
        Reservation reservation = reservationRequest.toReservation(reservationTime);
        long id = jdbcReservationDao.save(reservation);
        return findById(id);
    }

    private ReservationResponse findById(long id) {
        Reservation reservation = jdbcReservationDao.findById(id);
        return ReservationResponse.of(reservation);
    }

    public void deleteById(long id) {
        int isDelete = jdbcReservationDao.deleteById(id);
        if (isDelete < 1) {
            throw new IllegalArgumentException("해당 id는 존재하지 않습니다.");
        }
    }
}
