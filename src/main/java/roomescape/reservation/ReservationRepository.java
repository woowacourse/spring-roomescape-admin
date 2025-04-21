package roomescape.reservation;

import java.util.List;

public interface ReservationRepository {
    public Reservation saveReservation(Reservation wantToSaveReservation);
    public int deleteReservation(Long wantToDeleteId);
    public Reservation findReservationById(Long wandToFindId);
    public List<Reservation> findAllReservations();
    public boolean isExistReservation(Reservation wantToSaveReservation);
}
