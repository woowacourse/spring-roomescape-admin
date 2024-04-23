package roomescape.domain;

import java.sql.PreparedStatement;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class ReservationRepository {
    private JdbcTemplate jdbcTemplate;

    public ReservationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Reservation> findAll() {
        return jdbcTemplate.query(
                """
                        SELECT r.id AS reservation_id, r.name, r.date, t.id AS time_id, t.start_at AS time_value 
                        FROM reservation AS r INNER JOIN reservation_time AS t 
                        ON r.time_id = t.id""",
                reservationRowMapper());
    }

    public Reservation findById(long id) {
        return jdbcTemplate.queryForObject(
                """
                        SELECT r.id AS reservation_id, r.name, r.date, t.id AS time_id, t.start_at AS time_value
                        FROM reservation AS r INNER JOIN reservation_time AS t
                        ON r.time_id = t.id
                        WHERE r.id = ?""",
                reservationRowMapper(), id);
    }

    public Reservation create(Reservation reservation) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO reservation (name, date, time_id) VALUES (?, ?, ?)",
                    new String[]{"id"});
            ps.setString(1, reservation.getName());
            ps.setString(2, reservation.getDate());
            ps.setLong(3, reservation.getTime().getId());
            return ps;
        }, keyHolder);

        long id = keyHolder.getKey().longValue();
        return findById(id);
    }

    public void remove(Reservation reservation) {
        jdbcTemplate.update("DELETE FROM reservation WHERE id = ?", reservation.getId());
    }

    private RowMapper<Reservation> reservationRowMapper() {
        return (resultSet, rowNum) -> {
            Reservation reservation = new Reservation(
                    resultSet.getLong("reservation_id"),
                    resultSet.getString("name"),
                    resultSet.getString("date"),
                    new ReservationTime(resultSet.getLong("time_id"), resultSet.getString("start_at"))
            );
            return reservation;
        };
    }
}
