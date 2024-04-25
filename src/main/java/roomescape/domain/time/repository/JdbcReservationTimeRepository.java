package roomescape.domain.time.repository;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.core.simple.SimpleJdbcInsertOperations;
import org.springframework.stereotype.Repository;
import roomescape.domain.time.ReservationTime;
import roomescape.global.query.QueryBuilder;
import roomescape.global.query.condition.ComparisonCondition;

@Repository
public class JdbcReservationTimeRepository implements ReservationTimeRepository {
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsertOperations jdbcInsert;
    private final RowMapper<ReservationTime> rowMapper = (rs, rowNum) -> new ReservationTime(
            rs.getLong("id"),
            rs.getTime("start_at").toLocalTime()
    );

    public JdbcReservationTimeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("reservation_time")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public ReservationTime save(ReservationTime reservationTime) {
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("start_at", reservationTime.getStartAt());
        long id = jdbcInsert.executeAndReturnKey(params).longValue();
        return reservationTime.updateId(id);
    }

    @Override
    public boolean existsByStartAt(LocalTime localTime) {
        String query = QueryBuilder.select("reservation_time")
                .addColumns("count(*)")
                .where(ComparisonCondition.equalTo("start_at", localTime))
                .build();
        Integer count = jdbcTemplate.queryForObject(query, Integer.class);
        return count != null && count > 0;
    }

    @Override
    public Optional<ReservationTime> findById(long id) {
        String query = QueryBuilder.select("reservation_time")
                .addAllColumns()
                .where(ComparisonCondition.equalTo("id", id))
                .build();
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(query, rowMapper));
        } catch (DataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<ReservationTime> findAll() {
        String query = QueryBuilder.select("reservation_time")
                .addAllColumns()
                .build();
        return jdbcTemplate.query(query, rowMapper);
    }

    @Override
    public void deleteById(long id) {
        String query = QueryBuilder.delete("reservation_time")
                .where(ComparisonCondition.equalTo("id", id))
                .build();
        jdbcTemplate.update(query);
    }
}
