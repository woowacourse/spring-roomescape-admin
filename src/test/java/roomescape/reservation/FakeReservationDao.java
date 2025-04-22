package roomescape.reservation;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FakeReservationDao implements ReservationDao{

    private long NEXT_ID = 1L;
    private final List<Reservation> reservations = new ArrayList<>();
    private final List<Long> invokeDeleteId = new ArrayList<>();

    @Override
    public Reservation saveReservation(final Reservation reservation) {
        final Reservation writedReservation = reservation.writeId(NEXT_ID++);
        reservations.add(writedReservation);
        return writedReservation;
    }

    @Override
    public List<Reservation> findAllReservation() {
        return new ArrayList<>(reservations);
    }

    @Override
    public void deleteReservationById(final long id) {
        this.reservations.stream()
                .filter(reservation -> Objects.equals(reservation.id(), id))
                .findAny()
                .ifPresent(reservation -> reservations.remove(reservation));
        invokeDeleteId.add(id);
    }

    public boolean isInvokeDeleteById(final Long id){
        return this.invokeDeleteId.stream()
                .anyMatch(value -> Objects.equals(value, id));
    }
}
