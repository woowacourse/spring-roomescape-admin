package roomescape.infra.jdbc;

import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.business.domain.Reservation;
import roomescape.business.domain.ReservationTime;
import roomescape.dto.request.ReservationCreateRequest;
import roomescape.infra.ReservationDatabase;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
@Primary
public class ReservationJdbcDatabase implements ReservationDatabase {

    private static final RowMapper<Reservation> ROW_MAPPER = (rs, rowNum) -> {
        final long id = rs.getLong("reservation_id");
        final String name = rs.getString("name");
        final LocalDate date = rs.getDate("date").toLocalDate();
        final long timeId = rs.getLong("time_id");
        final LocalTime timeValue = rs.getTime("time_value").toLocalTime();
        return new Reservation(id, name, date, new ReservationTime(timeId, timeValue));
    };

    private final JdbcTemplate jdbcTemplate;

    public ReservationJdbcDatabase(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Reservation> findAll() {
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

        return jdbcTemplate.query(sql, ROW_MAPPER);
    }

    @Override
    public Reservation findById(long id) {
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

        return jdbcTemplate.queryForObject(sql, ROW_MAPPER, id);
    }

    @Override
    public long saveAndGetId(final ReservationCreateRequest request) {
        final Number savedId = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("reservation")
                .usingGeneratedKeyColumns("id")
                .executeAndReturnKey(request.dataMap());

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
