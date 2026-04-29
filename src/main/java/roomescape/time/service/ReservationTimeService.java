package roomescape.time.service;

import java.time.LocalTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roomescape.time.controller.dto.ReservationTimeRequest;
import roomescape.time.controller.dto.ReservationTimeResponse;
import roomescape.time.entity.ReservationTime;
import roomescape.time.repository.ReservationTimeRepository;

@Service
@RequiredArgsConstructor
public class ReservationTimeService {

    private final ReservationTimeRepository reservationTimeRepository;

    @Transactional
    public ReservationTimeResponse save(ReservationTimeRequest reservationTimeRequest) {
        LocalTime startAt = reservationTimeRequest.startAt();
        ReservationTime reservationTime = ReservationTime.createNew(startAt);
        return ReservationTimeResponse.from(reservationTimeRepository.save(reservationTime));
    }

    public ReservationTime getById(long id) {
        return reservationTimeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("찾는 예약 시간이 없습니다."));
    }

    public List<ReservationTimeResponse> findAll() {
        return reservationTimeRepository.findAll().stream()
                .map(ReservationTimeResponse::from)
                .toList();
    }

    @Transactional
    public void deleteById(long id) {
        if (!reservationTimeRepository.existsById(id)) {
            throw new IllegalArgumentException("삭제할 예약 시간이 존재하지 않습니다.");
        }
        reservationTimeRepository.deleteById(id);
    }

}
