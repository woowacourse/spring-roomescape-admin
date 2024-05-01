package roomescape.core.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.core.domain.ReservationTime;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class H2ReservationTimeRepository implements ReservationTimeRepository {
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    public H2ReservationTimeRepository(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("reservation_time")
                .usingGeneratedKeyColumns("id");
    }

    public ReservationTime save(ReservationTime reservationTime) {
        long reservationTimeId = jdbcInsert.executeAndReturnKey(Map.of("start_at", reservationTime.getStartAt()))
                .longValue();

        return new ReservationTime(
                reservationTimeId,
                reservationTime.getStartAt());
    }

    public List<ReservationTime> findAll() {
        String sql = "select * from reservation_time";

        return jdbcTemplate.query(sql, (resultSet, rowNum) -> new ReservationTime(
                resultSet.getLong("id"),
                resultSet.getTime("start_at").toLocalTime()));
    }

    public Optional<ReservationTime> findById(Long id) {
        String sql = "select * from reservation_time where id = ?";
        List<ReservationTime> reservationTimes = jdbcTemplate.query(sql, (resultSet, rowNum) -> new ReservationTime(
                resultSet.getLong("id"),
                resultSet.getTime("start_at").toLocalTime()), id);

        return reservationTimes.stream().findFirst();
    }

    public void deleteById(long id) {
        String sql = "delete from reservation_time where id = ?";
        jdbcTemplate.update(sql, id);
    }

    public boolean isReferencedId(final Long id) {
        String sql = "select count(*) from reservation where time_id = ?";
        Long count = jdbcTemplate.queryForObject(sql, Long.class, id);
        return count > 0;
    }
}
