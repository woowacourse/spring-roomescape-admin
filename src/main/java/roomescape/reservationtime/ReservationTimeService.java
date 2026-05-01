package roomescape.reservationtime;

import java.time.LocalTime;
import java.util.List;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roomescape.reservation.ReservationRepository;
import roomescape.reservationtime.exception.ReservationTimeException;
import roomescape.reservationtime.exception.ReservationTimeErrorCode;

@Service
public class ReservationTimeService {
    private final ReservationTimeRepository reservationTimeRepository;
    private final ReservationRepository reservationRepository;

    public ReservationTimeService(ReservationTimeRepository reservationTimeRepository,
                                  ReservationRepository reservationRepository) {
        this.reservationTimeRepository = reservationTimeRepository;
        this.reservationRepository = reservationRepository;
    }

    public List<ReservationTime> findAll() {
        return reservationTimeRepository.findAll();
    }

    @Transactional
    public ReservationTime save(LocalTime startAt) {
        try {
            return reservationTimeRepository.save(startAt);
        } catch (DataIntegrityViolationException e) {
            throw new ReservationTimeException(ReservationTimeErrorCode.DUPLICATE);
        }
    }

    @Transactional
    public void delete(long id) {
        int reservationCount = reservationRepository.countByTimeId(id);

        if (reservationCount > 0) {
            throw new ReservationTimeException(ReservationTimeErrorCode.HAS_RESERVATION);
        }

        int affectedRow = reservationTimeRepository.delete(id);

        if (affectedRow == 0) {
            throw new ReservationTimeException(ReservationTimeErrorCode.NOT_FOUND);
        }

    }
}
