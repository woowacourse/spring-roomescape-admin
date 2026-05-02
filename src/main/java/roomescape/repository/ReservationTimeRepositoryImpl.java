package roomescape.repository;

import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.entity.ReservationTime;

@Repository
public class ReservationTimeRepositoryImpl implements ReservationTimeRepository {

    private static final String TABLE_NAME = "reservation_time";

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public ReservationTimeRepositoryImpl(final DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
            .withTableName(TABLE_NAME)
            .usingColumns("start_at")
            .usingGeneratedKeyColumns("id");
    }

    @Override
    public ReservationTime findById(final long id) {
        try {
            final String sql = String.format("SELECT id, start_at FROM %s WHERE id = :id", TABLE_NAME);
            final SqlParameterSource parameters = new MapSqlParameterSource("id", id);

            return jdbcTemplate.queryForObject(
                sql,
                parameters,
                (resultSet, rowNum) -> new ReservationTime(
                    resultSet.getLong("id"),
                    resultSet.getTime("start_at").toLocalTime()));
        } catch (DataAccessException e) {
            throw new IllegalArgumentException("해당 id를 가진 시간이 존재하지 않습니다.");
        }
    }

    @Override
    public List<ReservationTime> findAll() {
        final String sql = String.format("SELECT id, start_at FROM %s", TABLE_NAME);

        return jdbcTemplate.query(
            sql,
            (resultSet, rowNum) -> new ReservationTime(
                resultSet.getLong("id"),
                resultSet.getTime("start_at").toLocalTime()));
    }

    @Override
    public ReservationTime save(final ReservationTime reservationTime) {
        final Map<String, Object> args = Map.of("start_at", reservationTime.getStartAt());

        final long generatedKey = simpleJdbcInsert.executeAndReturnKey(args).longValue();
        return ReservationTime.builder()
            .id(generatedKey)
            .startAt(reservationTime.getStartAt())
            .build();
    }

    @Override
    public void deleteById(long id) {
        final String sql = String.format("DELETE FROM %s WHERE id = :id", TABLE_NAME);
        final SqlParameterSource parameters = new MapSqlParameterSource("id", id);

        jdbcTemplate.update(sql, parameters);
    }
}
