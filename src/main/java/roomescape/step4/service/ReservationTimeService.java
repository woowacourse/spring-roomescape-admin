package roomescape.step4.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roomescape.step2.ReservationRepository;
import roomescape.step4.domain.ReservationTime;
import roomescape.step4.dto.ReservationTimeRequest;
import roomescape.step4.repository.ReservationTimeRepository;

import java.util.List;

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
        reservationTimeRepository.deleteById(id);
    }

    public ReservationTime findReservationTime(Long id) {
        return reservationTimeRepository.findById(id);
    }
}
