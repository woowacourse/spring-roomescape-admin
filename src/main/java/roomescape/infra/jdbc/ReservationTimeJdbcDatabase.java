package roomescape.infra.jdbc;

import org.springframework.context.annotation.Primary;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.infra.ReservationTimeDatabase;
import roomescape.infra.entity.ReservationTimeEntity;

import java.util.List;
import java.util.Optional;

@Repository
@Primary
public class ReservationTimeJdbcDatabase implements ReservationTimeDatabase {

    private final JdbcTemplate jdbcTemplate;

    public ReservationTimeJdbcDatabase(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<ReservationTimeEntity> findAll() {
        final String sql = """
                SELECT
                    id,
                    start_at
                FROM RESERVATION_TIME
                """;

        return jdbcTemplate.query(sql, ReservationTimeEntity.ROW_MAPPER);
    }

    @Override
    public Optional<ReservationTimeEntity> findById(final long id) {
        final String sql = """
                SELECT
                    id,
                    start_at
                FROM RESERVATION_TIME
                WHERE id = ?
                """;

        try {
            final ReservationTimeEntity reservationTime = jdbcTemplate.queryForObject(sql, ReservationTimeEntity.ROW_MAPPER, id);
            return Optional.ofNullable(reservationTime);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public long saveAndGetId(final ReservationTimeEntity entity) {
        final Number savedId = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName(ReservationTimeEntity.TABLE_NAME)
                .usingGeneratedKeyColumns(ReservationTimeEntity.ID_COL_NAME)
                .executeAndReturnKey(entity.toDataMap());

        return savedId.longValue();
    }

    @Override
    public void deleteById(final long id) {
        final String sql = """
                DELETE FROM RESERVATION_TIME
                WHERE id = ?
                """;

        jdbcTemplate.update(sql, id);
    }
}
