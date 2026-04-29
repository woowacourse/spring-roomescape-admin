package roomescape.reservationtime.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import roomescape.reservationtime.domain.ReservationTime;
import roomescape.reservationtime.dto.ReservationTimeRequest;
import roomescape.reservationtime.mapper.ReservationTimeMapper;
import roomescape.reservationtime.repository.ReservationTimeRepository;

@Service
@RequiredArgsConstructor
public class ReservationTimeService {
    private final ReservationTimeRepository reservationTimeRepository;

    public List<ReservationTime> findAllReservationTimes() {
        return reservationTimeRepository.findAllReservationTimes();
    }

    public ReservationTime saveReservationTime(ReservationTimeRequest reservationTimeRequest) {
        ReservationTime reservationTime = ReservationTimeMapper.toEntity(reservationTimeRequest);
        return reservationTimeRepository.saveReservationTime(reservationTime);
    }

    public int deleteById(Long id) {
        return reservationTimeRepository.deleteById(id);
    }
}
