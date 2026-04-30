package roomescape.step4.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roomescape.step2.ReservationRepository;
import roomescape.step4.domain.ReservationTime;
import roomescape.step4.dto.ReservationTimeRequest;
import roomescape.step4.repository.ReservationTimeRepository;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional(readOnly = true)
public class ReservationTimeService {

    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationTimeService(ReservationTimeRepository reservationTimeRepository, ReservationRepository reservationRepository) {
        this.reservationTimeRepository = reservationTimeRepository;
    }

    @Transactional
    public ReservationTime saveReservationTime(ReservationTimeRequest request) {
        ReservationTime reservationTime = new ReservationTime(null, request.startAt());

        return reservationTimeRepository.save(reservationTime);
    }

    public List<ReservationTime> findAllReservationTimes() {
        return reservationTimeRepository.findAll();
    }

    @Transactional
    public void deleteReservationTime(Long id) {
        int deleted = reservationTimeRepository.deleteById(id);

        if (deleted == 0) {
            throw new NoSuchElementException("삭제할 예약 시간이 존재하지 않습니다.");
        }
    }

    public ReservationTime findReservationTime(Long id) {
        return reservationTimeRepository.findById(id);
    }
}
