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
        List<ReservationTime> reservationTimes = reservationTimeRepository.getAll();
        for (ReservationTime reservationTime : reservationTimes) {
            validateDuplicateTime(reservationTimeRequest, reservationTime);
        }

        ReservationTime reservationTime = ReservationTime.create(
                null,
                reservationTimeRequest.startAt()
        );
        reservationTime.setId(reservationTimeRepository.save(reservationTime));

        return ReservationTimeResponse.from(reservationTime);
    }

    public List<ReservationTimeResponse> getAll() {
        return reservationTimeRepository.getAll()
                .stream()
                .map(ReservationTimeResponse::from)
                .toList();
    }

    @Transactional
    public void delete(Long id) {
        reservationTimeRepository.delete(id);
    }

    private void validateDuplicateTime(ReservationTimeRequest reservationTimeRequest, ReservationTime reservationTime) {
        if (reservationTime.isSameTime(reservationTimeRequest.startAt())) {
            throw new IllegalArgumentException("이미 존재하는 시간 슬롯입니다.");
        }
    }
}
