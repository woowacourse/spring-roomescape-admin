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

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Time;
import java.util.List;

@Service
public class ReservationService {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ReservationService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Reservation> readReservations() {
        String sql = "SELECT * FROM reservation";
        RowMapper<Reservation> rowMapper = getReservationRowMapper();

        return jdbcTemplate.query(sql, rowMapper);
    }

    public Reservation readReservation(Long id) {
        String sql = "SELECT * FROM reservation WHERE id = ?";
        RowMapper<Reservation> rowMapper = getReservationRowMapper();

        return jdbcTemplate.queryForObject(sql, rowMapper, id);
    }

    private RowMapper<Reservation> getReservationRowMapper() {
        return (resultSet, rowNum) ->
                new Reservation(resultSet.getLong("id"),
                        resultSet.getString("name"),
                        resultSet.getDate("date").toLocalDate(),
                        resultSet.getTime("time").toLocalTime());
    }

    public Reservation createReservation(ReservationCreateRequest request) {
        String sql = "INSERT INTO reservation (name, date, time) VALUES (?, ?, ?)";
        PreparedStatementCreator preparedStatementCreator = (connect) -> {
            PreparedStatement statement = connect.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, request.name());
            statement.setDate(2, Date.valueOf(request.date()));
            statement.setTime(3, Time.valueOf(request.time()));

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
