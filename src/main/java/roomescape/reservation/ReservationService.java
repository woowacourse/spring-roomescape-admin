package roomescape.reservation;

import java.time.LocalDate;
import java.util.List;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roomescape.reservation.exception.ReservationException;
import roomescape.reservation.exception.ReservationErrorCode;
import roomescape.reservationtime.ReservationTime;
import roomescape.reservationtime.ReservationTimeService;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final ReservationTimeService reservationTimeService;

    ReservationService(ReservationRepository reservationRepository, ReservationTimeService reservationTimeService) {
        this.reservationRepository = reservationRepository;
        this.reservationTimeService = reservationTimeService;
    }

    @Transactional
    public Reservation createReservation(String name, LocalDate date, long timeId) {
        ReservationTime reservationTime = reservationTimeService.findById(timeId);
        try {
            return reservationRepository.save(name, date, reservationTime);
        } catch (DataIntegrityViolationException e) {
            throw new ReservationException(ReservationErrorCode.DUPLICATE);
        }
    }

    public List<Reservation> getReservations() {
        return reservationRepository.findAll();
    }

    @Transactional
    public void deleteReservation(long id) {
        int affectedRow = reservationRepository.delete(id);

        if (affectedRow == 0) {
            throw new ReservationException(ReservationErrorCode.NOT_FOUND);
        }
    }
}
