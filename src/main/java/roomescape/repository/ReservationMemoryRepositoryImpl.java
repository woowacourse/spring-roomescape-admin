package roomescape.repository;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Repository;
import roomescape.dto.CreateReservationDto;
import roomescape.entity.Reservation;

@Repository("reservationMemoryRepository")
public class ReservationMemoryRepositoryImpl implements ReservationRepository {

    private final List<Reservation> reservations = new ArrayList<>();

    @Override
    public List<Reservation> findAll() {
        return List.of();
    }

    @Override
    public Long addAndGetId(CreateReservationDto createReservationDto) {
        return 0L;
    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public Reservation findById(Long id) {
        return null;
    }
}
