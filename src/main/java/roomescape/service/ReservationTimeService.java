package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.dao.ReservationTimeRepository;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationTimeRequest;

@Service
public class ReservationTimeService {

    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationTimeService(ReservationTimeRepository reservationTimeRepository) {
        this.reservationTimeRepository = reservationTimeRepository;
    }

    public List<ReservationTime> findAllReservationTimes() {
        return reservationTimeRepository.findAll();
    }

    public ReservationTime findReservationTime(Long id) {
        return reservationTimeRepository.findById(id);
    }

    public ReservationTime createReservationTime(ReservationTimeRequest request) {
        ReservationTime time = new ReservationTime(request.getStartAt());
        return reservationTimeRepository.save(time);
    }

    public void deleteReservationTime(Long id) {
        reservationTimeRepository.deleteById(id);
    }


}
