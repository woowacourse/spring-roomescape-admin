package roomescape.infra;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.Reservation;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

@Repository
public class ReservationDatabase {

    private static final RowMapper<Reservation> reservationRowMapper = (rs, rowNum) -> {
        final long id = rs.getLong("id");
        final String name = rs.getString("name");
        final LocalDate date = rs.getDate("date").toLocalDate();
        final LocalTime time = rs.getTime("time").toLocalTime();
        return new Reservation(id, name, date, time);
    };
    private final JdbcTemplate jdbcTemplate;

    public ReservationDatabase(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Reservation> findAll() {
        final String sql = """
                SELECT * FROM RESERVATION
                """;

        return jdbcTemplate.query(sql, reservationRowMapper);
    }

    public long saveAndGetId(final String name, final LocalDate date, final LocalTime time) {
        final Number savedId = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("reservation")
                .usingGeneratedKeyColumns("id")
                .executeAndReturnKey(Map.of(
                        "name", name,
                        "date", date,
                        "time", time
                ));

        return savedId.longValue();
    }

    public void deleteById(final Long id) {
        final String sql = """
                DELETE FROM RESERVATION
                WHERE id = ?
                """;

        jdbcTemplate.update(sql, id);
    }
}
