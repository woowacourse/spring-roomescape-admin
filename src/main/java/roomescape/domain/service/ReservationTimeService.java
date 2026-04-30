package roomescape.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roomescape.domain.entity.ReservationTime;
import roomescape.domain.dto.ReservationTimeRequest;
import roomescape.domain.dto.ReservationTimeResponse;
import roomescape.domain.repository.ReservationTimeRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationTimeService {
    private final ReservationTimeRepository reservationTimeRepository;

    @Transactional
    public ReservationTimeResponse save(ReservationTimeRequest reservationTimeRequest) {
        ReservationTime reservationTime = ReservationTime.create(
                null,
                reservationTimeRequest.startAt()
        );

        return new ReservationTimeResponse(
                reservationTimeRepository.save(reservationTime),
                reservationTimeRequest.startAt()
        );
    }

    public List<ReservationTimeResponse> getAll() {
        return reservationTimeRepository.getAll()
                .stream()
                .map(reservationTime -> new ReservationTimeResponse(
                        reservationTime.getId(),
                        reservationTime.getStartAt())
                ).toList();
    }

    @Transactional
    public void delete(Long id) {
        reservationTimeRepository.delete(id);
    }
}
