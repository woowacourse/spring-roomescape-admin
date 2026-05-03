package roomescape.domain.reservation.repository;

import java.util.List;
import roomescape.domain.reservation.Reservation;

public interface ReservationRepository {
    // 예약 추가
    Reservation save(Reservation reservation);

    // 예약 조회
    List<Reservation> findAll();

    // 예약 삭제
    void delete(Long id);
}
