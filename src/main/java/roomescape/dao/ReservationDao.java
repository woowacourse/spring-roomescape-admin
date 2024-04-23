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
import roomescape.domain.reservation.Reservation;
import roomescape.domain.reservationtime.ReservationTime;
import roomescape.dto.reservation.ReservationCreateRequestDto;

@Component
public class ReservationDao {

    private final JdbcTemplate jdbcTemplate;

    public ReservationDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Reservation> findAll() {
        String sql = "SELECT "
                + "r.id AS reservation_id, "
                + "r.name, "
                + "r.`date`, "
                + "t.id AS time_id, "
                + "t.start_at AS time_value "
                + "FROM reservation r "
                + "INNER JOIN reservation_time t "
                + "ON r.time_id = t.id";
        return jdbcTemplate.query(
                sql,
                (resultSet, rowNum) -> getReservation(resultSet)
        );
    }

    public Reservation findById(long id) {
        String sql = "SELECT "
                + "r.id AS reservation_id, "
                + "r.name, "
                + "r.`date`, "
                + "t.id AS time_id, "
                + "t.start_at AS time_value "
                + "FROM reservation r "
                + "INNER JOIN reservation_time t "
                + "ON r.time_id = t.id "
                + "WHERE r.id = ?";
        return jdbcTemplate.queryForObject(
                sql,
                (resultSet, rowNum) -> getReservation(resultSet),
                id
        );
    }

    public long add(ReservationCreateRequestDto requestDto) {
        String sql = "INSERT INTO reservation (name, date, time_id) VALUES (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                connection -> getPreparedStatement(requestDto, connection, sql),
                keyHolder
        );
        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    public boolean exist(long id) {
        String sql = "SELECT CASE WHEN EXISTS (SELECT 1 FROM reservation WHERE id = ?) THEN TRUE ELSE FALSE END";
        return jdbcTemplate.queryForObject(sql, Boolean.class, id);
    }

    public void delete(long id) {
        String sql = "DELETE FROM reservation WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    private Reservation getReservation(ResultSet resultSet) throws SQLException {
        return Reservation.of(
                resultSet.getLong("id"),
                resultSet.getString("name"),
                resultSet.getString("date"),
                ReservationTime.of(
                        resultSet.getLong("time_id"),
                        resultSet.getString("time_value")
                )
        );
    }

    private PreparedStatement getPreparedStatement(ReservationCreateRequestDto requestDto,
                                                   Connection connection,
                                                   String sql) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(sql, new String[]{"id"});
        preparedStatement.setString(1, requestDto.getName());
        preparedStatement.setString(2, requestDto.getDate());
        preparedStatement.setLong(3, requestDto.getTimeId());
        return preparedStatement;
    }
}
