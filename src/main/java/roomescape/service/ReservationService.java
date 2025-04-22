package roomescape.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationRepository;

@Service
public class ReservationService {

    private final ReservationRepository reservationDao;

    public ReservationService(ReservationRepository reservationDao) {
        this.reservationDao = reservationDao;
    }

    public void deleteReservation(Long id) {
        reservationDao.deleteById(id);
    }

    public long addReservation(Reservation newReservation) {
        LocalDate nowDate = LocalDate.now();
        LocalTime nowTime = LocalTime.now();

        if (newReservation.getDate().isBefore(nowDate)) {
            throw new IllegalArgumentException("과거 시간에 예약할 수 없습니다.");
        }

        if (newReservation.getDate().isEqual(nowDate) && newReservation.getTime().getTime().isBefore(nowTime)) {
            throw new IllegalArgumentException("과거 시간에 예약할 수 없습니다.");
        }
        return reservationDao.add(newReservation);
    }

    public List<Reservation> allReservations() {
        return reservationDao.findAll();
    }
}
