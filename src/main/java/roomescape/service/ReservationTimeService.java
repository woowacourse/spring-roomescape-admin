package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.controller.dto.ReservationTimeRequest;
import roomescape.controller.dto.ReservationTimeResponse;
import roomescape.domain.ReservationTime;
import roomescape.repository.ReservationTimeRepository;
import roomescape.service.dto.ReservationTimeDto;

@Service
public class ReservationTimeService {

    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationTimeService(ReservationTimeRepository reservationTimeRepository) {
        this.reservationTimeRepository = reservationTimeRepository;
    }

    public List<ReservationTimeResponse> getAllTimes() {
        return reservationTimeRepository.findAll()
                .stream()
                .map(ReservationTimeResponse::from)
                .toList();
    }

    public ReservationTimeResponse addTime(ReservationTimeRequest request) {
        ReservationTimeDto reservationTimeDto = ReservationTimeDto.from(request.toInstance());
        ReservationTime newReservationTime = reservationTimeRepository.create(reservationTimeDto);
        return ReservationTimeResponse.from(newReservationTime);
    }

    public void removeTime(Long id) {
        reservationTimeRepository.deleteById(id);
    }
}
