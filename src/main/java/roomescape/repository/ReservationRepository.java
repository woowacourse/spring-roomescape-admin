package roomescape.repository;

import roomescape.domain.Reservation;

import java.util.List;

public interface ReservationRepository {

    // 저장
    Long save(Reservation reservation);

    // 전체 조회
    List<Reservation> findAll();

    // 삭제
    void delete(Long id);
}
