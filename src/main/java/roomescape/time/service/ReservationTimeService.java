package roomescape.time.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.time.domain.ReservationTime;
import roomescape.time.dto.CreateResrvationTimeRequest;
import roomescape.time.dto.ReservationTimeResponse;
import roomescape.time.mapper.ReservationTimeMapper;
import roomescape.time.repository.ReservationTimeRepository;

@Service
public class ReservationTimeService {

    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationTimeService(ReservationTimeRepository reservationTimeRepository) {
        this.reservationTimeRepository = reservationTimeRepository;
    }

    public ReservationTimeResponse addReservationTime(CreateResrvationTimeRequest createResrvationTimeRequest) {
        ReservationTime reservationTime = ReservationTimeMapper.toReservationTime(createResrvationTimeRequest);
        ReservationTime savedReservationTime = reservationTimeRepository.save(reservationTime);
        return ReservationTimeResponse.from(savedReservationTime);
    }

    public List<ReservationTimeResponse> findAllReservationTimes() {
        return reservationTimeRepository.findAll().stream()
                .map(ReservationTimeResponse::from)
                .toList();
    }

    public void removeRegisteredReservationTime(Long id) {
        reservationTimeRepository.deleteById(id);
    }
}
