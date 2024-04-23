package roomescape.domain;

import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class ReservationRepository {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private JdbcTemplate jdbcTemplate;

    public ReservationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Reservation> findAll() {
        return jdbcTemplate.query(
                """
                        SELECT 
                        r.id AS reservation_id, 
                        r.name, 
                        r.date, 
                        t.id AS time_id, 
                        t.start_at AS time_value 
                        FROM reservation AS r 
                        INNER JOIN reservation_time AS t 
                        ON r.time_id = t.id""",
                reservationRowMapper());
    }

    public Reservation findById(Long id) {
        return jdbcTemplate.queryForObject(
                """
                        SELECT r.id AS reservation_id, 
                        r.name, 
                        r.date, 
                        t.id AS time_id, 
                        t.start_at AS time_value
                        FROM reservation AS r 
                        INNER JOIN reservation_time AS t
                        ON r.time_id = t.id
                        WHERE r.id = ?""",
                reservationRowMapper(), id);
    }

    public Reservation create(Reservation reservation) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO reservation (name, date, time_id) VALUES (?, ?, ?)", new String[]{"id"});
            ps.setString(1, reservation.getName().getValue());
            ps.setString(2, reservation.getDate().format(DATE_FORMATTER));
            ps.setLong(3, reservation.getTime().getId());
            return ps;
        }, keyHolder);

        long id = keyHolder.getKey().longValue();
        return findById(id);
    }

    public void removeById(Long id) {
        jdbcTemplate.update("DELETE FROM reservation WHERE id = ?", id);
    }

    private RowMapper<Reservation> reservationRowMapper() {
        return (resultSet, rowNum) -> {
            LocalTime startAt = LocalTime.parse(resultSet.getString("start_at"));
            Reservation reservation = new Reservation(
                    resultSet.getLong("reservation_id"),
                    new Name(resultSet.getString("name")),
                    LocalDate.parse(resultSet.getString("date")),
                    new ReservationTime(resultSet.getLong("time_id"), startAt)
            );
            return reservation;
        };
    }
}
