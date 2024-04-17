package roomescape.repository;

import roomescape.entity.ReservationEntity;

import java.util.List;

public interface ReservationRepository {
    List<ReservationEntity> findAll();

    ReservationEntity save(final ReservationEntity reservation);

    void deleteById(final Long reservationId);
}
