package roomescape.reservation;

import java.time.LocalDate;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roomescape.exception.ApiException;
import roomescape.exception.ErrorCode;
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
        return reservationRepository.save(name, date, reservationTime);
    }

    public List<Reservation> getReservations() {
        return reservationRepository.findAll();
    }

    @Transactional
    public void deleteReservation(long id) {
        int affectedRow = reservationRepository.delete(id);

        if (affectedRow == 0) {
            throw new ApiException(ErrorCode.RESERVATION_NOT_FOUND, id);
        }
    }
}
