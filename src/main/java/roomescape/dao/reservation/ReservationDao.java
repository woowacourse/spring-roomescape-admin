package roomescape.dao.reservation;

import java.util.List;
import roomescape.domain.Reservation;
import roomescape.dto.request.ReservationCreateRequest;

public interface ReservationDao {

    List<Reservation> findAll();

    long create(ReservationCreateRequest reservationCreateRequest);

    void delete(final Long id);
}
