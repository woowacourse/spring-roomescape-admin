package roomescape.repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.controller.ReservationTimeRepository;
import roomescape.domain.ReservationTime;

@Repository
public class DatabaseReservationTimeRepository implements ReservationTimeRepository {
    private final RowMapper<ReservationTime> reservationTimeRowMapper = (rs, rowNum) -> new ReservationTime(
            rs.getLong("id"),
            rs.getTime("start_at").toLocalTime()
    );

    private final JdbcTemplate jdbcTemplate;

    public DatabaseReservationTimeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<ReservationTime> findReservationTimes() {
        return jdbcTemplate.query("SELECT * FROM reservation_time", reservationTimeRowMapper);
    }

    @Override
    public Optional<ReservationTime> findReservationTimeById(Long id) {
        return Optional.ofNullable(jdbcTemplate.queryForObject(
                "SELECT * FROM reservation_time WHERE id = ?",
                reservationTimeRowMapper,
                id));
    }

    @Override
    public ReservationTime createReservationTime(ReservationTime reservationTime) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("reservation_time")
                .usingGeneratedKeyColumns("id");
        long createdReservationTimeId = simpleJdbcInsert.executeAndReturnKey(Map.of(
                "start_at", reservationTime.getStartAt()
        )).longValue();

        return findReservationTimeById(createdReservationTimeId).get();
    }

    @Override
    public void deleteReservationTimeById(Long id) {
        jdbcTemplate.update("DELETE FROM reservation_time WHERE id = ?", id);
    }
}
