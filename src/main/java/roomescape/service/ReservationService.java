package roomescape.service;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.dao.ReservationDao;
import roomescape.dao.ReservationTimeDao;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.service.dto.ReservationRequest;
import roomescape.service.dto.ReservationResponse;

@Service
public class ReservationService {

    private final ReservationDao reservationDao;
    private final ReservationTimeDao reservationTimeDao;
    private final Clock clock;

    public ReservationService(final ReservationDao reservationDao, final ReservationTimeDao reservationTimeDao,
                              final Clock clock) {
        this.reservationDao = reservationDao;
        this.reservationTimeDao = reservationTimeDao;
        this.clock = clock;
    }

    public List<ReservationResponse> readAll() {
        List<Reservation> reservations = reservationDao.findAll();
        return reservations.stream()
                .map(ReservationResponse::from)
                .toList();
    }

    public ReservationResponse create(ReservationRequest reservationRequest) {
        Reservation reservation = reservationRequest.toReservation();
        ReservationTime reservationTime = reservationTimeDao.findById(reservationRequest.timeId());
        Reservation reservationWithTime = reservationDao.insert(reservation, reservationTime);

        validatePastReservation(reservationWithTime);
        return ReservationResponse.from(reservationWithTime);
    }

    private void validatePastReservation(Reservation reservation) {
        if (reservation.isBefore(LocalDateTime.now(clock))) {
            throw new IllegalArgumentException("예약은 과거일 수 없습니다.");
        }
    }

    public void deleteById(long id) {
        reservationDao.deleteById(id);
    }
}
