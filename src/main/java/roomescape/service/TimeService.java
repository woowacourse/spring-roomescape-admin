package roomescape.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;
import roomescape.controller.dto.TimeCreateRequest;
import roomescape.domain.ReservationTime;

import java.sql.PreparedStatement;
import java.sql.Statement;

@Service
public class TimeService {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public TimeService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public ReservationTime createTime(TimeCreateRequest request) {
        String sql = "INSERT INTO reservation_time (start_at) VALUES (?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        PreparedStatementCreator preparedStatementCreator = (connect) -> {
            PreparedStatement statement = connect.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, request.startAt());
            return statement;
        };

        jdbcTemplate.update(preparedStatementCreator, keyHolder);
        return readReservationTime(keyHolder.getKey().longValue());
    }

    public ReservationTime readReservationTime(Long id) {
        String sql = "SELECT * FROM reservation_time WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, getReservationTimeRowMapper(), id);
    }

    private RowMapper<ReservationTime> getReservationTimeRowMapper() {
        RowMapper<ReservationTime> rowMapper = (resultSet, rowNum) -> new ReservationTime(
                resultSet.getLong("id"),
                resultSet.getTime("start_at").toLocalTime()
        );

        return rowMapper;
    }
}
