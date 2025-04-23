package roomescape.repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import roomescape.model.Reservation;
import roomescape.model.ReservationDateTime;

@Repository
public class ReservationDao {

    private final JdbcTemplate jdbcTemplate;

    public ReservationDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Reservation> findAll() {
        String findAllSql = """
                select id, name, date, time
                FROM reservation
                """;
        return jdbcTemplate.query(findAllSql, (resultSet, rowNum) -> new Reservation(
                resultSet.getLong("id"),
                resultSet.getString("name"),
                new ReservationDateTime(LocalDateTime.of(
                        LocalDate.parse(resultSet.getString("date")),
                        LocalTime.parse(resultSet.getString("time"))
                ))));
    }

    public Long insert(final Reservation reservation) {
        KeyHolder generatedKeyHolder = new GeneratedKeyHolder();
        String insertSql = """
                INSERT INTO reservation (name, date, time)
                VALUES (?, ?, ?)            
                """;
        jdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(insertSql,
                    Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, reservation.getName());
            preparedStatement.setString(2, reservation.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            preparedStatement.setString(3, reservation.getTime().format(DateTimeFormatter.ofPattern("HH:mm")));
            return preparedStatement;
        }, generatedKeyHolder);
        return generatedKeyHolder.getKey().longValue();
    }

    public void deleteById(final Long id) {
        String deleteSql = """
                DELETE FROM reservation
                WHERE id = ?
                """;
        jdbcTemplate.update(deleteSql, id);
    }
}
