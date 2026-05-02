package roomescape.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roomescape.domain.entity.ReservationTime;
import roomescape.domain.dto.ReservationTimeRequest;
import roomescape.domain.dto.ReservationTimeResponse;
import roomescape.domain.repository.ReservationRepository;
import roomescape.domain.repository.ReservationTimeRepository;

import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationTimeService {
    private final ReservationTimeRepository reservationTimeRepository;
    private final ReservationRepository reservationRepository;

    @Transactional
    public ReservationTimeResponse save(ReservationTimeRequest reservationTimeRequest) {
        validateDuplicateTime(reservationTimeRequest.startAt());

        ReservationTime reservationTime = reservationTimeRequest.toEntity();
        Long id = reservationTimeRepository.save(reservationTime);

        ReservationTime savedReservationTime = ReservationTime.create(id, reservationTime.getStartAt());
        return ReservationTimeResponse.from(savedReservationTime);
    }

    public List<ReservationTimeResponse> getAll() {
        return reservationTimeRepository.findAll()
                .stream()
                .map(ReservationTimeResponse::from)
                .toList();
    }

    @Transactional
    public void delete(Long id) {
        reservationRepository.deleteAllByTimeId(id);
        reservationTimeRepository.deleteById(id);
    }

    private void validateDuplicateTime(LocalTime time) {
        if (reservationTimeRepository.existsByDateAndTime(time)) {
            throw new IllegalArgumentException("이미 존재하는 시간 슬롯입니다.");
        }
    }
}
