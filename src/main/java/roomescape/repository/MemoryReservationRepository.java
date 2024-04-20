package roomescape.repository;

import roomescape.entity.Reservation;

import java.util.List;

public class MemoryReservationRepository implements ReservationRepository {
    @Override
    public List<Reservation> findAll() {
        return List.of();
    }

    @Override
    public Reservation findById(Long id) {
        return null;
    }

    @Override
    public Reservation save(Reservation reservation) {
        return null;
    }

    @Override
    public int deleteById(Long id) {
        return 0;
    }
}
