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

    public Reservation create(ReservationRequest request) {
        ReservationTime time = reservationTimeDao.findBy(request.getTimeId());
        Reservation reservation = new Reservation(
                null,
                request.getName(),
                request.getDate(),
                time);
        Long id = reservationDao.insert(reservation);
        return reservationDao.findBy(id);
    }

    public void delete(Long id) {
        int deletedCount = reservationDao.delete(id);
        if (deletedCount != 1) {
            throw new IllegalArgumentException("[ERROR] 삭제 요청 실패");
        }
    }
}
