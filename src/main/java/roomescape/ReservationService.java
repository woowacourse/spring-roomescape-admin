package roomescape;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationService {

    private ReservationDao reservationDao;
    private ReservationTimeDao reservationTimeDao;

    public ReservationService(ReservationDao reservationDao, ReservationTimeDao reservationTimeDao) {
        this.reservationDao = reservationDao;
        this.reservationTimeDao = reservationTimeDao;
    }

    public List<Reservation> findAll() {
        return reservationDao.findAll();
    }

    public Reservation createReservation(ReservationRequest request) {
        ReservationTime time = reservationTimeDao.findBy(request.getTimeId());
        Reservation reservation = new Reservation(
                null,
                request.getName(),
                request.getDate(),
                time);
        Long id = reservationDao.insert(reservation);
        return reservationDao.findBy(id);
    }

    public void deleteReservation(Long id) {
        int deleteCount = reservationDao.delete(id);
        if (deleteCount != 1) {
            throw new IllegalArgumentException("[ERROR] 삭제 요청 실패");
        }
    }
}
