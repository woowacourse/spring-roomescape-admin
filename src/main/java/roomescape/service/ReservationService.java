package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.reservation.ReservationQueryingDao;
import roomescape.reservation.Reservation;
import roomescape.reservation.ReservationRequest;
import roomescape.reservation.ReservationUpdatingDao;

import java.util.List;

@Service
public class ReservationService {

    private final ReservationQueryingDao reservationQueryingDao;
    private final ReservationUpdatingDao reservationUpdatingDao;

    public ReservationService(ReservationQueryingDao reservationQueryingDao, ReservationUpdatingDao reservationUpdatingDao) {
        this.reservationQueryingDao = reservationQueryingDao;
        this.reservationUpdatingDao = reservationUpdatingDao;
    }

    public List<Reservation> read() {
        return reservationQueryingDao.findAllReservations();
    }

    public Reservation create(ReservationRequest reservationReq) {
        Long generatedId = reservationUpdatingDao.insert(reservationReq);
        return reservationQueryingDao.findReservationById(generatedId);
    }

    public void update(Reservation newReservation, Long id) {
        reservationUpdatingDao.save(id, newReservation);
    }

    public void delete(Long id) {
        int count = reservationUpdatingDao.delete(id);

        if (count == 0) {
            throw new RuntimeException("삭제하려는 예약을 찾을 수 없습니다.");
        }
    }
}
