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
@Transactional
public class ReservationTimeService {

    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationTimeResponse save(ReservationTimeRequest reservationTimeRequest) {
        LocalTime startAt = reservationTimeRequest.startAt();

        if(reservationTimeRepository.existsByStartAt(startAt))
            throw new IllegalArgumentException("[ERROR] 시간 중복 추가는 불가능합니다.");

        ReservationTime reservationTime = ReservationTime.createNew(startAt);
        return ReservationTimeResponse.from(reservationTimeRepository.save(reservationTime));
    }

    public ReservationTime findById(long id) {
        return reservationTimeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 찾는 예약 시간이 없습니다."));
    }

    public List<ReservationTimeResponse> findAll() {
        return reservationTimeRepository.finaAll().stream()
                .map(ReservationTimeResponse::from)
                .toList();
    }

    public void deleteById(long id) {
        reservationTimeRepository.deleteById(id);
    }

}
