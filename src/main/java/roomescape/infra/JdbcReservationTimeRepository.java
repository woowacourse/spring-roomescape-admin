package roomescape.infra;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.domain.entity.ReservationTime;
import roomescape.repository.ReservationTimeRepository;

import java.util.List;
import java.util.Optional;

@Repository
public class JdbcReservationTimeRepository implements ReservationTimeRepository {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;
    private final RowMapper<ReservationTime> rowMapper = (resultSet, rowNum) -> {
        return new ReservationTime(
                resultSet.getLong("id"),
                resultSet.getTime("start_at").toLocalTime()
        );
    };

    public JdbcReservationTimeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("reservation_time")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public Long save(ReservationTime reservationTime) {
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("start_at", reservationTime.getStartAt());
        return simpleJdbcInsert.executeAndReturnKey(params).longValue();
    }

    @Override
    public List<ReservationTime> findAll() {
        String selectSql = "SELECT id, start_at FROM reservation_time";
        return jdbcTemplate.query(selectSql, rowMapper);
    }

    @Override
    public Optional<ReservationTime> findById(Long id) {
        String selectSql = "SELECT id, start_at FROM reservation_time WHERE id = ?";
        List<ReservationTime> reservationTime = jdbcTemplate.query(selectSql, rowMapper, id);
        return reservationTime.stream().findFirst();
    }

    @Override
    public void delete(Long id) {
        String deleteSql = "DELETE FROM reservation_time WHERE id = ?";
        jdbcTemplate.update(deleteSql, id);
    }
}
