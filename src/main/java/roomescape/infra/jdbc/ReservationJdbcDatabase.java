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
                    r.id,
                    r.name,
                    r.date,
                    t.id AS ${timeIdColName},
                    t.start_at AS ${timeValueColName}
                FROM RESERVATION AS r
                INNER JOIN RESERVATION_TIME AS t
                ON r.time_id = t.id
                """
                .replace("${timeIdColName}", ReservationEntity.TIME_ID_COL_NAME)
                .replace("${timeValueColName}", ReservationEntity.TIME_VALUE_COL_NAME);

        return jdbcTemplate.query(sql, ReservationEntity.ROW_MAPPER);
    }

    @Override
    public Optional<ReservationEntity> findById(long id) {
        final String sql = """
                SELECT
                    r.id,
                    r.name,
                    r.date,
                    t.id AS ${timeIdColName},
                    t.start_at AS ${timeValueColName}
                FROM RESERVATION AS r
                INNER JOIN RESERVATION_TIME AS t
                ON r.time_id = t.id
                WHERE r.id = ?
                """
                .replace("${timeIdColName}", ReservationEntity.TIME_ID_COL_NAME)
                .replace("${timeValueColName}", ReservationEntity.TIME_VALUE_COL_NAME);

        try {
            final ReservationEntity entity = jdbcTemplate.queryForObject(sql, ReservationEntity.ROW_MAPPER, id);
            return Optional.ofNullable(entity);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public long saveAndGetId(final ReservationEntity entity) {
        final Number savedId = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName(ReservationEntity.TABLE_NAME)
                .usingGeneratedKeyColumns(ReservationEntity.ID_COL_NAME)
                .executeAndReturnKey(entity.toDataMap());

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
