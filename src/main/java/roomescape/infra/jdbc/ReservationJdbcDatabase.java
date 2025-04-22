package roomescape.infra.jdbc;

import org.springframework.context.annotation.Primary;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.infra.ReservationDatabase;
import roomescape.infra.entity.ReservationEntity;

import java.util.List;
import java.util.Optional;

@Repository
@Primary
public class ReservationJdbcDatabase implements ReservationDatabase {

    private final JdbcTemplate jdbcTemplate;

    public ReservationJdbcDatabase(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<ReservationEntity> findAll() {
        final String sql = """
                SELECT
                    r.id AS reservation_id,
                    r.name,
                    r.date,
                    t.id AS time_id,
                    t.start_at AS time_value
                FROM RESERVATION AS r
                INNER JOIN RESERVATION_TIME AS t
                ON r.time_id = t.id
                """;

        return jdbcTemplate.query(sql, ReservationEntity.getRowMapper());
    }

    @Override
    public Optional<ReservationEntity> findById(long id) {
        final String sql = """
                SELECT
                    r.id AS reservation_id,
                    r.name,
                    r.date,
                    t.id AS time_id,
                    t.start_at AS time_value
                FROM RESERVATION AS r
                INNER JOIN RESERVATION_TIME AS t
                ON r.time_id = t.id
                WHERE r.id = ?
                """;

        try {
            final ReservationEntity entity = jdbcTemplate.queryForObject(sql, ReservationEntity.getRowMapper(), id);
            return Optional.ofNullable(entity);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public long saveAndGetId(final ReservationEntity entity) {
        final Number savedId = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("reservation")
                .usingGeneratedKeyColumns("id")
                .executeAndReturnKey(entity.dataMap());

        return savedId.longValue();
    }

    @Override
    public void deleteById(final long id) {
        final String sql = """
                DELETE FROM RESERVATION
                WHERE id = ?
                """;

        jdbcTemplate.update(sql, id);
    }
}
