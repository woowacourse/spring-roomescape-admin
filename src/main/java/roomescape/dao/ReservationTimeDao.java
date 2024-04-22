package roomescape.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import roomescape.domain.reservationtime.ReservationTime;
import roomescape.dto.ReservationTimeCreateRequestDto;

@Component
public class ReservationTimeDao {

    private final JdbcTemplate jdbcTemplate;

    public ReservationTimeDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<ReservationTime> findAll() {
        String sql = "SELECT id, start_at FROM reservation_time";
        return jdbcTemplate.query(
                sql,
                (resultSet, rowNum) -> getReservationTime(resultSet)
        );
    }

    public ReservationTime findById(long id) {
        String sql = "SELECT id, start_at FROM reservation_time WHERE id = ?";
        return jdbcTemplate.queryForObject(
                sql,
                (resultSet, rowNum) -> getReservationTime(resultSet),
                id
        );
    }

    public long add(ReservationTimeCreateRequestDto requestDto) {
        String sql = "INSERT INTO reservation_time (start_at) VALUES (?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                connection -> getPreparedStatement(requestDto, connection, sql),
                keyHolder
        );
        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    public boolean exist(long id) {
        String sql = "SELECT CASE WHEN EXISTS (SELECT 1 FROM reservation_time WHERE id = ?) THEN TRUE ELSE FALSE END";
        return jdbcTemplate.queryForObject(sql, Boolean.class, id);
    }

    public long delete(long id) {
        String sql = "DELETE FROM reservation_time WHERE id = ?";
        jdbcTemplate.update(sql, id);
        return id;
    }

    private ReservationTime getReservationTime(ResultSet resultSet) throws SQLException {
        return ReservationTime.of(
                resultSet.getLong("id"),
                resultSet.getString("start_at")
        );
    }

    private PreparedStatement getPreparedStatement(ReservationTimeCreateRequestDto requestDto,
                                                   Connection connection,
                                                   String sql) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(sql, new String[]{"id"});
        preparedStatement.setString(1, requestDto.getStartAt());
        return preparedStatement;
    }
}
