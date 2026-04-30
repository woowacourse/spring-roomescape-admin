package roomescape.reservationtime.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import roomescape.reservationtime.domain.ReservationTime;
import roomescape.reservationtime.dto.ReservationTimeRequest;
import roomescape.reservationtime.dto.ReservationTimeResponse;
import roomescape.reservationtime.mapper.ReservationTimeMapper;
import roomescape.reservationtime.repository.ReservationTimeRepository;

@Service
@RequiredArgsConstructor
public class ReservationTimeService {
    private final ReservationTimeRepository reservationTimeRepository;

    public List<ReservationTimeResponse> findAllReservationTimes() {
        List<ReservationTime> reservationTimes = reservationTimeRepository.findAllReservationTimes();

        return reservationTimes.stream()
                .map(ReservationTimeMapper::toResponse)
                .toList();
    }

    public ReservationTimeResponse saveReservationTime(ReservationTimeRequest reservationTimeRequest) {
        ReservationTime reservationTime = ReservationTimeMapper.toEntity(reservationTimeRequest);
        ReservationTime createdReservationTime = reservationTimeRepository.saveReservationTime(reservationTime);
        return ReservationTimeMapper.toResponse(createdReservationTime);
    }

    public int deleteById(Long id) {
        return reservationTimeRepository.deleteById(id);
    }
}
