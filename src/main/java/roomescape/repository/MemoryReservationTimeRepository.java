package roomescape.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import roomescape.entity.ReservationTime;

import java.util.List;

@Repository
public class MemoryReservationTimeRepository implements ReservationTimeRepository {

    private final JdbcTemplate jdbcTemplate;

    public MemoryReservationTimeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<ReservationTime> findAll() {
        return List.of();
    }

    @Override
    public ReservationTime save(ReservationTime reservationTime) {
        return null;
    }

    @Override
    public int deleteById(Long id) {
        return 0;
    }
}
