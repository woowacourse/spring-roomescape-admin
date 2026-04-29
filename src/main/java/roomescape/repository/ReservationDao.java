package roomescape.repository;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.entity.Reservation;

import java.sql.PreparedStatement;
import java.util.List;

@Repository
public class ReservationDao {
    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<Reservation> reservationRowMapper = (resultSet, rowNum) -> {
        Reservation reservation = new Reservation(
                resultSet.getLong("id"),
                resultSet.getString("name"),
                resultSet.getDate("date").toLocalDate(),
                resultSet.getTime("time").toLocalTime()
        );
        return reservation;
    };

    public ReservationDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Reservation> findAllReservation() {
        String sql = "SELECT * FROM reservation";
        return jdbcTemplate.query(sql, reservationRowMapper);
    }

    public Reservation findReservation(Long id) {
        String sql = "SELECT * FROM reservation WHERE id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, reservationRowMapper, id);
        } catch (EmptyResultDataAccessException e) {
            throw new IllegalArgumentException("존재하지 않는 예약입니다.");
        }
    }

    public Long insertReservation(Reservation reservation) {
        String sql = "INSERT INTO reservation (name, date, time) VALUES (?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    sql, new String[]{"id"});
            preparedStatement.setString(1, reservation.name());
            preparedStatement.setString(2, String.valueOf(reservation.date()));
            preparedStatement.setString(3, String.valueOf(reservation.time()));
            return preparedStatement;
        }, keyHolder);
        return keyHolder.getKey().longValue();
    }

    public void deleteReservation(Long id) {
        String sql = "DELETE FROM reservation WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}
