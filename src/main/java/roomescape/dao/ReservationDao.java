package roomescape.dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationRequest;

@Repository
public class ReservationDao {

    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Reservation> actorRowMapper = (rs, rowNum) ->
            new Reservation(
                    rs.getLong("reservation_id"),
                    rs.getString("name"),
                    rs.getDate("date").toLocalDate(),
                    new ReservationTime(rs.getLong("time_id"), rs.getTime("time_value").toLocalTime())
            );

    public ReservationDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Reservation> findAll() {
        String sql = """
                SELECT 
                    r.id AS reservation_id,
                    r.name,
                    r.date,
                    t.id AS time_id,
                    t.start_at AS time_value
                FROM reservation AS r
                INNER JOIN reservation_time AS t
                ON r.time_id = t.id""";  // time_value 이건 왜필요하지?
        return jdbcTemplate.query(sql, actorRowMapper);
    }

    public Reservation add(final ReservationRequest reservationRequest) {
        String sql = "INSERT INTO reservation (name, date, time_id) VALUES (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, reservationRequest.name());
            ps.setDate(2, Date.valueOf(reservationRequest.date()));
            ps.setLong(3, reservationRequest.timeId());
            return ps;
        }, keyHolder);

        long id = keyHolder.getKey().longValue();
        return findById(id);
    }

    private Reservation findById(final long id) {
        String sql = """
                SELECT
                    r.id AS reservation_id,
                    r.name,
                    r.date,
                    t.id AS time_id,
                    t.start_at AS time_value
                FROM reservation AS r
                INNER JOIN reservation_time AS t
                ON r.time_id = t.id
                WHERE r.id = ?
                """;
        Reservation reservation = jdbcTemplate.queryForObject(sql, actorRowMapper, id);
        return reservation;
    }

    public void delete(final long id) {
        String sql = "DELETE FROM reservation WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}
