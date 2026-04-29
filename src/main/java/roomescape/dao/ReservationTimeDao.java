package roomescape.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.ReservationTime;
import roomescape.dto.ReservationTimeRequest;
import roomescape.dto.ReservationTimeResponse;

@Repository
public class ReservationTimeDao {
    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<ReservationTime> rowMapper = (resultSet, rowNum) -> {
        ReservationTime reservationTime = new ReservationTime(
                resultSet.getLong("id"),
                resultSet.getString("start_at")
        );
        return reservationTime;
    };

    public ReservationTimeDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public ReservationTimeResponse insert(ReservationTimeRequest request) {
        String sql = "insert into reservation_time (start_at) values (?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement statement = connection.prepareStatement(
                        sql, new String[]{"id"}
                );
                statement.setString(1, request.startAt());

                return statement;
            }
        }, keyHolder);

        long generatedId = keyHolder.getKey().longValue();
        return ReservationTimeResponse.from(new ReservationTime(generatedId, request.startAt()));
    }
}
