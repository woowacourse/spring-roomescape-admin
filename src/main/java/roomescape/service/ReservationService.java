package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
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
        final Reservation reservation = new Reservation(
                reservationRequest.name(),
                reservationRequest.date(),
                reservationTimeDao.findById(reservationRequest.timeId())
        );
        return reservationDao.save(reservation);
    }

    public List<Reservation> findAll() {
        return reservationDao.findAll();
    }

    public void deleteById(final long id) {
        reservationDao.deleteById(id);
    }
}
