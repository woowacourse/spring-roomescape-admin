package roomescape.reservation.dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.reservation.Reservation;
import roomescape.time.ReservationTime;

@Repository
public class JdbcReservationDao implements ReservationDao {
    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Reservation> reservationRowMapper = (resultSet, rowNum) -> {
        return new Reservation(
                resultSet.getLong("reservation_id"),
                resultSet.getString("name"),
                resultSet.getDate("date").toLocalDate(),
                new ReservationTime(resultSet.getLong("time_id"),
                        resultSet.getTime("time_value").toLocalTime())
        );
    };

    @Autowired
    public JdbcReservationDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Reservation> findAll() {
        String sql = "SELECT r.id reservation_id, r.name, r.date, t.id time_id, t.start_at time_value"
                + " FROM reservation r"
                + " INNER JOIN reservation_time t"
                + " ON r.time_id = t.id";
        return jdbcTemplate.query(sql, reservationRowMapper);
    }

    @Override
    public Reservation save(Reservation reservation) {
        String sql = "INSERT INTO reservation(name, date, time_id) VALUES(?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(sql, new String[]{"id"});
            preparedStatement.setString(1, reservation.getCustomerName());
            preparedStatement.setDate(2, Date.valueOf(reservation.getReservationDate()));
            preparedStatement.setLong(3, reservation.getReservationTimeId());
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
