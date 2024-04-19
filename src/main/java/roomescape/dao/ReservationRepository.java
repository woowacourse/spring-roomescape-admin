package roomescape.dao;

import java.util.List;
import roomescape.domain.Reservation;
import roomescape.entity.ReservationEntity;

public interface ReservationRepository {

    ReservationEntity addReservation(Reservation reservation);

    List<ReservationEntity> findAll();

    void deleteById(Long id);

    boolean existsById(Long id);

    void deleteAll();
}
