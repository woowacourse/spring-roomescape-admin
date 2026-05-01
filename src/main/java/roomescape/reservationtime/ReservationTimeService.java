package roomescape.reservationtime;

import java.time.LocalTime;
import java.util.List;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roomescape.reservationtime.exception.DuplicateReservationTimeException;
import roomescape.reservationtime.exception.ReservationTimeNotFoundException;

@Service
public class ReservationTimeService {
    private final ReservationTimeRepository reservationTimeRepository;

    ReservationTimeService(ReservationTimeRepository reservationTimeRepository) {
        this.reservationTimeRepository = reservationTimeRepository;
    }

    public List<ReservationTime> findAll() {
        return reservationTimeRepository.findAll();
    }

    public ReservationTime findById(long id) {
        return reservationTimeRepository.findById(id)
                .orElseThrow(() -> new ReservationTimeNotFoundException(id));
    }

    @Transactional
    public ReservationTime save(LocalTime startAt) {
        try {
            return reservationTimeRepository.save(startAt);
        } catch (DataIntegrityViolationException e) {
            throw new DuplicateReservationTimeException(startAt.toString());
        }
    }

    @Transactional
    public void delete(long id) {
        int affectedRow = reservationTimeRepository.delete(id);

        if (affectedRow == 0) {
            throw new ReservationTimeNotFoundException(id);
        }
    }
}
