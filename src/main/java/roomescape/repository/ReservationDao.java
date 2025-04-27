package roomescape.repository;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;

@Repository
public class ReservationDao {

    private final JdbcTemplate jdbcTemplate;

    public ReservationDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Reservation> findAll() {
        final String sql = """
                    SELECT
                        r.id AS reservation_id,
                        r.name,
                        r.date,
                        t.id AS time_id,
                        t.start_at AS time_value
                    FROM reservation AS r 
                    INNER JOIN reservation_time AS t 
                    ON r.time_id = t.id
                """;
        return jdbcTemplate.query(sql, getReservationRowMapper());
    }

    public Optional<Reservation> findById(final long id) {
        final String sql = """
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
        try {
            final Reservation reservation = jdbcTemplate.queryForObject(sql, getReservationRowMapper(), id);
            return Optional.ofNullable(reservation);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public Reservation save(final Reservation reservation) {
        final String sql = "INSERT INTO reservation (name, date, time_id) VALUES (?, ?, ?)";
        final KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            final PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, reservation.getName());
            ps.setDate(2, Date.valueOf(reservation.getDate()));
            ps.setLong(3, reservation.getTime().getId());
            return ps;
        }, keyHolder);

        final long id = Objects.requireNonNull(keyHolder.getKey()).longValue();
        return reservation.register(id);
    }

    public void remove(final long id) {
        final String sql = "DELETE FROM reservation WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    private RowMapper<Reservation> getReservationRowMapper() {
        return (resultSet, rowNum) -> new Reservation(
                resultSet.getLong("id"),
                resultSet.getString("name"),
                resultSet.getString("date"),
                resultSet.getLong("time_id"),
                resultSet.getString("time_value")
        );
    }
}
