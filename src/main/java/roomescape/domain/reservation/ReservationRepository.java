package roomescape.domain.reservation;

import java.util.List;

public interface ReservationRepository {
    // 예약 추가
    Long save(Reservation reservation);

    // 예약 조회
    List<Reservation> findAll();

    // 예약 삭제
    void delete(Long id);
}
