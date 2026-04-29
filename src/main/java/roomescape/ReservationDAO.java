package roomescape;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Time;
import java.util.List;
import java.util.Objects;

@Repository
public class ReservationDAO {
    private JdbcTemplate jdbcTemplate;

    public ReservationDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Reservation> rowMapper = (resultSet, rowNum) -> {
        Reservation reservation = new Reservation(
                resultSet.getLong("id"),
                resultSet.getString("name"),
                resultSet.getDate("date").toLocalDate(),
                resultSet.getTime("time").toLocalTime()
        );
        return reservation;
    };

    public int count() {
        String sql = "SELECT COUNT(*) FROM reservation";
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

    public Reservation findReservationById(Long id) {
        String sql = "SELECT id, name, date, time FROM reservation WHERE id=?";
        return jdbcTemplate.queryForObject(sql, rowMapper, id);
    }

    public List<Reservation> findAllReservation() {
        String sql = "SELECT id, name, date, time FROM reservation";
        return jdbcTemplate.query(sql, rowMapper);
    }

    public void insert(Reservation reservation) {
        String sql = "INSERT INTO reservation (name, date, time) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, reservation.getName(), reservation.getDate(), reservation.getTime());
    }

    public Long insertWithKeyHolder(Reservation reservation) {
        String sql = "INSERT INTO reservation (name, date, time) VALUES(?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    sql, new String[]{"id"});
            preparedStatement.setString(1, reservation.getName());
            preparedStatement.setDate(2, Date.valueOf(reservation.getDate()));
            preparedStatement.setTime(3, Time.valueOf(reservation.getTime()));
            return preparedStatement;
        }, keyHolder);

        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    public int delete(Long id) {
        String sql = "DELETE FROM reservation where id = ?";
        return jdbcTemplate.update(sql, Long.valueOf(id));
    }

}
