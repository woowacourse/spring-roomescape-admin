package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.dto.ReservationRequest;
import roomescape.entity.Reservation;
import roomescape.repository.ReservationDao;
import roomescape.repository.ReservationRegisterDetail;
import roomescape.repository.ReservationTimeDao;

@Service
public class ReservationService {

    private final ReservationDao reservationDao;
    private final ReservationTimeDao reservationTimeDao;

    public ReservationService(ReservationDao reservationDao, ReservationTimeDao reservationTimeDao) {
        this.reservationDao = reservationDao;
        this.reservationTimeDao = reservationTimeDao;
    }

    public Reservation addReservation(ReservationRequest request) {
        String timeValue = reservationTimeDao.findById(request.timeId()).getStartAt();
        ReservationRegisterDetail registerDetail = new ReservationRegisterDetail(request, timeValue);

        return reservationDao.save(registerDetail);
    }

    public List<Reservation> findAllReservations() {
        return reservationDao.findAll();
    }

    public void deleteReservationById(long reservationId) {
        reservationDao.deleteById(reservationId);
    }
}
