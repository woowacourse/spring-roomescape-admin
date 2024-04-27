package roomescape.repository.web.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.domain.reservation.ReservationTime;
import roomescape.repository.ReservationTimeRepository;

import java.util.List;
import java.util.Optional;

@Repository
public class ReservationTimeDao implements ReservationTimeRepository {
    private static final String TABLE_NAME = "reservation_time";
    private static final String ID = "id";

    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert simpleJdbcInsert;
    private final RowMapper<ReservationTime> timeRowMapper;

    public ReservationTimeDao(JdbcTemplate jdbcTemplate, RowMapper<ReservationTime> timeRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(this.jdbcTemplate)
                .withTableName(TABLE_NAME)
                .usingGeneratedKeyColumns(ID);
        this.timeRowMapper = timeRowMapper;
    }

    public ReservationTime add(ReservationTime time) {
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("start_at", time.getTime());
        Long savedId = simpleJdbcInsert.executeAndReturnKey(parameters).longValue();
        return new ReservationTime(savedId, time.getTime());
    }

    public Optional<ReservationTime> findById(Long id) {
        String sql = "SELECT * FROM RESERVATION_TIME WHERE id = ?";
        return jdbcTemplate.query(sql, timeRowMapper, id)
                .stream()
                .findAny();
    }

    public List<ReservationTime> findAll() {
        String sql = "SELECT * FROM RESERVATION_TIME";
        return jdbcTemplate.query(sql, timeRowMapper);
    }

    public void delete(Long id) {
        String sql = "DELETE FROM RESERVATION_TIME WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}
