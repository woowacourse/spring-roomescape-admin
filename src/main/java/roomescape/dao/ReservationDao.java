package roomescape.dao;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Objects;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import roomescape.domain.reservation.Reservation;
import roomescape.domain.reservationtime.ReservationTime;
import roomescape.dto.ReservationCreateRequestDto;

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
                (resultSet, rowNum) -> Reservation.of(
                        resultSet.getLong("id"),
                        resultSet.getString("name"),
                        resultSet.getString("date"),
                        ReservationTime.of(
                                resultSet.getLong("time_id"),
                                resultSet.getString("time_value")
                        )
                )
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
                (resultSet, rowNum) -> Reservation.of(
                        resultSet.getLong("id"),
                        resultSet.getString("name"),
                        resultSet.getString("date"),
                        ReservationTime.of(
                                resultSet.getLong("time_id"),
                                resultSet.getString("time_value")
                        )
                ),
                id
        );
    }

    public long add(ReservationCreateRequestDto reservationCreateRequestDto) {
        String sql = "INSERT INTO reservation (name, date, time_id) VALUES (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                connection -> {
                    PreparedStatement preparedStatement
                            = connection.prepareStatement(sql, new String[]{"id"});
                    preparedStatement.setString(1, reservationCreateRequestDto.getName());
                    preparedStatement.setString(2, reservationCreateRequestDto.getDate());
                    preparedStatement.setLong(3, reservationCreateRequestDto.getTimeId());
                    return preparedStatement;
                },
                keyHolder
        );
        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    public void delete(long id) {
        String sql = "DELETE FROM reservation WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}
