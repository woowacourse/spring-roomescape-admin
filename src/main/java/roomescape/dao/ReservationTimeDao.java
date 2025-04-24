package roomescape.dao;

import roomescape.entity.ReservationTimeEntity;

import java.util.List;
import java.util.Optional;

public interface ReservationTimeDao {
    ReservationTimeEntity save(ReservationTimeEntity entity);

    List<ReservationTimeEntity> findAll();

    int deleteById(Long id);

    Optional<ReservationTimeEntity> findById(Long id);
}
