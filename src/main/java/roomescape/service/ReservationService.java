package roomescape.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;
import roomescape.controller.dto.ReservationCreateRequest;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Service
public class ReservationService {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ReservationService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Reservation> readReservations() {
        String sql = "SELECT r.id AS reservation_id, r.name, r.date, t.id AS time_id, t.start_at AS start_at " +
                "FROM reservation AS r INNER JOIN reservation_time as t on r.time_id = t.id";
        RowMapper<Reservation> rowMapper = getReservationRowMapper();
        return jdbcTemplate.query(sql, rowMapper);
    }

    public Reservation readReservation(Long id) {
        String sql = "SELECT r.id AS reservation_id, r.name, r.date, t.id AS time_id, t.start_at AS start_at " +
                "FROM reservation AS r INNER JOIN reservation_time as t on r.time_id = t.id WHERE r.id = ?";
        RowMapper<Reservation> rowMapper = getReservationRowMapper();

        return jdbcTemplate.queryForObject(sql, rowMapper, id);
    }

    private RowMapper<Reservation> getReservationRowMapper() {
        return (resultSet, rowNum) -> new Reservation(resultSet.getLong("id"),
                resultSet.getString("name"),
                resultSet.getString("date"),
                new ReservationTime(resultSet.getLong("time_id"),
                        resultSet.getString("start_at"))
        );
    }

    public Reservation createReservation(ReservationCreateRequest request) {
        String sql = "INSERT INTO reservation (name, date, time_id) VALUES (?, ?, ?)";
        PreparedStatementCreator preparedStatementCreator = (connect) -> {
            PreparedStatement statement = connect.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, request.name());
            statement.setString(2, request.date());
            statement.setLong(3, request.timeId());

            return statement;
        };
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(preparedStatementCreator, keyHolder);
        return readReservation(keyHolder.getKey().longValue());
    }

    public void deleteReservation(Long id) {
        String sql = "DELETE FROM reservation WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}
