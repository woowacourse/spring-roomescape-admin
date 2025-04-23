package roomescape.time.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.time.controller.request.ReservationTimeCreateRequest;
import roomescape.time.domain.ReservationTime;
import roomescape.time.repository.ReservationTimeRepository;
import roomescape.time.service.exception.ReservationTimeNotFoundException;

@Service
public class ReservationTimeService {

    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationTimeService(ReservationTimeRepository reservationTimeRepository) {
        this.reservationTimeRepository = reservationTimeRepository;
    }

    public ReservationTime create(ReservationTimeCreateRequest request) {
        ReservationTime reservationTime = request.to();
        return reservationTimeRepository.save(reservationTime);
    }

    public List<ReservationTime> getAll() {
        return reservationTimeRepository.findAll();
    }

    public void deleteById(Long id) {
        ReservationTime reservationTime = getReservationTime(id);
        reservationTimeRepository.deleteById(reservationTime.getId());
    }

    public ReservationTime getReservationTime(Long id) {
        return reservationTimeRepository.findById(id)
                .orElseThrow(() -> new ReservationTimeNotFoundException("[ERROR] 예약 시간을 찾을 수 없습니다."));
    }
}
