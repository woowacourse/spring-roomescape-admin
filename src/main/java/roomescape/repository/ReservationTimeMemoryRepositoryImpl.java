package roomescape.repository;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Repository;
import roomescape.dto.CreateReservationTimeDto;
import roomescape.entity.ReservationTime;

@Repository("reservationTimeMemoryRepository")
public class ReservationTimeMemoryRepositoryImpl implements ReservationTimeRepository {

    private final List<ReservationTime> reservationTimes = new ArrayList<>();

    @Override
    public Long addAndGetId(CreateReservationTimeDto createReservationTimeDto) {
        return 0L;
    }

    @Override
    public ReservationTime findById(Long id) {
        return null;
    }

    @Override
    public List<ReservationTime> findAll() {
        return List.of();
    }

    @Override
    public void deleteById(Long id) {

    }
}
