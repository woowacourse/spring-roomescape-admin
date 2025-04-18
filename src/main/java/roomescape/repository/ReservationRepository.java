package roomescape.repository;

import java.util.List;
import roomescape.domain.ReservationInfo;
import roomescape.entity.ReservationEntity;

public interface ReservationRepository {

    List<ReservationEntity> getReservations();

    ReservationEntity add(final ReservationInfo reservationInfo);

    void deleteById(final Long id);

    boolean existReservation(final Long id);
}
