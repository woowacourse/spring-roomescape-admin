package roomescape.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import roomescape.dto.ReservationRequestDto;
import roomescape.model.Reservation;
import roomescape.model.ReservationTime;

public class MemoryReservationRepository implements ReservationRepository{
    private final List<Reservation> reservations;
    private final AtomicLong id = new AtomicLong(0);

    public MemoryReservationRepository() {
        this.reservations = new ArrayList<Reservation>();
    }

    @Override
    public List<Reservation> getAllReservations() {
        return new ArrayList<>(reservations);
    }

    @Override
    public Reservation addReservation(ReservationRequestDto reservationRequestDto, ReservationTime reservationTime) {
        Reservation reservation = reservationRequestDto.toEntity(id.getAndIncrement(), reservationTime);
        reservations.add(reservation);
        return reservation;
    }

    @Override
    public int deleteReservation(Long id) {
        for (Reservation reservation : reservations) {
            if (reservation.getId().equals(id)) {
                reservations.remove(reservation);
                return 1;
            }
        }
        return 0;
    }
}
