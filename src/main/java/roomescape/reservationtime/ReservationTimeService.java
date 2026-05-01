package roomescape.reservationtime;

import java.time.LocalTime;
import java.util.List;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roomescape.exception.ApiException;
import roomescape.exception.ErrorCode;

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
                .orElseThrow(() -> new ApiException(ErrorCode.RESERVATION_TIME_NOT_FOUND, id));
    }

    @Transactional
    public ReservationTime save(LocalTime startAt) {
        try {
            return reservationTimeRepository.save(startAt);
        } catch (DataIntegrityViolationException e) {
            throw new ApiException(ErrorCode.DUPLICATE_RESERVATION_TIME, startAt);
        }
    }

    @Transactional
    public void delete(long id) {
        int affectedRow = reservationTimeRepository.delete(id);

        if (affectedRow == 0) {
            throw new ApiException(ErrorCode.RESERVATION_NOT_FOUND, id);
        }
    }
}
