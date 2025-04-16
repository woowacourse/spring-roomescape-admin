package roomescape.repository;

import roomescape.entity.ReservationEntity;

import java.util.List;

public interface ReservationRepository {

    List<ReservationEntity> findAll();

    ReservationEntity save(ReservationEntity reservationEntity);

    void deleteById(long id);
}
