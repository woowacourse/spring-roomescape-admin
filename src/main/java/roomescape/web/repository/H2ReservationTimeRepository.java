package roomescape.web.repository;

import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.sql.DataSource;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.domain.ReservationTime;
import roomescape.domain.ReservationTimeRepository;

@Repository
public class H2ReservationTimeRepository implements ReservationTimeRepository {

    private static final String TABLE_NAME = "reservation_time";
    private static final RowMapper<ReservationTime> ROW_MAPPER = (resultSet, rowNum) -> new ReservationTime(
            resultSet.getLong("id"),
            resultSet.getObject("start_at", LocalTime.class));

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public H2ReservationTimeRepository(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName(TABLE_NAME)
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public Optional<ReservationTime> findById(Long id) {
        String findSql = "SELECT id, start_at FROM %s WHERE id = ?";

        try {
            ReservationTime reservationTime = jdbcTemplate.queryForObject(
                    findSql.formatted(TABLE_NAME), ROW_MAPPER, id);
            return Optional.ofNullable(reservationTime);
        } catch (EmptyResultDataAccessException exception) {
            return Optional.empty();
        }
    }

    @Override
    public List<ReservationTime> findAll() {
        return jdbcTemplate.query("SELECT id, start_at FROM %s".formatted(TABLE_NAME), ROW_MAPPER);
    }

    @Override
    public ReservationTime create(ReservationTime reservationTime) {
        Map<String, Object> params = Map.of("start_at", reservationTime.startAt());
        Long id = simpleJdbcInsert.executeAndReturnKey(params).longValue();

        return new ReservationTime(id, reservationTime.startAt());
    }

    @Override
    public void deleteById(Long id) {
        jdbcTemplate.update("DELETE FROM %s WHERE id = ?".formatted(TABLE_NAME), id);
    }

}
