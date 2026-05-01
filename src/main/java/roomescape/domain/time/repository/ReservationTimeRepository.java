package roomescape.domain.time.repository;

import java.util.List;
import roomescape.domain.time.ReservationTime;

public interface ReservationTimeRepository {
    // 시간 추가
    Long save(ReservationTime reservationTime);

    // 시간 조회
    List<ReservationTime> findAll();

    // 시간 삭제
    void delete(Long id);

    // 시간 단일 조회
    ReservationTime findById(Long id);
}
