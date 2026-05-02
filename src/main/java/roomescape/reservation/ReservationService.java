package roomescape.reservation;

import java.time.LocalDate;
import java.util.List;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roomescape.reservation.exception.ReservationException;
import roomescape.reservation.exception.ReservationErrorCode;
import roomescape.reservationtime.ReservationTime;
import roomescape.reservationtime.ReservationTimeRepository;
import roomescape.reservationtime.exception.ReservationTimeErrorCode;
import roomescape.reservationtime.exception.ReservationTimeException;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationService(ReservationRepository reservationRepository,
                              ReservationTimeRepository reservationTimeRepository) {
        this.reservationRepository = reservationRepository;
        this.reservationTimeRepository = reservationTimeRepository;
    }

    @Transactional
    public Reservation createReservation(String name, LocalDate date, long timeId) {
        ReservationTime reservationTime = reservationTimeRepository.findById(timeId)
                .orElseThrow(() -> new ReservationTimeException(ReservationTimeErrorCode.NOT_FOUND));
        try {
            return reservationRepository.save(name, date, reservationTime);
        } catch (DataIntegrityViolationException e) {
            throw new ReservationException(mapIntegrityViolation(e));
        }
    }

    public List<Reservation> getReservations() {
        return reservationRepository.findAll();
    }

    @Transactional
    public void deleteReservation(long id) {
        reservationRepository.delete(id);
    }

    private ReservationErrorCode mapIntegrityViolation(DataIntegrityViolationException e) {
        String message = getMostSpecificMessage(e);
        if (message.contains("unique_date_time")) {
            return ReservationErrorCode.DUPLICATE;
        }
        return ReservationErrorCode.INTEGRITY_VIOLATION;
    }

    private String getMostSpecificMessage(DataIntegrityViolationException e) {
        Throwable mostSpecificCause = e.getMostSpecificCause();
        if (mostSpecificCause != null && mostSpecificCause.getMessage() != null) {
            return mostSpecificCause.getMessage().toLowerCase();
        }
        if (e.getMessage() != null) {
            return e.getMessage().toLowerCase();
        }
        return "";
    }
}
