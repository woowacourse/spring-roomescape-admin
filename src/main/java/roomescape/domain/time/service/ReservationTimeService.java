package roomescape.domain.time.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.domain.time.ReservationTime;
import roomescape.domain.time.dto.ReservationTimeRequestDTO;
import roomescape.domain.time.repository.ReservationTimeRepository;

@Service
public class ReservationTimeService {
    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationTimeService(ReservationTimeRepository reservationTimeRepository) {
        this.reservationTimeRepository = reservationTimeRepository;
    }

    public ReservationTime create(ReservationTimeRequestDTO requestDTO) {
        ReservationTime reservationTime = new ReservationTime(null, requestDTO.getStartAt());
        Long id = reservationTimeRepository.save(reservationTime);
        return new ReservationTime(id, reservationTime.getStartAt());
    }

    public List<ReservationTime> getAll() {
        return reservationTimeRepository.findAll();
    }

    public void delete(Long id) {
        reservationTimeRepository.delete(id);
    }
}
