package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.dto.TimeRequest;
import roomescape.dto.TimeResponse;
import roomescape.repository.ReservationTimeRepository;

@Service
public class ReservationTimeService {
    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationTimeService(ReservationTimeRepository reservationTimeRepository) {
        this.reservationTimeRepository = reservationTimeRepository;
    }

    public List<TimeResponse> getAllReservationTimes() {
        return reservationTimeRepository.findAll()
                .stream()
                .map(TimeResponse::new)
                .toList();
    }

    public TimeResponse addReservationTime(TimeRequest timeRequest) {
        return new TimeResponse(reservationTimeRepository.add(timeRequest));
    }

    public void deleteReservationTime(Long id) {
        reservationTimeRepository.delete(id);
    }
}
