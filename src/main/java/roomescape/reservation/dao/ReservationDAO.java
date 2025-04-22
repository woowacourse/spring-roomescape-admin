package roomescape.reservation.dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Time;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.reservation.domain.Reservation;
import roomescape.reservation.dto.request.ReservationRequest;

@Repository
public class ReservationDAO {
    private final JdbcTemplate jdbcTemplate;

    public ReservationDAO(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Reservation> findAllReservations() {
        String sql = "SELECT * from reservation";
        List<Reservation> reservations = jdbcTemplate.query(
                sql,
                (resultSet, rowNum) -> {
                    Reservation reservation = new Reservation(
                            resultSet.getLong("id"),
                            resultSet.getString("name"),
                            resultSet.getDate("date").toLocalDate(),
                            resultSet.getTime("time").toLocalTime()
                    );
                    return reservation;
                });
        return reservations;
    }

    public long insertReservation(final ReservationRequest reservationRequest) {
        String sql = "INSERT into reservation (name, date, time) values (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    sql,
                    new String[]{"id"});
            preparedStatement.setString(1, reservationRequest.name());
            preparedStatement.setDate(2, Date.valueOf(reservationRequest.date()));
            preparedStatement.setTime(3, Time.valueOf(reservationRequest.time()));
            return preparedStatement;
        }, keyHolder);
        return keyHolder.getKey().longValue();
    }

    public void removeReservation(final long id) {
        String sql = "DELETE from reservation where id = ?";
        jdbcTemplate.update(sql, id);
    }
}
