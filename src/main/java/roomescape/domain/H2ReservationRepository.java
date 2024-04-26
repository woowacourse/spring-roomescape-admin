package roomescape.domain;

import java.time.LocalDate;
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

@Repository
public class H2ReservationRepository implements ReservationRepository {

    private static final String TABLE_NAME = "reservation";
    private static final RowMapper<Reservation> ROW_MAPPER = (resultSet, rowNum) -> new Reservation(
            resultSet.getLong("id"),
            resultSet.getString("name"),
            resultSet.getObject("date", LocalDate.class),
            new ReservationTime(
                    resultSet.getLong("time_id"),
                    resultSet.getObject("start_at", LocalTime.class)));

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public H2ReservationRepository(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName(TABLE_NAME)
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public Optional<Reservation> findById(Long id) {
        String findSql = """
                SELECT
                    r.id AS id,
                    name,
                    date,
                    t.id AS time_id,
                    start_at
                FROM reservation AS r
                INNER JOIN reservation_time AS t
                ON r.time_id = t.id
                WHERE r.id = ?""";

        try {
            Reservation reservation = jdbcTemplate.queryForObject(findSql, ROW_MAPPER, id);
            return Optional.ofNullable(reservation);
        } catch (EmptyResultDataAccessException exception) {
            return Optional.empty();
        }
    }

    @Override
    public List<Reservation> findAll() {
        return jdbcTemplate.query("""
                SELECT
                    r.id AS id,
                    name,
                    date,
                    t.id AS time_id,
                    start_at
                FROM reservation AS r
                INNER JOIN reservation_time AS t
                ON r.time_id = t.id""", ROW_MAPPER);
    }

    @Override
    public Reservation create(Reservation reservation) {
        Map<String, Object> params = Map.of(
                "name", reservation.name(),
                "date", reservation.date(),
                "time_id", reservation.time().id());
        Long id = simpleJdbcInsert.executeAndReturnKey(params).longValue();

        return findById(id).orElseThrow(IllegalStateException::new);
    }

    @Override
    public void deleteById(Long id) {
        jdbcTemplate.update("DELETE FROM %s WHERE id = ?".formatted(TABLE_NAME), id);
    }

}
