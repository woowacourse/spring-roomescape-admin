package roomescape.dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Time;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;

@Repository
public class ReservationDao {

    private final JdbcTemplate jdbcTemplate;

    public ReservationDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Reservation> findAll() {
        String selectAllSQL = "select id, name, date, time from reservation";

        return jdbcTemplate.query(selectAllSQL, (resultSet, rowNum) -> new Reservation(
                resultSet.getLong("id"),
                resultSet.getString("name"),
                resultSet.getDate("date").toLocalDate(),
                resultSet.getTime("time").toLocalTime()
        ));
    }

    public Long save(Reservation reservation) {
        String insertSQL = "insert into reservation (name, date, time) values (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(insertSQL, new String[]{"id"});
            preparedStatement.setString(1, reservation.getName());
            preparedStatement.setDate(2, Date.valueOf(reservation.getDate()));
            preparedStatement.setTime(3, Time.valueOf(reservation.getTime()));
            return preparedStatement;
        }, keyHolder);

        return keyHolder.getKey().longValue();
    }

    public void delete(Long reservationId) {
        String deleteSQL = "delete from reservation where id = ?";
        jdbcTemplate.update(deleteSQL, reservationId);
    }
}
