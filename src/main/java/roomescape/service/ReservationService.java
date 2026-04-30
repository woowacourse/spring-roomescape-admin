package roomescape.service;

import java.time.LocalDate;
import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.dao.ReservationDao;
import roomescape.dao.ReservationTimeDao;
import roomescape.domain.Name;
import roomescape.domain.Reservation;
import roomescape.dto.request.ReservationRequest;
import roomescape.domain.ReservationTime;

@Service
public class ReservationService {
    private final ReservationDao reservationDao;
    private final ReservationTimeDao reservationTimeDao;

    public ReservationService(ReservationDao reservationDao, ReservationTimeDao reservationTimeDao) {
        this.reservationDao = reservationDao;
        this.reservationTimeDao = reservationTimeDao;
    }

    public List<Reservation> findAllReservations() {
        return reservationDao.findAll();
    }

    public Reservation createReservation(ReservationRequest request) {
        ReservationTime reservationTime = reservationTimeDao.findById(request.timeId());

        Long generatedId = reservationDao.insertReservation(
                request.name(),
                request.date(),
                request.timeId()
        );

        return new Reservation(
                generatedId,
                Name.parse(request.name()),
                LocalDate.parse(request.date()),
                reservationTime
        );
    }

    public void deleteReservation(Long id) {
        reservationDao.deleteById(id);
    }
}
