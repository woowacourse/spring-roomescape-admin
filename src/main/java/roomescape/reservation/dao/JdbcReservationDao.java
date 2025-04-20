package roomescape.reservation.dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Time;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import roomescape.reservation.Reservation;

@Component
public class JdbcReservationDao implements ReservationDao {
    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Reservation> reservationRowMapper = (resultSet, rowNum) -> {
        return new Reservation(
                resultSet.getLong("id"),
                resultSet.getString("name"),
                resultSet.getDate("date").toLocalDate(),
                resultSet.getTime("time").toLocalTime()
        );
    };

    @Autowired
    public JdbcReservationDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Reservation> findAll() {
        String sql = "SELECT * FROM reservation";
        return jdbcTemplate.query(sql, reservationRowMapper);
    }

    @Override
    public Reservation save(Reservation reservation) {
        String sql = "INSERT INTO reservation(name, date, time) VALUES(?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(sql, new String[]{"id"});
            preparedStatement.setString(1, reservation.getCustomerName());
            preparedStatement.setDate(2, Date.valueOf(reservation.getReservationDate()));
            preparedStatement.setTime(3, Time.valueOf(reservation.getReservationTime()));
            return preparedStatement;
        }, keyHolder);
        return new Reservation(keyHolder.getKeyAs(Long.class), reservation.getCustomerName(),
                reservation.getReservationDate(), reservation.getReservationTime());
    }

    @Override
    public boolean removeById(long id) {
        String sql = "DELETE FROM reservation WHERE id = ?";
        int rowNumber = jdbcTemplate.update(sql, id);
        return rowNumber == 1;
    }
}
