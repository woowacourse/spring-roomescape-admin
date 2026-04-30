package roomescape.time.service;

import java.time.LocalTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roomescape.time.entity.ReservationTime;
import roomescape.time.exception.ReservationTimeErrorCode;
import roomescape.time.exception.ReservationTimeException;
import roomescape.time.repository.ReservationTimeRepository;

@Service
@RequiredArgsConstructor
public class ReservationTimeService {

    private final ReservationTimeRepository reservationTimeRepository;

    @Transactional
    public ReservationTime save(LocalTime startAt) {
        ReservationTime reservationTime = ReservationTime.createNew(startAt);
        return reservationTimeRepository.save(reservationTime);
    }

    public ReservationTime getById(long id) {
        return reservationTimeRepository.findById(id)
                .orElseThrow(() -> new ReservationTimeException(ReservationTimeErrorCode.RESERVATION_TIME_NOT_FOUND));
    }

    public List<ReservationTime> findAll() {
        return reservationTimeRepository.findAll();
    }

    @Transactional
    public void deleteById(long id) {
        reservationTimeRepository.deleteById(id);
    }

}
