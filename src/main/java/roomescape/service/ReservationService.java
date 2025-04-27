package roomescape.service;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationResponse;
import roomescape.model.Reservation;
import roomescape.model.ReservationTime;
import roomescape.dao.JdbcReservationDao;
import roomescape.dao.JdbcReservationTimeDao;

@Service
public class ReservationService {

    private final JdbcReservationDao jdbcReservationDao;
    private final JdbcReservationTimeDao jdbcReservationTimeDao;

    public ReservationService(JdbcReservationDao jdbcReservationDao,
                              JdbcReservationTimeDao jdbcReservationTimeDao) {
        this.jdbcReservationDao = jdbcReservationDao;
        this.jdbcReservationTimeDao = jdbcReservationTimeDao;
    }

    public ReservationResponse addReservation(ReservationRequest reservationRequest) {
        ReservationTime reservationTime = jdbcReservationTimeDao.findById(reservationRequest.timeId());
        Reservation reservation = reservationRequest.toEntityWithReservationTime(reservationTime);
        Reservation savedReservation = jdbcReservationDao.save(reservation);
        return ReservationResponse.fromEntity(savedReservation);
    }

    public void deleteReservation(Long id) {
        boolean isDeleted = jdbcReservationDao.deleteById(id);
        if (!isDeleted) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "해당하는 id가 없습니다");
        }
    }

    public List<ReservationResponse> getReservations() {
        return jdbcReservationDao.findAll()
                .stream()
                .map(ReservationResponse::fromEntity)
                .toList();
    }
}
