package roomescape.reservationtime;

import java.time.LocalTime;
import java.util.List;
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

    public ReservationTime findById(Long id) {
        return reservationTimeRepository.findById(id)
                .orElseThrow(() -> new ApiException(ErrorCode.RESERVATION_TIME_NOT_FOUND, id));
    }

    @Transactional
    public ReservationTime save(LocalTime startAt) {
        return reservationTimeRepository.save(startAt);
    }

    @Transactional
    public void delete(Long id) {
        int affectedRow = reservationTimeRepository.delete(id);

        if (affectedRow == 0) {
            throw new ApiException(ErrorCode.RESERVATION_NOT_FOUND, id);
        }
    }
}
