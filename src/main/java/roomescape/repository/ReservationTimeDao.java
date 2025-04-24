package roomescape.repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import roomescape.model.ReservationTime;

public class ReservationTimeDao {

    private final JdbcTemplate jdbcTemplate;

    public ReservationTimeDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public ReservationTime insert(final ReservationTime reservationTime) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String insertSql = """
                INSERT INTO reservation_time (start_at)
                VALUES (?)
                """;
        jdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(insertSql,
                    Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, reservationTime.getStartAt().format(DateTimeFormatter.ofPattern("HH:mm")));
            return preparedStatement;
        }, keyHolder);
        return new ReservationTime(keyHolder.getKey().longValue(), reservationTime.getStartAt());
    }

    public List<ReservationTime> findAll() {
        String findAllSql = """
                SELECT id, start_at
                FROM reservation_time
                """;
        return jdbcTemplate.query(findAllSql, (resultSet, rowNum) ->
                new ReservationTime(
                        resultSet.getLong("id"),
                        LocalTime.parse(resultSet.getString("start_at"))
                ));
    }

    public void deleteById(final Long id) {
        String deleteByIdSql = """
                DELETE FROM reservation_time
                WHERE id = ?
                """;
        jdbcTemplate.update(deleteByIdSql, id);
    }
}
