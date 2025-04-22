package roomescape.domain.Reservation;

import java.util.List;
import roomescape.dto.request.ReservationCreateRequest;

public interface Reservations {

    List<Reservation> findAll();

    long create(ReservationCreateRequest reservationCreateRequest);

    void delete(final Long id);
}
