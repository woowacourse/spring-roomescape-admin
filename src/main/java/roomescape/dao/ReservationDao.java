package roomescape.dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;

@Repository
public class ReservationDao {

    private final JdbcTemplate jdbcTemplate;

    public ReservationDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Reservation> findAll() {
        String selectAllSQL = """
        SELECT r.id AS reservation_id, r.name AS reservation_name, r.date AS reservation_date,
               t.id AS time_id, t.start_at AS time_start_at FROM reservation AS r
        INNER JOIN reservation_time AS t ON r.time_id = t.id
        """;

        return jdbcTemplate.query(selectAllSQL, (resultSet, rowNum) -> new Reservation(
                resultSet.getLong("reservation_id"),
                resultSet.getString("reservation_name"),
                resultSet.getDate("reservation_date").toLocalDate(),
                new ReservationTime(
                        resultSet.getLong("time_id"),
                        resultSet.getTime("time_start_at").toLocalTime()
                )
        ));
    }

    public Reservation save(Reservation reservation) {
        String insertSQL = "INSERT INTO reservation (name, date, time_id) VALUES (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(insertSQL, new String[]{"id"});
            preparedStatement.setString(1, reservation.getName());
            preparedStatement.setDate(2, Date.valueOf(reservation.getDate()));
            preparedStatement.setLong(3, reservation.getReservationTime().getId());
            return preparedStatement;
        }, keyHolder);

        return findById(keyHolder.getKey().longValue());
    }

    public void delete(Long reservationId) {
        String deleteSQL = "DELETE FROM reservation WHERE id = ?";
        jdbcTemplate.update(deleteSQL, reservationId);
    }

    public Reservation findById(Long reservationId) {
        String selectSQL = """
        SELECT r.id AS reservation_id, r.name AS reservation_name, r.date AS reservation_date,
               t.id AS time_id, t.start_at AS time_start_at FROM reservation AS r
        INNER JOIN reservation_time AS t ON r.time_id = t.id
        WHERE r.id = ?
        """;

        return jdbcTemplate.queryForObject(selectSQL, (resultSet, rowNum) -> new Reservation(
                resultSet.getLong("reservation_id"),
                resultSet.getString("reservation_name"),
                resultSet.getDate("reservation_date").toLocalDate(),
                new ReservationTime(
                        resultSet.getLong("time_id"),
                        resultSet.getTime("time_start_at").toLocalTime()
                )
        ), reservationId);
    }
}
