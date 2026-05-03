package roomescape.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;

import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

@Repository
public class ReservationDAO {
    private final JdbcTemplate jdbcTemplate;

    public ReservationDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Reservation> rowMapper = (resultSet, rowNum) -> {
        ReservationTime time = new ReservationTime(
                resultSet.getLong("time_id"),
                LocalTime.parse(resultSet.getString("time_value"))
        );
        return new Reservation(
                resultSet.getLong("reservation_id"),
                resultSet.getString("name"),
                LocalDate.parse(resultSet.getString("date")),
                time
        );
    };

    public int count() {
        String sql = "SELECT COUNT(*) FROM reservation";
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

    public Reservation findReservationById(Long id) {
        String sql = "SELECT r.id as reservation_id, r.name, r.date, t.id as time_id, t.start_at as time_value " +
                "FROM reservation as r INNER JOIN reservation_time as t ON r.time_id = t.id WHERE r.id=?";
        return jdbcTemplate.queryForObject(sql, rowMapper, id);
    }

    public List<Reservation> findAllReservation() {
        String sql = "SELECT r.id as reservation_id, r.name, r.date, t.id as time_id, t.start_at as time_value " +
                "FROM reservation as r INNER JOIN reservation_time as t ON r.time_id = t.id";
        return jdbcTemplate.query(sql, rowMapper);
    }

    public boolean existsByTimeId(Long id) {
        String sql = "SELECT COUNT(*) FROM reservation WHERE time_id=?";
        int rowNum = jdbcTemplate.queryForObject(sql, Integer.class, id);
        return rowNum > 0;
    }

    public Long insertWithKeyHolder(Reservation reservation) {
        String sql = "INSERT INTO reservation (name, date, time_id) VALUES(?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    sql, new String[]{"id"});
            preparedStatement.setString(1, reservation.getName());
            preparedStatement.setString(2, reservation.getDate().toString());
            preparedStatement.setLong(3, reservation.getTime().getId());
            return preparedStatement;
        }, keyHolder);

        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    public int delete(Long id) {
        String sql = "DELETE FROM reservation WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }
}
