package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.db.ReservationDao;
import roomescape.db.ReservationTimeDao;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationRequest;


@Service
public class ReservationService {

    private final ReservationDao reservationDao;

    private final ReservationTimeDao reservationTimeDao;

    public ReservationService(
            final ReservationDao reservationDao,
            final ReservationTimeDao reservationTimeDao
    ) {
        this.reservationDao = reservationDao;
        this.reservationTimeDao = reservationTimeDao;
    }

    public Reservation create(final ReservationRequest reservationRequest) {
        validateNotNull(reservationRequest);
        final Reservation reservation = new Reservation(
                reservationRequest.name(),
                reservationRequest.date(),
                reservationTimeDao.findById(reservationRequest.timeId())
                        .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 time id입니다."))
        );
        return reservationDao.save(reservation);
    }

    private void validateNotNull(final ReservationRequest reservationRequest) {
        if (reservationRequest == null) {
            throw new IllegalArgumentException("null이 될 수 없습니다.");
        }
    }

    public List<Reservation> findAll() {
        return reservationDao.findAll();
    }

    public void deleteById(final long id) {
        reservationDao.deleteById(id);
    }
}
