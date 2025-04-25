package roomescape.usecase.Reservation;

import java.util.List;

public interface ReservationRepository {

    Reservation addReservation(Reservation reservation);

    List<ReservationOutput> getAllReservations();
}
