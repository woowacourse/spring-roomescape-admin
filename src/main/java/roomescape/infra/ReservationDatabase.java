package roomescape.infra;

import roomescape.infra.entity.ReservationEntity;

import java.util.List;
import java.util.Optional;

public interface ReservationDatabase {

    List<ReservationEntity> findAll();

    Optional<ReservationEntity> findById(final long id);

    long saveAndGetId(final ReservationEntity entity);

    void deleteById(final long id);
}
