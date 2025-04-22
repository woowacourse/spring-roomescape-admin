package roomescape.infra;

import roomescape.infra.entity.ReservationTimeEntity;

import java.util.List;
import java.util.Optional;

public interface ReservationTimeDatabase {

    List<ReservationTimeEntity> findAll();

    Optional<ReservationTimeEntity> findById(final long id);

    long saveAndGetId(final ReservationTimeEntity request);

    void deleteById(final long id);
}
