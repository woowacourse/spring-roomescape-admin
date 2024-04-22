package roomescape.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class ReservationDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ReservationDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Reservation save(Reservation reservation) {
        String sql = "INSERT INTO reservation (name, date, time_id) VALUES (?, ?, ?)";
        PreparedStatementCreator preparedStatementCreator = (connect) -> {
            PreparedStatement statement = connect.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, reservation.getName());
            statement.setString(2, reservation.getDate());
            statement.setLong(3, reservation.getTime().getId());

            return statement;
        };

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(preparedStatementCreator, keyHolder);

        return findById(keyHolder.getKey().longValue());
    }

    public Reservation findById(Long id) {
        String sql = "SELECT r.id AS reservation_id, r.name, r.date, t.id AS time_id, t.start_at AS start_at " +
                "FROM reservation AS r INNER JOIN reservation_time as t on r.time_id = t.id WHERE r.id = ?";
        return jdbcTemplate.queryForObject(sql, getReservationRowMapper(), id);
    }

    public List<Reservation> findAll() {
        String sql = "SELECT r.id AS reservation_id, r.name, r.date, t.id AS time_id, t.start_at AS start_at " +
                "FROM reservation AS r INNER JOIN reservation_time as t on r.time_id = t.id";
        return jdbcTemplate.query(sql, getReservationRowMapper());
    }

    private RowMapper<Reservation> getReservationRowMapper() {
        return (resultSet, rowNum) -> new Reservation(resultSet.getLong("id"),
                resultSet.getString("name"),
                resultSet.getString("date"),
                new ReservationTime(resultSet.getLong("time_id"),
                        resultSet.getString("start_at"))
        );
    }

    public void deleteById(Long id) {
        String sql = "DELETE FROM reservation WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}
