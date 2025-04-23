package roomescape.service;

import jakarta.validation.Valid;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationRepository;
import roomescape.domain.ReservationTime;
import roomescape.dto.AddReservationDto;

@Service
public class ReservationService {

    private final ReservationRepository reservationDao;
    private final ReservationTimeService reservationTimeService;

    public ReservationService(ReservationRepository reservationDao, ReservationTimeService reservationTimeService) {
        this.reservationDao = reservationDao;
        this.reservationTimeService = reservationTimeService;
    }

    public void deleteReservation(Long id) {
        reservationDao.deleteById(id);
    }

    @Transactional
    public long addReservation(@Valid AddReservationDto newReservation) {
        ReservationTime reservationTime = reservationTimeService.findReservationTimeById(newReservation.timeId());
        Reservation reservation = newReservation.toReservation(reservationTime);
        LocalDate nowDate = LocalDate.now();
        LocalTime nowTime = LocalTime.now();
        validateAddReservationDateTime(reservation, nowDate, nowTime);
        return reservationDao.add(reservation);
    }

    private void validateAddReservationDateTime(Reservation newReservation, LocalDate nowDate,
                                                LocalTime nowTime) {
        boolean isAfterNow = false;
        if (newReservation.getDate().isBefore(nowDate)) {
            isAfterNow = true;
        }

        if (newReservation.getDate().isEqual(nowDate) && newReservation.getTime().getTime().isBefore(nowTime)) {
            isAfterNow = true;
        }
        if (isAfterNow) {
            throw new IllegalArgumentException("과거 시간에 예약할 수 없습니다.");
        }
    }

    public List<Reservation> allReservations() {
        return reservationDao.findAll();
    }
}
