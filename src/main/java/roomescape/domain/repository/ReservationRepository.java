package roomescape.domain.repository;

import java.util.List;
import roomescape.domain.Reservation;

public interface ReservationRepository {
    // 예약 추가
    Long save(Reservation reservation);

    // 예약 조회
    List<Reservation> findAll();

    // 예약 삭제
    void delete(Long id);
}
