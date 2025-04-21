package roomescape.infra;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.Reservation;
import roomescape.dto.request.ReservationCreateRequest;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public class ReservationDatabase {

    private static final RowMapper<Reservation> ROW_MAPPER = (rs, rowNum) -> {
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

        return jdbcTemplate.query(sql, ROW_MAPPER);
    }

    public long saveAndGetId(final ReservationCreateRequest request) {
        final Number savedId = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("reservation")
                .usingGeneratedKeyColumns("id")
                .executeAndReturnKey(request.dataMap());

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
