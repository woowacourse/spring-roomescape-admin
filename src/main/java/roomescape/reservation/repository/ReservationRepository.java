package roomescape.reservation.repository;

import java.util.List;
import java.util.Optional;
import roomescape.reservation.domain.Reservation;
import roomescape.reservation.entity.ReservationEntity;

public interface ReservationRepository {
    List<ReservationEntity> getAll();

    ReservationEntity put(Reservation item);

    void deleteById(long id);

    Optional<ReservationEntity> findById(long id);
}
