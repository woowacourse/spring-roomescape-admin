package roomescape.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.dao.EmptyResultDataAccessException;
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

    public List<ReservationTimeResponse> findAllReservationTimes() {
        String sql = "select * from reservation_time";
        try {
            List<ReservationTime> reservationTimes = jdbcTemplate.query(sql, rowMapper);
            return reservationTimes.stream()
                    .map(ReservationTimeResponse::from)
                    .collect(Collectors.toList());
        } catch (EmptyResultDataAccessException exception) {
            return null;
        }
    }

    public void delete(Long id) {
        String sql = "delete from reservation_time where id = ?";
        jdbcTemplate.update(sql, id);
    }
}
