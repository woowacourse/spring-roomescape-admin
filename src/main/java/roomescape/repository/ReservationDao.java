package roomescape.repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import roomescape.model.Reservation;
import roomescape.model.exception.ReservationNotFoundException;

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
                LocalDate.parse(resultSet.getString("date")),
                LocalTime.parse(resultSet.getString("time"))
        ));
    }

    public Reservation insert(final Reservation reservation) {
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
        return new Reservation(generatedKeyHolder.getKey().longValue(), reservation.getName(),
                reservation.getDate(), reservation.getTime());
    }

    public void deleteById(final Long id) {
        String deleteSql = """
                DELETE FROM reservation
                WHERE id = ?
                """;
        int updatedRow = jdbcTemplate.update(deleteSql, id);
        if (updatedRow == 0) {
            throw new ReservationNotFoundException("존재하지 않는 예약번호 입니다.");
        }
    }

    public Reservation findById(final Long id) {
        String findSql = """
                SELECT name, date, time
                FROM reservation
                WHERE id = ?
                """;
        try {
            return jdbcTemplate.queryForObject(findSql,
                    (resultSet, rowNum) -> new Reservation(
                            id,
                            resultSet.getString("name"),
                            LocalDate.parse(resultSet.getString("date")),
                            LocalTime.parse(resultSet.getString("time"))
                    ), id);
        } catch (EmptyResultDataAccessException e) {
            throw new ReservationNotFoundException("존재하지 않는 예약번호 입니다.", e);
        }
    }
}
