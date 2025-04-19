package roomescape.repository;

import java.util.List;
import roomescape.domain.Reservation;
import roomescape.entity.ReservationEntity;

public interface ReservationRepository {

    List<ReservationEntity> getReservations();

    ReservationEntity add(final Reservation reservation);

    void deleteById(final Long id);

    boolean existReservation(final Long id);
}
