package roomescape.repository.reservation;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import roomescape.domain.reservation.Reservation;
import roomescape.domain.time.ReservationTime;

@Component
public class H2ReservationDao implements ReservationDao {

    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<Reservation> reservationRowMapper = ((rs, rowNum) -> new Reservation(
        rs.getLong("reservation_id"),
        rs.getString("name"),
        rs.getDate("date").toLocalDate(),
        new ReservationTime(
            rs.getLong("time_id"),
            rs.getTime("time_value").toLocalTime()
        )
    ));

    public H2ReservationDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void save(final Reservation reservation) {
        String sql = "INSERT INTO reservation (name, date, time_id) VALUES (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, reservation.getName());
            ps.setDate(2, Date.valueOf(reservation.getDate()));
            ps.setLong(3, reservation.getTime().getId());
            return ps;
        }, keyHolder);

        reservation.setId(keyHolder.getKey().longValue());
    }

    @Override
    public Optional<Reservation> findById(final long id) {
        String sql = """
            SELECT
                r.id AS reservation_id,
                r.name,
                r.date,
                t.id AS time_id,
                t.start_at AS time_value
            FROM reservation AS r
            INNER JOIN reservation_time AS t ON r.time_id = t.id
            WHERE r.id = ?
            """;
        List<Reservation> results = jdbcTemplate.query(sql, reservationRowMapper, id);

        return Optional.ofNullable(DataAccessUtils.singleResult(results));
    }

    @Override
    public List<Reservation> findAll() {
        String sql = """
            SELECT
                r.id as reservation_id,
                r.name,
                r.date,
                t.id as time_id,
                t.start_at as time_value
            FROM reservation as r
            inner join reservation_time as t
            on r.time_id = t.id
            """;

        return jdbcTemplate.query(sql, reservationRowMapper);
    }

    @Override
    public boolean deleteById(final long id) {
        String sql = "DELETE FROM reservation WHERE id = ?";

        return jdbcTemplate.update(sql, id) == 1;
    }
}
