package roomescape.infra;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.Reservation;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

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

    public Long saveAndGetId(final String name, final LocalDate date, final LocalTime time) {
        final KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            final String sql = """
                    INSERT INTO RESERVATION (name, date, time)
                    VALUES (?, ?, ?);
                    """;
            final PreparedStatement ps = connection.prepareStatement(
                    sql,
                    new String[]{"id"});

            ps.setString(1, name);
            ps.setDate(2, Date.valueOf(date));
            ps.setTime(3, Time.valueOf(time));
            return ps;
        }, keyHolder);

        return keyHolder.getKey().longValue();
    }

    public void deleteById(final Long id) {
        final String sql = """
                DELETE FROM RESERVATION
                WHERE id = ?
                """;

        jdbcTemplate.update(sql, id);
    }
}
