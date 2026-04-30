package roomescape.time.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.time.domain.ReservationTime;
import roomescape.time.dto.ReservationTimeResponse;
import roomescape.time.mapper.ReservationTimeMapper;
import roomescape.time.repository.ReservationTimeRepository;

@Service
public class ReservationTimeService {

    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationTimeService(ReservationTimeRepository reservationTimeRepository) {
        this.reservationTimeRepository = reservationTimeRepository;
    }

    public ReservationTimeResponse addReservationTime(ReservationTime requestedReservationTime) {
        ReservationTime savedReservationTime = reservationTimeRepository.save(requestedReservationTime);
        return ReservationTimeMapper.toReseravationTimeRespone(savedReservationTime);
    }

    public List<ReservationTimeResponse> findAllReservationTimes() {
        return reservationTimeRepository.findAll().stream()
                .map(ReservationTimeMapper::toReseravationTimeRespone)
                .toList();
    }

    public void removeRegisteredReservationTime(Long id) {
        reservationTimeRepository.deleteById(id);
    }
}
