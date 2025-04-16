package roomescape.repository;

import roomescape.entity.ReservationEntity;

import java.util.List;
import java.util.Optional;

public interface ReservationRepository {

    Optional<ReservationEntity> findById(long id);

    List<ReservationEntity> findAll();

    ReservationEntity save(ReservationEntity reservationEntity);

    void deleteById(long id);
}
